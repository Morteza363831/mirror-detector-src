package org.khu.config;

import com.github.benmanes.caffeine.cache.Caffeine;
import lombok.RequiredArgsConstructor;
import org.khu.utils.CaffeineSettings;
import org.springframework.cache.CacheManager;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.TimeUnit;

@Configuration
@RequiredArgsConstructor
public class CaffeineConfig {

    // tools
    private final CaffeineSettings caffeineSettings;


    @Bean
    public Caffeine<Object, Object> caffeine() {

        return Caffeine.newBuilder()
                .maximumSize(10000)
                .expireAfterWrite(24, TimeUnit.HOURS);
    }

    @Bean
    public CacheManager cacheManager(Caffeine<Object, Object> caffeine) {

        CaffeineCacheManager caffeineCacheManager = new CaffeineCacheManager();

        caffeineCacheManager.setCacheNames(caffeineSettings.getCacheNames());

        caffeineCacheManager.setCaffeine(caffeine);
        return caffeineCacheManager;
    }

}
