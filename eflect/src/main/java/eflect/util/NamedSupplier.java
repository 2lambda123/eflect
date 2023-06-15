package eflect.util;

import java.util.function.Supplier;

public class NamedSupplier<T> implements Supplier<T> {
    final private String name;
    final private Supplier<T> inner;

    public NamedSupplier(String name, Supplier<T> inner) {
        this.name = name;
        this.inner = inner;
    }

    public String getName() {
        return name;
    }

    @Override
    public T get() {
        return inner.get();
    }
}
