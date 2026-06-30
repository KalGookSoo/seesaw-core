package kr.me.seesaw.core.support.hierarchy;

import org.springframework.lang.Nullable;

public interface Hierarchical<T extends Hierarchical<T, ID>, ID> {

    ID getId();

    @Nullable
    ID getParentId();

    void addChild(T child);

    default boolean isRoot() {
        return getParentId() == null;
    }

    default boolean hasParent() {
        return !isRoot();
    }

}
