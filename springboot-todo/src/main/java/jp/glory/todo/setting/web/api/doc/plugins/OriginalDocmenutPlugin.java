package jp.glory.todo.setting.web.api.doc.plugins;

import java.util.Optional;

import org.springframework.stereotype.Component;

import jp.glory.todo.context.base.web.api.OriginalOperationDoc;
import jp.glory.todo.setting.web.api.doc.plugins.operation.OriginalOperationDocBuilder;
import jp.glory.todo.setting.web.api.doc.plugins.request.OriginalRequestDocBuilder;
import springfox.documentation.builders.OperationBuilder;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.OperationBuilderPlugin;
import springfox.documentation.spi.service.contexts.OperationContext;

/**
 * 独自ドキュメントプラグイン.
 * @author Junki Yamada
 */
@Component
public class OriginalDocmenutPlugin implements OperationBuilderPlugin {

    @Override
    public boolean supports(final DocumentationType docType) {

        return true;
    }

    @Override
    public void apply(final OperationContext context) {

        final OperationBuilder builder = context.operationBuilder();

        final Optional<OriginalOperationDoc> optOpe = Optional.ofNullable(context.findAnnotation(OriginalOperationDoc.class).orNull());
        optOpe.ifPresent(v -> new OriginalOperationDocBuilder(v).build(builder));

        final OriginalRequestDocBuilder requestBuilder = new OriginalRequestDocBuilder(context.getParameters());
        requestBuilder.build(builder);

    }
}
