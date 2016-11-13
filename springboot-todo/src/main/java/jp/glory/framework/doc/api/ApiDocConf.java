package jp.glory.framework.doc.api;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RestController;

import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * APIドキュメントの設定ファイル
 * @author Junki Yamada
 *
 */
@Configuration
@EnableSwagger2
public class ApiDocConf {

    /**
     * APIドキュメント設定.
     * @return ドキュメント設定
     */
    @Bean
    public Docket apiDoc() {

        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                    .apis(RequestHandlerSelectors.withClassAnnotation(RestController.class))
                    .build()
                .useDefaultResponseMessages(false);
    }
}
