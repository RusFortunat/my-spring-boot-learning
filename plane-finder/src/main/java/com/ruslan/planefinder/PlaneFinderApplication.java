package com.ruslan.planefinder;

import com.ruslan.planefinder.client.PlaneFinderPollerRedisImpl;
import com.ruslan.planefinder.entity.Aircraft;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.JacksonJsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@SpringBootApplication
@ComponentScan(
    excludeFilters = @ComponentScan.Filter(
        type = FilterType.ASSIGNABLE_TYPE,
        classes = { PlaneFinderPollerRedisImpl.class } // the bean you want to disable
    )
)
public class PlaneFinderApplication {

//    @Bean
//    public RedisOperations<String, Aircraft> redisOperations(RedisConnectionFactory redisConnectionFactory) {
//        JacksonJsonRedisSerializer<Aircraft> serializer = new  JacksonJsonRedisSerializer<>(Aircraft.class);
//
//        RedisTemplate<String, Aircraft> redisTemplate = new RedisTemplate<>();
//        redisTemplate.setConnectionFactory(redisConnectionFactory);
//        redisTemplate.setDefaultSerializer(serializer);
//        redisTemplate.setKeySerializer(new StringRedisSerializer());
//
//        return redisTemplate;
//    }

	public static void main(String[] args) {
		SpringApplication.run(PlaneFinderApplication.class, args);
	}

}
