package com.example.cachelibrary.repositories.impl;

import com.example.cachelibrary.repositories.interfaces.ICacheRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.time.Duration;
import java.util.Date;
import java.util.Map;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public class CacheRepository<T> implements ICacheRepository<T> {
  ReactiveRedisTemplate reactiveTemplate;

  RedisTemplate template;

  Logger logger = LoggerFactory.getLogger(CacheRepository.class);

  private static final ObjectMapper OBJECT_MAPPER;
  private static final TimeZone DEFAULT_TIMEZONE = TimeZone.getTimeZone("UTC");

  static {
    OBJECT_MAPPER = new ObjectMapper();
    OBJECT_MAPPER.setTimeZone(DEFAULT_TIMEZONE);
  }

  public CacheRepository(
      @Qualifier("reactiveStringRedisTemplate") ReactiveRedisTemplate reactiveTemplate,
      @Qualifier("stringRedisTemplate") RedisTemplate template) {
    this.reactiveTemplate = reactiveTemplate;
    this.template = template;
  }

  private void addCollection(String collection, String hkey, T object)
      throws JsonProcessingException {
    String jsonObject = OBJECT_MAPPER.writeValueAsString(object);
    template.opsForHash().put(collection, hkey, jsonObject);
  }

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

  public Mono<Boolean> addReactive(String collection, String hkey, T object) {

    try {
      String jsonObject = OBJECT_MAPPER.writeValueAsString(object);
      return reactiveTemplate.opsForHash().put(collection, hkey, jsonObject);
    } catch (Exception e) {
      logger.error(e.getMessage(), e);
      return Mono.just(false);
    }
  }

  public Mono<Boolean> addReactive(String collection, String hkey, T object, int timeout)
  {
    try {
      String jsonObject = OBJECT_MAPPER.writeValueAsString(object);
      reactiveTemplate.opsForHash().put(collection, hkey, jsonObject).block();
      return reactiveTemplate.expire(collection, Duration.ofSeconds(timeout));
    } catch (Exception e) {
      return Mono.just(false);
    }
  }

  public Mono<T> findReactive(String collection, String hkey, Class<T> tclass) {

    return reactiveTemplate
        .opsForHash()
        .get(collection, hkey)
        .flatMap(
            x -> {
              try {
                String json = String.valueOf(x);
                return Mono.just(OBJECT_MAPPER.readValue(json, tclass));
              } catch (JsonProcessingException e) {
                logger.error(e.getMessage(), e);
              }

              return x;
            });
  }

  public boolean deleteReactive(String collection) {
    try {
      reactiveTemplate.delete(collection);
      return true;
    } catch (Exception e) {
      return false;
    }
  }

  public boolean delete(String collection) {
    try {
      template.delete(collection);
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

  public T find(String collection, String hkey, Class<T> tclass) {
    try {
      String jsonObj = String.valueOf(template.opsForHash().get(collection, hkey));
      return OBJECT_MAPPER.readValue(jsonObj, tclass);
    } catch (Exception e) {
      return null;
    }
  }

  public T find(String collection, Class<T> tclass) {
    try {
      String jsonObj = String.valueOf(template.opsForHash().entries(collection));
      return (T) jsonObj;
    } catch (Exception e) {
      return null;
    }
  }

  public String first(String collection) {
    try {
      Map<String, Object> map = template.opsForHash().entries(collection);
      return map.keySet().toArray()[0].toString();
    } catch (Exception e) {
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
