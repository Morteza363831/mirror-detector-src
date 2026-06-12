package org.khu.utils;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "launch-pad")
public class LauncePadSettings {

    private String baseUrl;

    private String mirrorType;

    private String mirrorWsOp;
}
