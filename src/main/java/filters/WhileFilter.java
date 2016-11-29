package filters;

import Coverage.Coverage;
import spoon.processing.AbstractProcessor;
import spoon.reflect.code.CtStatement;
import spoon.reflect.code.CtWhile;
import spoon.reflect.declaration.CtElement;
import spoon.reflect.factory.CodeFactory;

public class WhileFilter implements Filter {

    private final AbstractProcessor parentProcessor;

    public WhileFilter(AbstractProcessor parentProcessor) {
        this.parentProcessor = parentProcessor;
    }

    @Override
    public boolean match(CtElement ctElement) {
        return ctElement instanceof CtWhile;
    }

    @Override
    public void apply(CtElement ctElement) {

        CodeFactory codeFactory = parentProcessor.getFactory().Code();

        CtWhile ctWhile = (CtWhile) ctElement;

        CtStatement statementWhile = codeFactory.createCodeSnippetStatement(
                Coverage.registerLine(ctWhile.getPosition().getLine())
        );

        ctWhile.getBody().insertBefore(statementWhile);
    }
}
