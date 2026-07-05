package org.khu.config;

import com.github.benmanes.caffeine.cache.Caffeine;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.khu.utils.CaffeineSettings;
import org.springframework.cache.CacheManager;
import org.springframework.cache.caffeine.CaffeineCacheManager;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CaffeineConfigTest {

    private CaffeineSettings caffeineSettings;

    private CaffeineConfig config;

    @BeforeEach
    void setUp() {

        caffeineSettings = mock(CaffeineSettings.class);

        when(caffeineSettings.getCacheNames())
                .thenReturn(List.of("countries", "distributions", "mirrors"));

        config = new CaffeineConfig(caffeineSettings);
    }

    @Test
    void shouldCreateCaffeineBean() {

        Caffeine<Object, Object> caffeine = config.caffeine();

        assertNotNull(caffeine);
    }

    @Test
    void shouldCreateCacheManager() {

        Caffeine<Object, Object> caffeine = config.caffeine();

        CacheManager manager = config.cacheManager(caffeine);

        assertNotNull(manager);
        assertInstanceOf(CaffeineCacheManager.class, manager);

        CaffeineCacheManager cacheManager = (CaffeineCacheManager) manager;

        assertNotNull(cacheManager.getCache("countries"));
        assertNotNull(cacheManager.getCache("distributions"));
        assertNotNull(cacheManager.getCache("mirrors"));

        verify(caffeineSettings).getCacheNames();
    }
}