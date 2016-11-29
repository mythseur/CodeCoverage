package filters;

import Coverage.Coverage;
import spoon.processing.AbstractProcessor;
import spoon.reflect.code.CtFor;
import spoon.reflect.code.CtStatement;
import spoon.reflect.declaration.CtElement;
import spoon.reflect.factory.CodeFactory;

public class ForFilter implements Filter {

    private final AbstractProcessor parentProcessor;

    public ForFilter(AbstractProcessor parentProcessor) {
        this.parentProcessor = parentProcessor;
    }

    @Override
    public boolean match(CtElement ctElement) {
        return ctElement instanceof CtFor;
    }

    @Override
    public void apply(CtElement ctElement) {

        CodeFactory codeFactory = parentProcessor.getFactory().Code();

        CtFor ctFor = (CtFor) ctElement;

        CtStatement statementToInsert = codeFactory.createCodeSnippetStatement(
                Coverage.registerLine(ctFor.getBody().getPosition().getLine())
        );

        ctFor.getBody().insertBefore(statementToInsert);
    }
}
