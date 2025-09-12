package it.vrad.motivational.telegram.bot.core.model.dto.persistence;

import it.vrad.motivational.telegram.bot.core.model.enums.PhraseCSVHeader;
import it.vrad.motivational.telegram.bot.core.model.enums.persistence.PhraseType;
import lombok.Builder;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

@Slf4j
@Builder
@Data
public class PhraseDto implements Serializable {

    @Serial
    private static final long serialVersionUID = -1570658351614352970L;

    private Long id;
    private AuthorDto author;
    private String text;
    private PhraseType type;
    private Boolean disabled;
    private List<UserPhraseDto> userPhraseDtos;

    /**
     * Check if the phrase has all required fields
     * @return true if the phrase is valid, false otherwise
     */
    public boolean isValid(){
        if(author == null){
            log.warn("Phrase not valid: author is empty. {}", this);
            return false;
        }

        return author.isValid() && StringUtils.isNoneEmpty(text);
    }

    public String getAuthorFullName(){
        if(author != null){
            return author.getFullName();
        }

        return StringUtils.EMPTY;
    }

    public boolean isBiography(){
        return PhraseType.BIOGRAPHY.equals(type);
    }
}
