package it.vrad.motivational.telegram.bot.core.functional;

@FunctionalInterface
public interface CheckedFunction<T, R> {

    R accept(T t) throws Exception;
}
