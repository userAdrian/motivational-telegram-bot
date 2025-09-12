package it.vrad.motivational.telegram.bot.integration.telegram.model.response;

import java.util.List;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class Poll {
    private String id;

    private String question;

    private List<PollOption> options;

    @JsonProperty("total_voter_count")
    private Integer totalVoterCount;

    @JsonProperty("is_closed")
    private boolean isClosed;

    @JsonProperty("is_anonymous")
    private boolean isAnonymous;

    private String type;

    @JsonProperty("allows_multiple_answers")
    private boolean allowsMultipleAnswers;

    @JsonProperty("correct_option_id")
    private Integer correctOptionId;

    private String explanation;

    @JsonProperty("explanation_entities")
    private List<MessageEntity> explanationEntities;

    @JsonProperty("open_period")
    private Integer openPeriod;

    @JsonProperty("close_date")
    private Integer closeDate;
}
