package com.example.cachelibrary.repositories.impl;

import com.example.cachelibrary.configuration.RedisConfigTest;
import com.example.cachelibrary.configuration.RedisEmbeddedServerConfig;
import com.example.cachelibrary.repositories.interfaces.ICacheRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.*;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@Import({RedisConfigTest.class, RedisEmbeddedServerConfig.class})
@TestPropertySource(locations = {"/redis.properties"})

class CacheRepositoryTest {

  @Autowired RedisTemplate redisTemplate;

  @Autowired ReactiveRedisTemplate reactiveRedisTemplate;

  ICacheRepository<String[]> repository;

  String collection = "http://pepe";
  String hkey = "pepe";
  String[] array = {"a", "b"};

  @BeforeEach
  void setUp() {
    repository = new CacheRepository(reactiveRedisTemplate, redisTemplate);
  }

  @AfterEach
  void tearDown() {
    try {
      Set<String> redisKeys = redisTemplate.keys("*");
      for (String key : redisKeys) {
        redisTemplate.delete(key);
      }
    } catch (Exception e) {
    }
  }

  @DisplayName("Test add params String collection, String hkey, T object")
  @Test
  void testAdd1() {
    assertTrue(repository.add(collection, hkey, array));

    String[] response = repository.find(collection, hkey, String[].class);

    assertTrue(Arrays.equals(response, array));
  }

  @DisplayName(
      "Test add params String collection, String hkey, T object, int timeout, TimeUnit unit")
  @Test
  void testAdd2() throws InterruptedException {

    int timeout = 5000;
    TimeUnit timeUnit = TimeUnit.MILLISECONDS;

    assertTrue(repository.add(collection, hkey, array, timeout, timeUnit));
    Thread.sleep(timeout);

    String[] response = repository.find(collection, hkey, String[].class);
    assertTrue(response == null);
  }

  @DisplayName("Test add params String collection, String hkey, T object, Date date")
  @Test
  void testAdd3() throws InterruptedException {

    // Expira en el dia de la fecha, en 5 segundos
    Date date = new Date();
    int timeout = 5000;
    Calendar cal = Calendar.getInstance();
    cal.setTime(date);
    cal.add(Calendar.MILLISECOND, timeout);
    Date expireDate = cal.getTime();

    assertTrue(repository.add(collection, hkey, array, expireDate));
    String[] response = repository.find(collection, hkey, String[].class);
    assertTrue(Arrays.equals(response, array));

    Thread.sleep(timeout);

    String[] response1 = repository.find(collection, hkey, String[].class);
    assertTrue(response1 == null);
  }

  @DisplayName("Test addReactive params String collection, String hkey, T object")
  @Test
  void testAddReactive1() throws JsonProcessingException, InterruptedException {

    assertTrue(repository.addReactive(collection, hkey, array).block());

    String[] response = repository.find(collection, hkey, String[].class);

    assertTrue(Arrays.equals(response, array));
  }

  @DisplayName("Test addReactive params String collection, String hkey, T object,int timeout")
  @Test
  void testAddReactive2() throws JsonProcessingException, InterruptedException {

    int timeout = 5;

    assertTrue(repository.addReactive(collection, hkey, array, timeout).block());

    Thread.sleep(timeout * 1000);

    String[] response = repository.find(collection, hkey, String[].class);
    assertTrue(response == null);
  }

  @DisplayName("Test findReactive when exists")
  @Test
  void testFindReactiveWhenExists() {

    repository.add(collection, hkey, array);

    String[] response = repository.findReactive(collection, hkey, String[].class).block();

    assertArrayEquals(array, response);
  }

  @DisplayName("Test findReactive when doesn't exists")
  @Test
  void testFindReactiveWhenDoesntExists() {
    String collection = "http://pepe";
    String hkey = "pepe";

    String[] response = repository.findReactive(collection, hkey, String[].class).block();

    assertEquals(null, response);
  }

  @DisplayName("Test deleteReactive when exists")
  @Test
  void testDeleteReactiveWhenExists() {

    repository.add(collection, hkey, array);

    long response = repository.deleteReactive(collection).block();

    assertEquals(1, response);
  }

  @DisplayName("Test deleteReactive when doesnt exists")
  @Test
  void testDeleteReactiveWhenDoesntExists() {
    String collection = "http://pepe";
    long response = repository.deleteReactive(collection).block();
    assertEquals(0, response);
  }

  @DisplayName("Test delete when exists with params String collection")
  @Test
  void testDelete1WhenExists() {

    repository.add(collection, hkey, array);
    assertTrue(repository.delete(collection));
  }

  @DisplayName("Test delete when doesnt exists with params String collection")
  @Test
  void testDelete1WhenDoesntExists() {
    String collection = "http://pepe";
    assertFalse(repository.delete(collection));
  }

  @DisplayName("Test delete when exists with params String collection, String hkey")
  @Test
  void testDelete2WhenExists() {
    String hkey1 = "pepe1";
    String[] array1 = {"c", "d"};

    repository.add(collection, hkey, array);
    repository.add(collection, hkey1, array1);

    assertEquals(1, repository.delete(collection, hkey));
  }

  @DisplayName(
      "Test delete when doesnt exists collection with params String collection, String hkey")
  @Test
  void testDelete2WhenDoesntExistsCollection() {
    String collection = "http://pepe";
    String hkey = "pepe";

    assertEquals(0, repository.delete(collection, hkey));
  }

  @DisplayName("Test delete when doesnt exists hkey with params String collection, String hkey")
  @Test
  void testDelete2WhenDoesntExistsHkey() {

    repository.add(collection, hkey, array);

    String hkey1 = "pepe1";
    assertEquals(0, repository.delete(collection, hkey1));
  }

  @DisplayName("Test find when exists with params String collection, String hkey, Class<T> tclass")
  @Test
  void testFindWhenExists() {
    repository.add(collection, hkey, array);

    String[] response = repository.find(collection, hkey, String[].class);

    assertArrayEquals(array, response);
  }

  @DisplayName(
      "Test find when doesn't exists collection with params String collection, String hkey, Class<T> tclass")
  @Test
  void testFindWhenDoesntExistsCollection() {

    String[] response = repository.find(collection, hkey, String[].class);

    assertEquals(null, response);
  }

  @DisplayName(
      "Test find when doesn't exists hkey with params String collection, String hkey, Class<T> tclass")
  @Test
  void testFindWhenDoesntExistsHkey() {
    repository.add(collection, hkey, array);
    String hkey1 = "pepe1";

    String[] response = repository.find(collection, hkey1, String[].class);

    assertEquals(null, response);
  }

  @DisplayName("Test first when exists collection with params String collection")
  @Test
  void testFirstWhenExists() {
    String hkey1 = "pepe1";
    String[] array1 = {"c", "d"};

    repository.add(collection, hkey1, array1);
    repository.add(collection, hkey, array);

    String response = repository.first(collection);

    assertEquals(hkey1, response);
    assertNotEquals(hkey, response);
  }

  @DisplayName("Test first when doesn't exists collection with params String collection")
  @Test
  void testFirstWhenDoesntExists() {
    String response = repository.first(collection);
    assertEquals(null, response);
  }

  @DisplayName("Test isAvailable")
  @Test
  void testIsAvailable() {
    Boolean response = repository.isAvailable();
    if (response) {
      assertTrue(response);
    } else {
      assertFalse(response);
    }
  }

  @DisplayName("Test any when collection exists")
  @Test
  void testAnyExists() {
    repository.add(collection, hkey, array);
    assertTrue(repository.any(collection));
  }

  @DisplayName("Test any when collection doesnt exists")
  @Test
  void testAnyDoesntExists() {
    assertFalse(repository.any(collection));
  }

  @DisplayName("Test hasKey when collection and key exists")
  @Test
  void testHasKeyExists() {
    repository.add(collection, hkey, array);
    assertTrue(repository.hasKey(collection, hkey));
  }

  @DisplayName("Test hasKey when collection exists but key doesnt")
  @Test
  void testHasntKeyExists() {
    String hkey1 = "pepe1";
    repository.add(collection, hkey1, array);

    assertFalse(repository.hasKey(collection, hkey));
  }
}
