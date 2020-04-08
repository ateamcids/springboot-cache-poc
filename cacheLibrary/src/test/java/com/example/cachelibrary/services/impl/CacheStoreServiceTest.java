package com.example.cachelibrary.services.impl;

import com.example.cachelibrary.model.CacheResStatusDescripcionEnum;
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

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
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

    String collection = "http://pepe";
    String hkey = "pepe";
    String[] array = {"a", "b"};
    when(cacheRepository.add(collection, hkey, array)).thenReturn(true);

    this.store = new CacheStoreService(cacheRepository, strategyFactory, reactiveStrategyFactory);
  }

  @DisplayName("Test addCollection params String collection, String hkey, T object")
  @Test
  void addCollection() {
    String collection = "http://pepe";
    String hkey = "pepe";
    String[] array = {"a", "b"};

    assertTrue(this.store.addCollection(collection, hkey, array));
  }

  @Test
  void testAddCollection() {
    when(cacheRepository.add("http://pepe", "pepe", "objeto")).thenReturn(true);
  }

  @Test
  void testAddCollection1() {}

  @Test
  void add() {}

  @DisplayName("Test addReactive con eTag")
  @Test
  void addReactive() throws JsonProcessingException, InterruptedException {
    String[] array = {"uno", "dos", "tres"};
    String requestUrl = "http://localhost";
    CacheResponseStatus cacheResponseStatus =
        new CacheResponseStatus(CacheResStatusDescripcionEnum.APLICOESTRATEGIA.getDescripcion(), HttpStatus.OK, true);
    HttpHeaders headers = new HttpHeaders();
    headers.add("eTag", "3f5a37d9698744f3b40c89e2f0c94fb1");

    when(this.cacheRepository.addReactive(requestUrl, headers.getETag(), array))
        .thenReturn(Mono.just(true));
    when(this.cacheRepository.any(requestUrl))
            .thenReturn(false);
    assertEquals(cacheResponseStatus,this.store.addReactive(array, requestUrl, headers).block());
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
