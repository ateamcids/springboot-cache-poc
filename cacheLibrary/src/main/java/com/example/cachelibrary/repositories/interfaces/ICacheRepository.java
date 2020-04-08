package com.example.cachelibrary.repositories.interfaces;

import com.fasterxml.jackson.core.JsonProcessingException;
import java.util.Date;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

/**
 *    En esta clase se definen los metodos de acceso a Redis
 *    asi como tambien operaciones basicas.
 *
 */
@Repository
public interface ICacheRepository<T> {

  /**
   *  Añadir objeto dentro de Redis.
   *
   * @param collection Nombre de la coleccion a guardar.
   * @param hkey  Llave para identificar el objeto dentro de la coleccion.
   * @param object Objeto a guardar.
   * @return Boolean que informa si se guardo o no.
   */
  boolean add(String collection, String hkey, T object);

  /**
   *  Añadir objeto dentro de Redis con un tiempo determinado de expiracion.
   *
   * @param collection Nombre de la coleccion a guardar.
   * @param hkey  Llave para identificar el objeto dentro de la coleccion.
   * @param object Objeto a guardar.
   * @param timeout Tiempo que el objeto va a durar en memoria.
   * @param unit Unidad de tiempo.
   * @return Boolean que informa si se guardo o no.
   */
  boolean add(String collection, String hkey, T object, int timeout, TimeUnit unit);

  /**
   *  Añadir objeto dentro de Redis con una fecha y momento determinado de expiracion.
   *
   * @param collection Nombre de la coleccion a guardar.
   * @param hkey  Llave para identificar el objeto dentro de la coleccion.
   * @param object Objeto a guardar.
   * @param date  Fecha y momento de expiracion.
   * @return Boolean que informa si se guardo o no.
   */
  boolean add(String collection, String hkey, T object, Date date);

  /**
   *  Añadir objeto dentro de Redis de manera reactiva.
   *
   * @param collection Nombre de la coleccion a guardar.
   * @param hkey  Llave para identificar el objeto dentro de la coleccion.
   * @param object Objeto a guardar.
   * @return Un mono de tipo boolean que informa si se guardo o no.
   */
  Mono<Boolean> addReactive(String collection, String hkey, T object)
      throws JsonProcessingException, InterruptedException;

  /**
   *  Añadir objeto dentro de Redis de manera reactiva con un tiempo determinado de expiracion.
   *
   * @param collection Nombre de la coleccion a guardar.
   * @param hkey  Llave para identificar el objeto dentro de la coleccion.
   * @param object Objeto a guardar.
   * @param timeout Tiempo que el objeto va a durar en memoria EN SEGUNDOS.
   * @return Un mono de tipo boolean que informa si se guardo o no.
   */
  Mono<Boolean> addReactive(String collection, String hkey, T object, int timeout)
      throws JsonProcessingException, InterruptedException;

  /**
   *  Buscar un objeto dentro de redis de forma reactiva.
   *
   * @param collection Nombre de la coleccion a buscar.
   * @param hkey  Llave para identificar el objeto dentro de la coleccion.
   * @param tclass  Modelo de datos de objeto a buscar.?
   * @return  Mono<T> del objeto buscado.
   */
  Mono<T> findReactive(String collection, String hkey, Class<T> tclass);

  /**
   *  Eliminar una coleccion.
   *
   * @param collection Nombre de la coleccion a eliminar.
   * @return  Boolean que informa si se elimino o no.
   */
  boolean delete(String collection);

  /**
   *  Elimina un objeto dentro de la coleccion con esa llave.
   *
   * @param collection  Nombre de la coleccion a eliminar.
   * @param hkey  Nombre de la llave que identifica el objeto.
   * @return  Boolean que informa si se elimino o no.
   */
  long delete(String collection, String hkey);

  /**
   *  Eliminar una coleccion en forma reactiva.
   *
   * @param collection Nombre de la coleccion a eliminar.
   * @return  Mono<Long> la cantidad de colecciones eliminadas.
   */
  Mono<Long> deleteReactive(String collection);

  /**
   *  Buscar un objeto determinado dentro de redis por medio de una llave y coleccion.
   *
   * @param collection Nombre de la coleccion a buscar.
   * @param hkey  Llave para identificar el objeto dentro de la coleccion.
   * @param tclass  Modelo de datos de objeto a buscar.
   * @return  El objeto buscado.
   */
  T find(String collection, String hkey, Class<T> tclass);

  /**
   *  Buscar objeto por medio de una coleccion.
   *
   * @param collection Nombre de la coleccion a buscar.
   * @param tclass Modelo de datos de objeto a buscar.
   * @return  Los objeto buscado.
   */
  T find(String collection, Class<T> tclass);

  /**
   * Testear colexion a redis.
   *
   * @return  Un bolean que informa si hay o no conexion con redis.
   */
  Boolean isAvailable();

  /**
   *  Informar si existe o no una coleccion.
   *
   * @param collection  Nombre de la coleccion a buscar.
   * @return  Un bolean que informa si existe o no esa coleccion.
   */
  boolean any(String collection);

  /**
   *  Informar si existe una llave en una determinada coleccion.
   *
   * @param collection  Nombre de la coleccion.
   * @param hkey  LLave a buscar dentro de la coleccion.
   * @return  Un bolean que informa si existe o no esa llave.
   */
  boolean hasKey(String collection, String hkey);

  /**
   *  Obtener el primer objeto dentro de la coleccion.
   *
   * @param collection  Nombre de la coleccion.
   * @return  String con el primer hkey de la coleccion.
   */
  String first(String collection);
}
