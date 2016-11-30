package filters;

import commonSpoon.CoverageSpoon;
import spoon.processing.AbstractProcessor;
import spoon.reflect.code.CtDo;
import spoon.reflect.code.CtStatement;
import spoon.reflect.declaration.CtElement;
import spoon.reflect.factory.CodeFactory;

public class DoWhileFilter implements Filter {

    private final AbstractProcessor parentProcessor;

    public DoWhileFilter(AbstractProcessor parentProcessor) {
        this.parentProcessor = parentProcessor;
    }

    @Override
    public boolean match(CtElement ctElement) {
        return ctElement instanceof CtDo;
    }

    @Override
    public void apply(CtElement ctElement) {

        CodeFactory codeFactory = parentProcessor.getFactory().Code();

        CtDo ctDo = (CtDo) ctElement;

        CtStatement statementToInsert = codeFactory.createCodeSnippetStatement(
                CoverageSpoon.registerLine(ctDo.getBody().getPosition().getLine())
        );

        ctDo.getBody().insertBefore(statementToInsert);
    }
}
