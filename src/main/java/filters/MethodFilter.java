package filters;

import Coverage.Coverage;
import spoon.processing.AbstractProcessor;
import spoon.reflect.code.CtStatement;
import spoon.reflect.declaration.CtElement;
import spoon.reflect.declaration.CtMethod;
import spoon.reflect.factory.CodeFactory;

/**
 * Filtre de méthode (CtMethod)
 * Insère une trace au début du corps de la méthode
 */
public class MethodFilter implements Filter {

    private final AbstractProcessor parentProcessor;

    public MethodFilter(AbstractProcessor parentProcessor) {
        this.parentProcessor = parentProcessor;
    }

    @Override
    public boolean match(CtElement ctElement) {
        return (ctElement instanceof CtMethod);
    }

    @Override
    public void apply(CtElement ctElement) {

        CodeFactory codeFactory = parentProcessor.getFactory().Code();

        CtStatement statementToInsert = codeFactory.createCodeSnippetStatement(
                Coverage.registerMethod(ctElement.getShortRepresentation())
        );

        CtMethod ctMethod = (CtMethod) ctElement;

        ctMethod.getBody().insertBegin(statementToInsert);

    }
}
