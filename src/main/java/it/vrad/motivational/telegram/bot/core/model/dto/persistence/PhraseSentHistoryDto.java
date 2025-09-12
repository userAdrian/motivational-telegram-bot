package it.vrad.motivational.telegram.bot.core.model.dto.persistence;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;

@Builder
@Data
public class PhraseSentHistoryDto {
    private Long id;
    private Long phraseId;
    private Long userId;
    private OffsetDateTime sendingDate;
}
