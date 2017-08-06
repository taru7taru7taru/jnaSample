package sample.execute.fileupload;

import javax.servlet.MultipartConfigElement;
import org.springframework.boot.context.embedded.MultipartConfigFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MultiPartConfigure {

    @Bean
    public MultipartConfigElement multipartConfigElement() {
        MultipartConfigFactory factory = new MultipartConfigFactory();
        factory.setMaxFileSize("3MB");
        factory.setMaxRequestSize("10MB");
        return factory.createMultipartConfig();
    }
}
