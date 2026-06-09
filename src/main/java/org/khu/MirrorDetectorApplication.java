package org.khu;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class MirrorDetectorApplication {

    public static void main(String[] args) {
        SpringApplication.run(MirrorDetectorApplication.class, args);
    }

}
