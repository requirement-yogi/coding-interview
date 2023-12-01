package hello.utils;

public interface Condition<T> {
    boolean filter(T item);
}
