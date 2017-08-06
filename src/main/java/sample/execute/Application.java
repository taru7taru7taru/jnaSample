package sample.execute;

import java.util.Arrays;

import org.apache.catalina.Context;
import org.apache.catalina.connector.Connector;
import org.apache.coyote.http11.AbstractHttp11Protocol;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.embedded.EmbeddedServletContainerFactory;
import org.springframework.boot.context.embedded.tomcat.TomcatContextCustomizer;
import org.springframework.boot.context.embedded.tomcat.TomcatEmbeddedServletContainerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Bean
    public CommandLineRunner commandLineRunner(ApplicationContext ctx) {
        return args -> {

            System.out.println("Let's inspect the beans provided by Spring Boot:");

            String[] beanNames = ctx.getBeanDefinitionNames();
            Arrays.sort(beanNames);
            for (String beanName : beanNames) {
                System.out.println(beanName);
            }

        };
    }
    
    /**
     * TOMCATのリクエストボディの最大サイズを変えるおまじない
     * 参考サイト：https://www.agilegroup.co.jp/technote/springboot-fileupload-error-handling.html
     * 			https://tomcat.apache.org/tomcat-8.5-doc/api/org/apache/coyote/http11/AbstractHttp11Protocol.html#setMaxSavePostSize(int)
     * 
     *  JsessionId の名前を変える？
     * 	http://qiita.com/uzresk/items/13228e86f20deda753a3
     * https://www.glamenv-septzen.net/view/1093#id52ac84
     * 
     * @return
     */
    @Bean
    public TomcatEmbeddedServletContainerFactory containerFactory() {
        return new TomcatEmbeddedServletContainerFactory() {
            @Override
            protected void customizeConnector(Connector connector) {
                super.customizeConnector(connector);
                if ((connector.getProtocolHandler() instanceof AbstractHttp11Protocol<?>)) {
                    ((AbstractHttp11Protocol<?>) connector.getProtocolHandler()).setMaxSwallowSize(-1);		//HandlerExceptionResolver の実装に必要　https://www.agilegroup.co.jp/technote/springboot-fileupload-error-handling.html
                    ((AbstractHttp11Protocol<?>) connector.getProtocolHandler()).setMaxSavePostSize(-1);	//こっちがmaxPostSizeかな？
                }
            }
        };
    }
    
/*    @Bean
    EmbeddedServletContainerFactory configure() {
        final TomcatEmbeddedServletContainerFactory factory = new TomcatEmbeddedServletContainerFactory();
        factory.addContextCustomizers(new TomcatContextCustomizer() {
            @Override
            public void customize(Context context) {
                context.setSessionCookieName("originalsessionName");
            }            
        });
        		
        return factory;
    }*/

}
