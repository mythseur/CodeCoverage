package processors;

import filters.*;
import spoon.processing.AbstractProcessor;
import spoon.reflect.declaration.CtClass;
import spoon.reflect.declaration.CtElement;
import spoon.reflect.visitor.filter.TypeFilter;

import java.util.Arrays;
import java.util.List;

public class ClassProcessor extends AbstractProcessor<CtClass<?>> {

    private List<Filter> filters = Arrays.asList(
            new ClassFilter(this),
            new MethodFilter(this),
            new IfFilter(this),
            new WhileFilter(this),
            new ForFilter(this),
            new DoWhileFilter(this)
    );

    @Override
    public void process(CtClass<?> ctClass) {
        //Pour tous les éléments d'une classe, on l'instrumente
        ctClass.getElements(new TypeFilter<>(CtElement.class))
               .forEach(this::instrument);

        System.out.println(ctClass);
    }

    private void instrument(CtElement statement) {
        //Choix d'un filtre pour l'élément. Il dépend du type de l'élément
        Filter filter = filters.stream()
                               .filter(f -> f.match(statement))
                               .findFirst()
                               .orElse(new DoNothingFilter());

        //Application du filtre
        filter.apply(statement);
    }
}
