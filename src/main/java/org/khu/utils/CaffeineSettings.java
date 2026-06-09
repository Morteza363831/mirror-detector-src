package org.khu.utils;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

@Data
@Component
@ConfigurationProperties(prefix = "caffeine")
public class CaffeineSettings {

    private List<String> cacheNames;
}
