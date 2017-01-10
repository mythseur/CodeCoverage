package filters;

import commonSpoon.CoverageSpoon;
import spoon.processing.AbstractProcessor;
import spoon.reflect.code.CtAssignment;
import spoon.reflect.code.CtReturn;
import spoon.reflect.code.CtStatement;
import spoon.reflect.declaration.CtElement;
import spoon.reflect.factory.CodeFactory;

public class MoreStatementFilter implements Filter {

    private final AbstractProcessor parentProcessor;

    public MoreStatementFilter(AbstractProcessor parentProcessor) {
        this.parentProcessor = parentProcessor;
    }


    @Override
    public boolean match(CtElement ctElement) {
        return ctElement instanceof CtAssignment
                || ctElement instanceof CtReturn;
    }

    @Override
    public void apply(CtElement ctElement) {

        CodeFactory codeFactory = parentProcessor.getFactory().Code();

        CtStatement ctStatement = (CtStatement) ctElement;

        CtStatement statementToInsert = codeFactory.createCodeSnippetStatement(
                CoverageSpoon.registerLine(ctStatement.getPosition().getLine())
        );

        ctStatement.insertBefore(statementToInsert);
    }
}
