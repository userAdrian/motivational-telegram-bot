package it.vrad.motivational.telegram.bot.core.model;

import it.vrad.motivational.telegram.bot.core.model.dto.persistence.PhraseDto;
import lombok.Builder;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

@Builder
@Data
public class StatisticsPageDetails implements Serializable {
    @Serial
    private static final long serialVersionUID = 5343558189377694241L;

    private int totalPhrases;
    private PhraseDto lessViewedPhrase;
    private PhraseDto mostViewedPhrase;
}
