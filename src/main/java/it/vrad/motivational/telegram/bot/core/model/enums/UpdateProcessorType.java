package it.vrad.motivational.telegram.bot.core.model.enums;

public enum UpdateProcessorType {
    MESSAGE,
    CALLBACK_QUERY,
    CHAT_MEMBER_UPDATED;

    //the processor bean name MUST be the same as the enum name
    public static final String MESSAGE_PROCESSOR_BEAN_NAME = "MESSAGE";
    public static final String CALLBACK_QUERY_PROCESSOR_BEAN_NAME = "CALLBACK_QUERY";
    public static final String CHAT_MEMBER_UPDATED_PROCESSOR_BEAN_NAME = "CHAT_MEMBER_UPDATED";

}
