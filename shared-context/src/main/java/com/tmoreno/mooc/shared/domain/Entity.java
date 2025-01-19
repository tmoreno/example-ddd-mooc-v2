package com.tmoreno.mooc.shared.domain;

import java.util.Objects;

public abstract class Entity<I extends Identifier> {
    protected final I id;

    public Entity(I id) {
        this.id = id;
    }

    public final I getId() {
        return id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Entity<?> entity = (Entity<?>) o;
        return Objects.equals(id, entity.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
