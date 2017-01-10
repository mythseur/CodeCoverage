package filters;

import commonSpoon.CoverageSpoon;
import spoon.processing.AbstractProcessor;
import spoon.reflect.code.CtForEach;
import spoon.reflect.code.CtStatement;
import spoon.reflect.declaration.CtElement;
import spoon.reflect.factory.CodeFactory;

public class ForEachFilter implements Filter {

    private final AbstractProcessor parentProcessor;

    public ForEachFilter(AbstractProcessor parentProcessor) {
        this.parentProcessor = parentProcessor;
    }

    @Override
    public boolean match(CtElement ctElement) {
        return ctElement instanceof CtForEach;
    }

    @Override
    public void apply(CtElement ctElement) {

        CodeFactory codeFactory = parentProcessor.getFactory().Code();

        CtForEach ctForEach = (CtForEach) ctElement;

        CtStatement statementToInsert = codeFactory.createCodeSnippetStatement(
                CoverageSpoon.registerLine(ctForEach.getBody().getPosition().getLine())
        );

        ctForEach.getBody().insertBefore(statementToInsert);
    }
}
