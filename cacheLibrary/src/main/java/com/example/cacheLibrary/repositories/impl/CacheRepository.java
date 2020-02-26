package com.example.cacheLibrary.repositories.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.example.cacheLibrary.repositories.interfaces.ICacheRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

import java.util.Date;
import java.util.Map;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;

@Repository
public class CacheRepository<T> implements ICacheRepository<T> {
    ReactiveRedisTemplate reactiveTemplate;

    RedisTemplate template;

    private static final ObjectMapper OBJECT_MAPPER;
    private static final TimeZone DEFAULT_TIMEZONE = TimeZone.getTimeZone("UTC");

    static {
        OBJECT_MAPPER = new ObjectMapper();
        OBJECT_MAPPER.setTimeZone(DEFAULT_TIMEZONE);
    }

    public CacheRepository(    @Qualifier("reactiveRedisTemplate") ReactiveRedisTemplate reactiveTemplate,@Qualifier("redisTemplate")  RedisTemplate template) {
        this.reactiveTemplate = reactiveTemplate;
        this.template = template;
    }

    private void addCollection(String collection, String hkey, T object) throws JsonProcessingException {
        String jsonObject = OBJECT_MAPPER.writeValueAsString(object);
        template.opsForHash().put(collection, hkey, jsonObject);
    }

    // implement methods


    public boolean add(String collection, String hkey, T object, int timeout, TimeUnit unit) {
        try {
            addCollection(collection, hkey, object);
            template.expire(collection, timeout, unit);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean add(String collection, String hkey, T object) {

        try {
            addCollection(collection, hkey, object);
            return true;
        } catch (Exception e) {
            return false;
        }
    }


    public boolean add(String collection, String hkey, T object, Date date) {
        try {
            addCollection(collection, hkey, object);
            template.expireAt(collection, date);
            return true;
        } catch (Exception e) {
            return false;
        }
    }


    public Mono<Boolean> addReactive(String collection, String hkey, T object) throws JsonProcessingException {

        try {
            String jsonObject = OBJECT_MAPPER.writeValueAsString(object);
            return reactiveTemplate.opsForHash().put(collection, hkey, jsonObject);
        } catch (Exception e) {
            throw e;
            // return false;
        }
    }


    public Mono<T> findReactive(String collection, String hkey, Class<T> tClass) {

        try {
            return reactiveTemplate.opsForHash().get(collection, hkey).flatMap(x -> {

                try {
                    String json = String.valueOf(x);
                    return Mono.just(OBJECT_MAPPER.readValue(json, tClass));
                } catch (JsonProcessingException e) {
                    e.printStackTrace();
                }

                return x;
            });
        } catch (Exception e) {
            throw e;
            // return false;
        }
    }


    public boolean delete(String collection) {
        try {
            template.delete(collection);
            reactiveTemplate.delete(collection);
            return true;
        } catch (Exception e) {
            return false;
        }
    }


    public boolean delete(String collection, String hkey) {
        try {
            template.opsForHash().delete(collection, hkey);
            reactiveTemplate.delete(collection, hkey);
            return true;
        } catch (Exception e) {
            return false;
        }
    }


    public T find(String collection, String hkey, Class<T> tClass) {
        try {
            String jsonObj = String.valueOf(template.opsForHash().get(collection, hkey));
            return OBJECT_MAPPER.readValue(jsonObj, tClass);
        } catch (Exception e) {
            if (e.getMessage() == null) {
            } else {
            }
            return null;
        }
    }

    public String first(String collection) {
        try {
            Map<String, Object> map = template.opsForHash().entries(collection);
            String value = map.keySet().toArray()[0].toString();
            return value;
        } catch (Exception e) {
            return null;
        }
    }

    public T find(String collection, Class<T> tClass) {
        try {
            String jsonObj = String.valueOf(template.opsForHash().entries(collection));
            return (T) jsonObj;
            // return OBJECT_MAPPER.readValue(jsonObj, tClass);
        } catch (Exception e) {
            if (e.getMessage() == null) {
            } else {
            }
            return null;
        }
    }


    public Boolean isAvailable() {
        try {
            return template.getConnectionFactory().getConnection().ping() != null;
        } catch (Exception e) {
            return false;
        }

    }

    public boolean any(String collection) {
        try {
            return !template.opsForHash().entries(collection).isEmpty();
        } catch (Exception e) {
            return false;
        }
    }

    public boolean hasKey(String collection, String hkey) {
        try {
            return template.opsForHash().entries(collection).containsKey(hkey);
        } catch (Exception e) {
            return false;
        }
    }

    public Map completeCollection(String collection) {
        try {
            return template.opsForHash().entries(collection);
        } catch (Exception e) {
            return null;
        }
    }

}
