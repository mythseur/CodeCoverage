package filters;

import Coverage.Coverage;
import spoon.processing.AbstractProcessor;
import spoon.reflect.declaration.CtClass;
import spoon.reflect.declaration.CtElement;
import spoon.reflect.declaration.CtField;
import spoon.reflect.factory.CodeFactory;

public class ClassFilter implements Filter {

    private final AbstractProcessor<CtClass<?>> parentProcessor;

    public ClassFilter(AbstractProcessor<CtClass<?>> parentProcessor) {
        this.parentProcessor = parentProcessor;
    }

    @Override
    public boolean match(CtElement ctElement) {
        return ctElement instanceof CtClass;
    }

    @Override
    public void apply(CtElement ctElement) {
        CodeFactory codeFactory = parentProcessor.getFactory().Code();

        CtClass ctClass = (CtClass) ctElement;

        CtField newField = Coverage.registerClass(codeFactory);

        ctClass.addField(newField);
    }
}
