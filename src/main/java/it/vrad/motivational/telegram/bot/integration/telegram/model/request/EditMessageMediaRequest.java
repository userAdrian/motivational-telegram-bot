package it.vrad.motivational.telegram.bot.integration.telegram.model.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import it.vrad.motivational.telegram.bot.integration.telegram.model.response.InlineKeyboardMarkup;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.core.io.FileSystemResource;
import org.springframework.util.MultiValueMap;

import java.io.File;
import java.util.List;

@Builder
@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class EditMessageMediaRequest extends AbstractTelegramRequest {
    //must be equal to the name of the field that contains the file
    private static final String FILE_TO_SEND_KEY = "fileToSend";

    @JsonProperty("chat_id")
    @NotNull
    private Long chatId;

    @JsonProperty("message_id")
    @NotNull
    private Long messageId;

    @JsonProperty("media")
    @NotNull
    private InputMedia inputMedia;

    private File fileToSend;

    @JsonProperty("reply_markup")
    private InlineKeyboardMarkup replyMarkup;

    /**
     * Converts this request into a {@link MultiValueMap} suitable for multipart/form-data requests.
     * <p>
     * If a file is attached (under the field {@link EditMessageMediaRequest#FILE_TO_SEND_KEY}), this method replaces the generic key
     * with the actual file name, as required by the Telegram Bot API for uploading files.
     * <p>
     * For more details, see the Telegram Bot API documentation:
     * <a href="https://core.telegram.org/bots/api#inputmedia">InputMedia</a>
     *
     * @return a {@link MultiValueMap} representing the request, with file keys adjusted for file uploads
     */
    public MultiValueMap<String, Object> asMultiValueMap() {
        MultiValueMap<String, Object> map = super.asMultiValueMap();

        //replace the key if a file exists
        List<Object> value = map.remove(FILE_TO_SEND_KEY);
        if (CollectionUtils.isNotEmpty(value)) {
            FileSystemResource file = (FileSystemResource) value.getFirst();
            map.add(file.getFilename(), file);
        }

        return map;
    }

}
