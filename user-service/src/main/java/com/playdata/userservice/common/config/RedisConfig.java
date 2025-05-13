package com.playdata.userservice.common.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
public class RedisConfig {

    @Value("${spring.data.redis.host}")
    private String host;

    @Value("${spring.data.redis.port}")
    private int port;

    // Redis 서버와의 연결을 설정하는 역할을 하는 RedisConnectionFactory
    // Redis 접속에 필요한 설정을 지정한 후 구현체를 빈으로 등록
    @Bean
    public RedisConnectionFactory redisConnectionFactory() {
        RedisStandaloneConfiguration configuration
                = new RedisStandaloneConfiguration();
        configuration.setHostName(host);
        configuration.setPort(port);
        configuration.setDatabase(1); // 1번 DB 사용하겠다. default -> 0
        return new LettuceConnectionFactory(configuration);
    }

    // spring과 redis가 상호작용할 때 redis key, value의 형식을 정의.
    @Bean
    public RedisTemplate<String, Object> redisTemplate(
            RedisConnectionFactory factory
    ) {
        RedisTemplate<String, Object> template = new RedisTemplate<>();

        // Redis의 키를 문자열로 직렬화 시키겠다.
        template.setKeySerializer(new StringRedisSerializer());
        // value는 JSON으로 직렬화 시키겠다.
        template.setValueSerializer(new GenericJackson2JsonRedisSerializer());
        // Redis 접속 정보를 template에게 전달해서 위에서 설정한 정보로 접속 후 사용.
        template.setConnectionFactory(factory);

        return template;
    }

}









