package com.ultimate;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

import com.ultimate.properties.FileUploadProperties;

@SpringBootApplication
@EnableConfigurationProperties({FileUploadProperties.class})
public class DownuploadApplication {

    public static void main(String[] args) {
        SpringApplication.run(DownuploadApplication.class, args);
    }

}
