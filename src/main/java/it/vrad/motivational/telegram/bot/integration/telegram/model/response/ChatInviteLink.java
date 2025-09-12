package it.vrad.motivational.telegram.bot.integration.telegram.model.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class ChatInviteLink {

    @JsonProperty("invite_link")
    private String inviteLink;

    private User creator;

    @JsonProperty("creates_join_request")
    private boolean createsJoinRequest;

    @JsonProperty("is_primary")
    private boolean isPrimary;

    @JsonProperty("is_revoked")
    private boolean isRevoked;

    private String name;

    @JsonProperty("expire_date")
    private String expireDate;

    @JsonProperty("member_limit")
    private Integer memberLimit;

    @JsonProperty("pending_join_request_count")
    private Integer pendingJoinRequestCount;
}