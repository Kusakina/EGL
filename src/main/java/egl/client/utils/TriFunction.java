package egl.client.utils;

/**
 * https://stackoverflow.com/a/19649473
 */
@FunctionalInterface
public
interface TriFunction<A,B,C,R> {

    R apply(A a, B b, C c);
}