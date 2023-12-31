package de.bs1.landshut.device.database.function;

@FunctionalInterface
public interface ThrowableFunction<I, O, T extends Throwable> {

  O apply(I i) throws T;

}
