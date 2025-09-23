package it.vrad.motivational.telegram.bot.core.functional;

@FunctionalInterface
public interface CheckedConsumer<T> {

    void accept(T t) throws Exception;
}
