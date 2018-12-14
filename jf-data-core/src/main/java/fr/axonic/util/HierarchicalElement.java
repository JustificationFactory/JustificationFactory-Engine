package fr.axonic.util;

import java.util.function.Function;

public interface HierarchicalElement<T> {

    T getParent();

    void setParent(T parent);

    default <P, C extends HierarchicalElement<P>> C setParent(C oldChild, C newChild) {
        return setParent((P) this, oldChild, newChild);
    }

    static <P, C extends HierarchicalElement<P>> C setParent(P parent, C oldChild, C newChild) {
        if (oldChild != newChild) {
            if (oldChild != null) {
                oldChild.setParent(null);
            }
            newChild.setParent(parent);
        }
        return newChild;
    }

    default <P> P getAncestor(Class<P> ancestorClass) {
        P result = null;

        for (Object ancestor = this.getParent(); ancestor != null;) {

            if (ancestor.getClass().equals(ancestorClass)) {
                result = (P) ancestor;
                break;
            } else if (ancestor instanceof HierarchicalElement) {
                ancestor = ((HierarchicalElement) ancestor).getParent();
            } else {
                break;
            }
        }

        return result;
    }

    public static final String HIERARCHY_SEPARATOR = "\u25B6";

    default String getHierarchyString(Function<HierarchicalElement, String> function) {
        StringBuilder result = new StringBuilder();

        if (getParent() != null) {
            if (getParent() instanceof HierarchicalElement) {
                result.append(((HierarchicalElement) getParent()).getHierarchyString(function) + HIERARCHY_SEPARATOR);
            } else {
                result.append("#" + getParent().getClass().getSimpleName() + HIERARCHY_SEPARATOR);
            }
        }

        result.append(function.apply(this));

        return result.toString();
    }
}
