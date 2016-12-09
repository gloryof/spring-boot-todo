package jp.glory.framework.doc.api.plugins.request;

import org.springframework.stereotype.Component;

import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.OperationBuilderPlugin;
import springfox.documentation.spi.service.contexts.OperationContext;

/**
 * リクエストの独自ドキュメントプラグイン.
 * @author Junki Yamada
 */
@Component
public class OriginalRequestDocPlugin implements OperationBuilderPlugin {

    @Override
    public boolean supports(final DocumentationType docType) {

        return true;
    }

    @Override
    public void apply(final OperationContext context) {

        final OriginalRequestDocBuilder requestBuilder = new OriginalRequestDocBuilder(context.getParameters());
        context.operationBuilder().parameters(requestBuilder.build());
    }
}