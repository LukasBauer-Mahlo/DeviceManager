package de.bs1.landshut.device.database.function;

@FunctionalInterface
public interface ThrowableConsumer<I, T extends Throwable> {

  void accept(I i) throws T;

}
