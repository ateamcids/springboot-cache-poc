package com.teco.cachelibrary.services.interfaces;

import com.teco.cachelibrary.model.CacheResponseStatus;
import com.fasterxml.jackson.core.JsonProcessingException;
import java.util.Date;
import java.util.concurrent.TimeUnit;
import org.springframework.http.HttpHeaders;
import reactor.core.publisher.Mono;

/**
 * Clase definida como interface, se encaraga de comunicarse con el repository de redis.
 *
 * @param <T> objeto de tipo generico .
 */
public interface ICacheStoreService<T> {
  /**
   * Solicita información al repositorio de redis para obtener los datos de una determinada
   * colección y un objeto en particular de esa colección indentificado por una clave.
   *
   * @param collection Nombre de la colección que se quiere buscar
   * @param hkey Clave del objeto perteneciente a la colección que se va a buscar
   * @param tclass Modelo de dato del objeto
   * @return T Objeto de tipo generico .
   */
  T find(String collection, String hkey, Class<T> tclass);

  /**
   * Solicita información al repositorio de redis para obtener todos los datos de una determinada
   * colección .
   *
   * @param collection Nombre de la colección
   * @param tclass Modelo de datos a recuperar.
   * @return T Objeto de tipo generico .
   */
  T find(String collection, Class<T> tclass);

  /**
   * Pedirle al repositorio de redis que obtenga el primer dato de la coleccion (key de cada
   * coleccion).
   *
   * @param collection Nombre de la coleccion
   * @return String del hkey encontrado .
   */
  public String first(String collection);

  /**
   * Le envia datos al repositorio de redis para agregar un nuevo dato.
   *
   * @param collection Nombre con el que se guardara la coleccion en redis
   * @param hkey Clave que identifica a cada dato que se guarda en la colección
   * @param object Que se quiere guardar en redis
   * @return boolean que muestra si se guardo correctamente o no .
   */
  boolean addCollection(String collection, String hkey, T object);

  /**
   * Le envia datos al repositorio de redis para agregar un nuevo dato con un tiempo maximo de
   * expiracion .
   *
   * @param collection Nombre con el que se guardara la colección en redis
   * @param hkey Clave que indetifica a cada dato que se guarda en la coleccón
   * @param object Que se quiere guardar en redis
   * @param timeout Tiempo de expiración de la colección en memoria
   * @param unit Unidad de tiempo
   * @return boolean que muestra si se guardo correctamente o no .
   */
  boolean addCollection(String collection, String hkey, T object, int timeout, TimeUnit unit);

  /**
   * Le envia datos al repositorio de redis para agregar un nuevo dato con una fecha maxima de
   * expiracion .
   *
   * @param collection Nombre con el que se guardara la colección en redis
   * @param hkey Clave que indetifica a cada dato que se guarda en la coleccón
   * @param object Que se quiere guardar en redis
   * @param date fecha en que expiran los datos de la colección
   * @return boolean que muestra si se guardo correctamente o no .
   */
  boolean addCollection(String collection, String hkey, T object, Date date);

  /**
   * Le envia datos al repositorio de redis para guardar nuevos datos de forma sincronica, este
   * método resuelve la strategia de cache control a utilizar .
   *
   * @param object Que se va a guardar a en redis
   * @param requestUrl Nombre con el que se guardara la colección
   * @param headers Encabezado de la petición
   * @return Modelo de datos definido en el objeto CacheResponseStatus .
   */
  CacheResponseStatus add(T object, String requestUrl, HttpHeaders headers) throws JsonProcessingException, InterruptedException;

  /**
   * Solicita información de manera Reactiva al repositorio de redis para obtener todos los datos de
   * una determinada colección .
   *
   * @param collection Nombre de la colección que se quiere buscar
   * @param hkey Clave del objeto perteneciente a la colección que se va a buscar
   * @param tclass Modelo de datos a recuperar.
   * @return Mono de tipo generico del objeto encontrado .
   */
  Mono<T> findReactive(String collection, String hkey, Class<T> tclass);

  /**
   * Le envia datos al repositorio de redis para guardar nuevos datos de forma Reactiva, este método
   * resuelve la strategia de cache control .
   *
   * @param object Que se va a guardar a en redis
   * @param requestUrl Nombre con el que se guardara la colección
   * @param headers Encabezado de la petición http
   * @return Mono de tipo CacheResponseStatus .
   */
  Mono<CacheResponseStatus> addReactive(T object, String requestUrl, HttpHeaders headers)
      throws JsonProcessingException, InterruptedException;

  /**
   * Envia datos al repositorio de redis para guardar de forma Reactiva una nueva colección .
   *
   * @param object Que se va a guardar a en redis
   * @param requestUrl Nombre con el que se guardara la colección
   * @param headers Encabezado de la petición http
   * @return Mono de tipo boolean si se guardo correctamente o no .
   */
  Mono<Boolean> addReactiveCollection(T object, String requestUrl, HttpHeaders headers)
      throws JsonProcessingException, InterruptedException;

  /**
   * Envia datos al repositorio de redis para guardar de forma Reactiva una nueva colección con un
   * tiempo máximo de expiración .
   *
   * @param object Que se va a guardar a en redis
   * @param requestUrl Nombre con el que se guardara la colección
   * @param headers Encabezado de la petición http
   * @param timeOut Tiempo de expiración
   * @return Mono de tipo booleano si se guardo correctamente o no
   */
  Mono<Boolean> addReactiveCollection(T object, String requestUrl, HttpHeaders headers, int timeOut)
      throws JsonProcessingException, InterruptedException;
}
