package com.example.cachelibrary.services.impl;

import com.example.cachelibrary.model.CacheResStatusDescripcionEnum;
import com.example.cachelibrary.model.CacheResponseStatus;
import com.example.cachelibrary.repositories.interfaces.ICacheRepository;
import com.example.cachelibrary.util.strategy.model.CacheControlStrategyResponse;
import com.example.cachelibrary.util.strategy.reactive.ReactiveStrategyFactory;
import com.example.cachelibrary.util.strategy.sync.CacheControlEnum;
import com.example.cachelibrary.util.strategy.sync.StrategyFactory;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
class CacheStoreServiceTest {

    @MockBean
    ICacheRepository cacheRepository;

    @MockBean
    StrategyFactory strategyFactory;

    @MockBean
    ReactiveStrategyFactory reactiveStrategyFactory;

    CacheStoreService store;

    @BeforeEach
    void init() {
        System.out.println("BeforeEach initEach() method called");
        strategyFactory= new StrategyFactory();
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

    @DisplayName("Test addReactive con eTag")
    @Test
    void add() throws JsonProcessingException, InterruptedException {
        List list = new ArrayList();
        list.add("objeto 1");
        String requestUrl = "http://localhost";
        CacheResponseStatus cacheResponseStatus =
                new CacheResponseStatus(CacheResStatusDescripcionEnum.APLICOESTRATEGIA.getDescripcion(), HttpStatus.OK, true);
        HttpHeaders headers = new HttpHeaders();
        headers.add("eTag", "3f5a37d9698744f3b40c89e2f0c94fb1");
        when(this.cacheRepository.add(requestUrl, headers.getETag(), list)).thenReturn(true);
        assertEquals(cacheResponseStatus,this.store.add(list,requestUrl,headers));

        HttpHeaders headersCacheControl = new HttpHeaders();
        headersCacheControl.add("Cache-Control", "max-age=30");
        when(this.cacheRepository.add(requestUrl, requestUrl, list)).thenReturn(true);
        when(this.strategyFactory.getStrategy(CacheControlEnum.getByCode("max-age=30")));
        //assertEquals(cacheResponseStatus,this.store.add(list,requestUrl,headersCacheControl));
    }

    @Test
    void addReactiveMaxAge() {
        /*String[] array = {"uno", "dos", "tres"};
        String requestUrl = "http://localhost";
        CacheResponseStatus cacheResponseStatus =
                new CacheResponseStatus("hola", HttpStatus.NOT_MODIFIED, true);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Cache-Control", "max-age=30");
        assertThrows(
                NullPointerException.class,
                () -> {
                    when(this.cacheRepository.addReactive(requestUrl, requestUrl, array).block())
                            .thenReturn(Mono.just(true));
                });
        assertThrows(
                NullPointerException.class,
                () -> {
                   // assertEquals(
                    //        cacheResponseStatus, this.store.addReactive(array, requestUrl, headers).block());

                    System.out.println();
                    System.out.println("-----------------------"+this.store.addReactive(array, requestUrl, headers).block());
                });*/
    }

    @DisplayName("Test addReactive con eTag")
    @Test
    void addReactive() throws JsonProcessingException, InterruptedException {
        /*String[] array = {"uno", "dos", "tres"};
        String requestUrl = "http://localhost";
        CacheResponseStatus cacheResponseStatus =
                new CacheResponseStatus(CacheResStatusDescripcionEnum.APLICOESTRATEGIA.getDescripcion(), HttpStatus.OK, true);
        HttpHeaders headers = new HttpHeaders();
        headers.add("eTag", "3f5a37d9698744f3b40c89e2f0c94fb1");
        when(this.cacheRepository.addReactive(requestUrl, headers.getETag(), array))
                .thenReturn(Mono.just(true));
        when(this.cacheRepository.any(requestUrl))
                .thenReturn(false);
        assertEquals(cacheResponseStatus,this.store.addReactive(array, requestUrl, headers).block());*/
    }

    @Test
    void testAddReactiveCollection() {
    }

    @Test
    void find() {
        String collection = "http://pepe";
        String hkey = "pepe";
        List list = new ArrayList();
        list.add("objeto 1");
        list.add("objeto 2");
        list.add("objeto 3");

        when(cacheRepository.find(collection, hkey, List.class)).thenReturn(list);
        assertEquals(list, this.store.find(collection, hkey, List.class));
    }

    @Test
    void findColecction() {
        String collection = "http://pepe";
        List list = new ArrayList();
        list.add("objeto 1");
        list.add("objeto 2");
        list.add("objeto 3");
        when(cacheRepository.find(collection, List.class)).thenReturn(list);
        assertEquals(list, this.store.find(collection, List.class));
    }

    @Test
    void first() {
        String collection = "http://pepe";
        when(cacheRepository.first(collection)).thenReturn("primer elemento");
        assertNotNull(this.store.first(collection));
    }

    @Test
    void findReactive() {
        String collection = "http://pepe";
        String hkey = "pepe";
        List list = new ArrayList();
        list.add("objeto 1");
        list.add("objeto 2");
        list.add("objeto 3");

        when(cacheRepository.findReactive(collection, hkey, List.class)).thenReturn(Mono.just(list));
        assertNotNull(this.store.findReactive(collection, hkey, List.class));

    }
}
