package filters;


import coverage.Coverage;
import spoon.processing.AbstractProcessor;
import spoon.reflect.code.CtIf;
import spoon.reflect.code.CtStatement;
import spoon.reflect.declaration.CtElement;
import spoon.reflect.factory.CodeFactory;

public class IfFilter implements Filter {

    private final AbstractProcessor parentProcessor;

    public IfFilter(AbstractProcessor parentProcessor) {
        this.parentProcessor = parentProcessor;
    }

    @Override
    public boolean match(CtElement ctElement) {
        return ctElement instanceof CtIf;
    }

    @Override
    public void apply(CtElement ctElement) {

        CodeFactory codeFactory = parentProcessor.getFactory().Code();

        CtIf ctIf = (CtIf) ctElement;

        CtStatement statementThen = codeFactory.createCodeSnippetStatement(
                Coverage.registerLine(ctIf.getThenStatement().getPosition().getLine())
        );
        ctIf.getThenStatement().insertBefore(statementThen);


        if(ctIf.getElseStatement() != null && !(ctIf.getElseStatement() instanceof CtIf)){
            //on ne traite pas else if comme un else

            CtStatement statementElse = codeFactory.createCodeSnippetStatement(
                    Coverage.registerLine(ctIf.getElseStatement().getPosition().getLine())
            );
            ctIf.getElseStatement().insertBefore(statementElse);
        }
    }
}
