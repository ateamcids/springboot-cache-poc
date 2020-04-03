package com.example.cachelibrary.services.impl;

import com.example.cachelibrary.repositories.interfaces.ICacheRepository;
import com.example.cachelibrary.util.strategy.reactive.ReactiveStrategyFactory;
import com.example.cachelibrary.util.strategy.sync.StrategyFactory;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;

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

  @Test
  void addReactive() {}

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
