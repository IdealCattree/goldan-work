package com.ideal.golden.common.prop;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties("golden")
@Data
public class GoldenProperties {
    private PropConfig propConfig;
    private Upload upload;

    @Data
    public static class PropConfig {
        private String[] corsOrigins;
    }

    @Data
    public static class Upload {
        private String basePath;
        private String imgPath;
        private String filePath;
        private String videoPath;
    }
}
