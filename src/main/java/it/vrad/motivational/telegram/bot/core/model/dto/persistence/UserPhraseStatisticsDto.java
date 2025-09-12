package it.vrad.motivational.telegram.bot.core.model.dto.persistence;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class UserPhraseStatisticsDto {
    private Long userId;
    private Long lessViewedPhraseId;
    private Long mostViewedPhraseId;
    private Integer totalPhrases;
}

