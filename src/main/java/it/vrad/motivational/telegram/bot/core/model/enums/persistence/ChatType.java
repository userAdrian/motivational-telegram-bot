package it.vrad.motivational.telegram.bot.core.model.enums.persistence;

import lombok.Getter;

@Getter
public enum ChatType {
    PRIVATE("private");

    private final String value;

    ChatType(String value){
        this.value = value;
    }

    public static ChatType fromValue(String value){
        for(ChatType chatType : ChatType.values()){
            if(chatType.getValue().equalsIgnoreCase(value)){
                return chatType;
            }
        }

        throw new IllegalArgumentException("No ChatType enum found for value: " + value);
    }

}
