package com.example.minipocservicio1.cachelibrary.repositories.impl;

import com.example.minipocservicio1.cachelibrary.repositories.interfaces.ICacheRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

import java.util.Date;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;


@Repository
@Slf4j
public class CacheRepository<T> implements ICacheRepository<T> {

    final ReactiveRedisTemplate reactiveTemplate;
    final RedisTemplate template;

    private static final ObjectMapper OBJECT_MAPPER;
    private static final TimeZone DEFAULT_TIMEZONE = TimeZone.getTimeZone("UTC");

    static {
        OBJECT_MAPPER = new ObjectMapper();
        OBJECT_MAPPER.setTimeZone(DEFAULT_TIMEZONE);
    }

    public CacheRepository(ReactiveRedisTemplate reactiveTemplate, RedisTemplate template) {
        this.reactiveTemplate = reactiveTemplate;
        this.template = template;
    }

    private void addCollection(String collection, String hkey, T object) throws JsonProcessingException {
        String jsonObject = OBJECT_MAPPER.writeValueAsString(object);
        template.opsForHash().put(collection, hkey, jsonObject);
    }

    // implement methods

    @Override
    public boolean add(String collection, String hkey, T object, int timeout, TimeUnit unit) {
        try {
            addCollection(collection,hkey,object);
            template.expire(collection,timeout , unit);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public boolean add(String collection, String hkey, T object) {

        try {
            addCollection(collection,hkey,object);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public boolean add(String collection, String hkey, T object, Date date) {
        try {
            addCollection(collection,hkey,object);
            template.expireAt(collection,date);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public  Mono<Boolean> addReactive(String collection, String hkey, T object) throws JsonProcessingException, InterruptedException {

        try {
            String jsonObject = OBJECT_MAPPER.writeValueAsString(object);
            return reactiveTemplate.opsForHash().put(collection, hkey, jsonObject);
        } catch (Exception e) {
            throw e;
           // return false;
        }
    }

    public Mono<T> findReactive(String collection, String hkey, Class<T> tClass)  {

        try {
         return reactiveTemplate.opsForHash().get(collection, hkey).flatMap(x-> {

             try {
                 String json = String.valueOf(x);
                 return Mono.just(OBJECT_MAPPER.readValue(json,tClass));
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


    @Override
    public boolean delete(String collection, String hkey) {
        try {
            template.opsForHash().delete(collection, hkey);
            reactiveTemplate.delete(collection,hkey);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public T find(String collection, String hkey, Class<T> tClass) {
        try {
            String jsonObj = String.valueOf(template.opsForHash().get(collection, hkey));
            return OBJECT_MAPPER.readValue(jsonObj, tClass);
        } catch (Exception e) {
            if(e.getMessage() == null){
            } else {
            }
            return null;
        }
    }

    @Override
    public Boolean isAvailable() {
        try{
            return template.getConnectionFactory().getConnection().ping() != null;
        } catch (Exception e) {
            return false;
        }

    }

    public boolean any(String collection){
        try{
            return !template.opsForHash().entries(collection).isEmpty();
        } catch (Exception e) {
            return false;
        }
    }

    public boolean hasKey(String collection, String hkey){
        try{
            return template.opsForHash().entries(collection).containsKey(hkey);
        } catch (Exception e) {
            return false;
        }
    }

}
