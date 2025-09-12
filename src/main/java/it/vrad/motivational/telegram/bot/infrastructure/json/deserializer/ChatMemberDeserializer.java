package it.vrad.motivational.telegram.bot.infrastructure.json.deserializer;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import it.vrad.motivational.telegram.bot.integration.telegram.TelegramConstants;
import it.vrad.motivational.telegram.bot.integration.telegram.model.response.ChatMember;
import it.vrad.motivational.telegram.bot.integration.telegram.model.response.ChatMemberAdministrator;
import it.vrad.motivational.telegram.bot.integration.telegram.model.response.ChatMemberBanned;
import it.vrad.motivational.telegram.bot.integration.telegram.model.response.ChatMemberLeft;
import it.vrad.motivational.telegram.bot.integration.telegram.model.response.ChatMemberMember;
import it.vrad.motivational.telegram.bot.integration.telegram.model.response.ChatMemberOwner;
import it.vrad.motivational.telegram.bot.integration.telegram.model.response.ChatMemberRestricted;

import java.io.IOException;

/**
 * Custom deserializer for {@code ChatMember} class.
 * Deserializes JSON into the correct ChatMember subclass based on the "status" field.
 */
public class ChatMemberDeserializer extends StdDeserializer<ChatMember> {
    // ObjectMapper used for deserialization of subclasses
    private final ObjectMapper mapper;

    /**
     * Constructor for ChatMemberDeserializer.
     * @param mapper ObjectMapper instance to use for deserialization
     */
    public ChatMemberDeserializer(ObjectMapper mapper) {
        super(ChatMember.class);
        this.mapper = mapper;
    }

    /**
     * Deserializes a JSON node into the appropriate ChatMember subclass
     * depending on the "status" field value.
     *
     * @param jp    the JSON parser
     * @param ctxt  the deserialization context
     * @return      the deserialized ChatMember instance
     * @throws IOException if deserialization fails
     */
    @Override
    public ChatMember deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException {
        JsonNode node = jp.getCodec().readTree(jp);

        // Switch on the "status" field to determine the correct subclass
        return switch (node.get("status").asText()) {
            case TelegramConstants.CHAT_MEMBER_ADMINISTRATOR_STATUS -> mapper.readValue(node.toString(), ChatMemberAdministrator.class);
            case TelegramConstants.CHAT_MEMBER_RESTRICTED_STATUS -> mapper.readValue(node.toString(), ChatMemberRestricted.class);
            case TelegramConstants.CHAT_MEMBER_KICKED_STATUS -> mapper.readValue(node.toString(), ChatMemberBanned.class);
            case TelegramConstants.CHAT_MEMBER_CREATOR_STATUS -> mapper.readValue(node.toString(), ChatMemberOwner.class);
            case TelegramConstants.CHAT_MEMBER_LEFT_STATUS -> mapper.readValue(node.toString(), ChatMemberLeft.class);
            case TelegramConstants.CHAT_MEMBER_MEMBER_STATUS -> mapper.readValue(node.toString(), ChatMemberMember.class);
            default -> null; // Unknown status returns null
        };
    }
}
