package com.ideal.golden.prop;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties("golden")
@Data
public class GoldenProperties {
    private PropConfig propConfig;

    @Data
    public static class PropConfig {
        private String[] corsOrigins;
    }
}
