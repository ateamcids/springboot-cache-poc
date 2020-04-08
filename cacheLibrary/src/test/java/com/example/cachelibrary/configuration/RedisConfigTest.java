package com.example.cachelibrary.configuration;

import io.lettuce.core.ClientOptions;
import io.lettuce.core.resource.ClientResources;
import io.lettuce.core.resource.DefaultClientResources;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.connection.ReactiveRedisConnectionFactory;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettucePoolingClientConfiguration;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.StringRedisSerializer;

/**
 * RedisConfig Esta clase se encarga de la creación de la conexión hacia Redis mediante las
 * propiedades declaradas en el application.properties. Utiliza el conector Lettuce
 */
@TestConfiguration
public class RedisConfigTest {
    /** Host de Redis. */
    private String redisHost="localhost";

    /** Puerto de Redis. */
    private int redisPort=6379;

    /**
     * Inyección y creación de clientResources.
     *
     * @return ClientResources instanciados de manera default, teniendo como destrucción cuando se
     *     llama shutdown.
     */
    @Qualifier("this")
    @Bean(destroyMethod = "shutdown")
    ClientResources clientResources() {
        return DefaultClientResources.create();
    }

    /**
     * Creación de la configuración de Redis, generando un objeto con las credenciales.
     *
     * @return RedisStandaloneConfiguration.
     */
    @Bean
    public RedisStandaloneConfiguration redisStandaloneConfiguration() {
        return new RedisStandaloneConfiguration(redisHost, redisPort);
    }

    /**
     * Declaración de las opciones de los clientes dentro de una conexión hacia redis con Lettuce.
     *
     * @return Genera un objeto ClientOptions con todas las opciones de los clientes.
     */
    @Bean
    public ClientOptions clientOptions() {
        return ClientOptions.builder()
                .disconnectedBehavior(ClientOptions.DisconnectedBehavior.REJECT_COMMANDS)
                .autoReconnect(true)
                .build();
    }

    /**
     * Creación del pool de conexiones de lettuce.
     *
     * @param options ClientOptions declaradas anteriormente.
     * @param dcr ClientResources declaradas anteriormente.
     * @return
     */
    @Bean
    LettucePoolingClientConfiguration lettucePoolConfig(
            ClientOptions options, @Qualifier("this") ClientResources dcr) {
        return LettucePoolingClientConfiguration.builder()
                .poolConfig(new GenericObjectPoolConfig())
                .clientOptions(options)
                .clientResources(dcr)
                .build();
    }

    /**
     * Factory de la conexión con Lettuce.
     *
     * @param redisStandaloneConfiguration parametro inyectado de la configuración de Redis generada
     *     en esta misma clase.
     * @param lettucePoolConfig parametro inyectado de creación del pool de conexión de clientes de
     *     lettuce hacia Redis.
     * @return LettuceConnectionFactory nueva conexión de lettuce.
     */
    @Bean
    @Primary
    public RedisConnectionFactory connectionFactory(
            RedisStandaloneConfiguration redisStandaloneConfiguration,
            LettucePoolingClientConfiguration lettucePoolConfig) {
        return new LettuceConnectionFactory(redisStandaloneConfiguration, lettucePoolConfig);
    }

    /**
     * Factory de la conexión con Lettuce de manera Reactiva La implementación del método es similar
     * pero instancia dos objetos distintos para hacer en tiempo de ejecución consultas síncronas y
     * reactivas.
     *
     * @param redisStandaloneConfiguration parametro inyectado de la configuración de Redis generada
     *     en esta misma clase.
     * @param lettucePoolConfig creación del pool de conexión de clientes de lettuce hacia Redis
     * @return LettuceConnectionFactory nueva conexión de lettuce.
     */
    @Bean
    @Qualifier("reactiveConnectionFactory")
    public ReactiveRedisConnectionFactory reactiveConnectionFactory(
            RedisStandaloneConfiguration redisStandaloneConfiguration,
            LettucePoolingClientConfiguration lettucePoolConfig) {
        return new LettuceConnectionFactory(redisStandaloneConfiguration, lettucePoolConfig);
    }

    /**
     * Genera un punto de acceso a Redis mediante un RedisTemplate Reactivo, utilizado como método de
     * serialización el string.
     *
     * @param redisConnectionFactory utiliza las conexiones reactivas a redis.
     * @return
     */
    @Bean
    @ConditionalOnMissingBean(name = "reactiveRedisTemplate")
    @Primary
    public ReactiveRedisTemplate<Object, Object> reactiveRedisTemplate(
            @Qualifier("reactiveConnectionFactory")
                    ReactiveRedisConnectionFactory redisConnectionFactory) {
        return new ReactiveRedisTemplate(redisConnectionFactory, RedisSerializationContext.string());
    }

    /**
     * Genera un punto de acceso a Redis mediante un RedisTemplate sincrono, utilizado como método de
     * serialización el string.
     *
     * @param redisConnectionFactory utiliza las conexiones a redis.
     * @return
     */
    @Bean
    @ConditionalOnMissingBean(name = "redisTemplate")
    @Primary
    public RedisTemplate<Object, Object> redisTemplate(
            RedisConnectionFactory redisConnectionFactory) {
        RedisTemplate<Object, Object> template = new RedisTemplate();
        template.setConnectionFactory(redisConnectionFactory);
        template.setDefaultSerializer(new StringRedisSerializer());
        return template;
    }
}
