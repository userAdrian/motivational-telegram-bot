package it.vrad.motivational.telegram.bot.integration.telegram.model.response;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class PollAnswer {
	@JsonProperty("poll_id")
	private String pollId;

	@JsonProperty("voter_chat")
	private Chat voterChat;

	private User user;

	@JsonProperty("option_ids")
	private List<Integer> optionIds;

}
