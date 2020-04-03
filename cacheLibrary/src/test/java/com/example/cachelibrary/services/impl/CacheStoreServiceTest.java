package com.example.cachelibrary.services.impl;

import com.example.cachelibrary.model.CacheResponseStatus;
import com.example.cachelibrary.repositories.interfaces.ICacheRepository;
import com.example.cachelibrary.util.strategy.reactive.ReactiveStrategyFactory;
import com.example.cachelibrary.util.strategy.sync.StrategyFactory;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import reactor.core.publisher.Mono;

import java.util.Date;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.booleanThat;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
class CacheStoreServiceTest {

  @MockBean ICacheRepository cacheRepository;

  @MockBean StrategyFactory strategyFactory;

  @MockBean ReactiveStrategyFactory reactiveStrategyFactory;

  CacheStoreService store;

  @BeforeEach
  void init() {
    System.out.println("BeforeEach initEach() method called");

    this.store = new CacheStoreService(cacheRepository, strategyFactory, reactiveStrategyFactory);
  }

  @DisplayName("Test addCollection params String collection, String hkey, T object")
  @Test
  void addCollection() {
    String collection = "http://pepe";
    String hkey = "pepe";
    String[] array = {"a", "b"};

    when(cacheRepository.add(collection, hkey, array)).thenReturn(true);
    assertTrue(this.store.addCollection(collection, hkey, array));
  }

  @DisplayName("Test addCollection params String collection, String hkey, T object, Date")
  @Test
  void AddCollectionDate() {
    String collection = "http://pepe";
    String hkey = "pepe";
    String[] array = {"a", "b"};
    Date date = new Date();

    when(cacheRepository.add(collection, hkey, array, date)).thenReturn(true);
    assertTrue(this.store.addCollection(collection, hkey, array, date));
  }

  @Test
  void AddCollectionTimeOut() {
    String collection = "http://pepe";
    String hkey = "pepe";
    String[] array = {"a", "b"};
    int timeout = 30;
    TimeUnit unit = TimeUnit.SECONDS;

    when(cacheRepository.add(collection, hkey, array, timeout, unit)).thenReturn(true);
    assertTrue(this.store.addCollection(collection, hkey, array, timeout, unit));
  }

  @Test
  void add() {}

  @DisplayName("Test addReactive con eTag")
  @Test
  void addReactive() throws JsonProcessingException, InterruptedException {
    String[] array = {"uno", "dos", "tres"};
    String requestUrl = "http://localhost";
    CacheResponseStatus cacheResponseStatus =
        new CacheResponseStatus("hola", HttpStatus.NOT_MODIFIED, true);
    HttpHeaders headers = new HttpHeaders();
    headers.add("eTag", "3f5a37d9698744f3b40c89e2f0c94fb1");
    assertThrows(
        NullPointerException.class,
        () -> {
          when(this.cacheRepository.addReactive(requestUrl, headers.getETag(), array).block())
              .thenReturn(Mono.just(true));
        });
    assertThrows(
        NullPointerException.class,
        () -> {
          assertEquals(
              cacheResponseStatus, this.store.addReactive(array, requestUrl, headers).block());
        });
  }

  @Test
  void addReactiveCollection() {}

  @Test
  void testAddReactiveCollection() {}

  @Test
  void find() {}

  @Test
  void testFind() {}

  @Test
  void first() {}

  @Test
  void findReactive() {}
}
