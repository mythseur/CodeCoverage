package filters;

import spoon.reflect.declaration.CtElement;

/**
 * Un filtre est utilisé pour spécifier un traitement avec un sous-type de CtElement
 * Par exemple, un filtre pour les if fera un traitement différent que pour un while
 */
public interface Filter {

    /**
     * Vérifie si l'élément est concerné par le filtre
     * @param ctElement élément à filtrer
     * @return vrai si l'élément est du type concerné par le filtre
     */
    boolean match(CtElement ctElement);

    /**
     * Applique l'instrumentation à l'élément
     * REQUIRES : match(ctElement)
     * @param ctElement élément à instrumenter
     */
    void apply(CtElement ctElement);
}
