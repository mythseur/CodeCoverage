package filters;

import spoon.reflect.declaration.CtElement;

public class DoNothingFilter implements filters.Filter {
    @Override
    public boolean match(CtElement ctElement) {
        return false;
    }

    @Override
    public void apply(CtElement ctElement) {
        //Do nothing
    }
}
