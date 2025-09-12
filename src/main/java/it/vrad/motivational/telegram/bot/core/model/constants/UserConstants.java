package it.vrad.motivational.telegram.bot.core.model.constants;

import it.vrad.motivational.telegram.bot.core.model.enums.persistence.UserRole;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UserConstants {
    public static final List<UserRole> USER_NOT_VALID_ROLES = List.of(UserRole.KICKED, UserRole.BANNED);

}
