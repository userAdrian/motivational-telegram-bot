package it.vrad.motivational.telegram.bot.core.model.dto.persistence;

import lombok.Builder;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

@Builder
@Data
public class AuthorDto implements Serializable {

    @Serial
    private static final long serialVersionUID = -5154052113031375438L;

    private Long id;
    private String firstName;
    private String lastName;

    private List<PhraseDto> phraseDtos;

    public boolean isValid(){
        return StringUtils.isNoneEmpty(firstName, lastName);
    }

    public String getFullName(){
        return firstName + " " + lastName;
    }
}

