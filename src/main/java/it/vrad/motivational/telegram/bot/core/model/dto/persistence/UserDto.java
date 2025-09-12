package it.vrad.motivational.telegram.bot.core.model.dto.persistence;


import it.vrad.motivational.telegram.bot.core.model.constants.UserConstants;
import it.vrad.motivational.telegram.bot.core.model.enums.persistence.UserRole;
import lombok.Builder;
import lombok.Data;
import org.apache.commons.collections4.CollectionUtils;

import java.util.List;
import java.util.Set;

@Data
@Builder
public class UserDto {

    private Long id;
    private Long telegramId;
    private UserRole userRole;

    private ChatDto chatDto;
    private List<UserPhraseDto> userPhraseDtos;

    public boolean isAdmin() {
        return UserRole.ADMIN.equals(userRole);
    }

    public boolean isNotAdmin() {
        return !isAdmin();
    }

    public boolean isValid() {
        return !isInvalid();
    }
    public boolean isInvalid() {
        return UserConstants.USER_NOT_VALID_ROLES.contains(userRole);
    }

    public boolean isAuthorized(Set<UserRole> userRoles){
        return matchesAnyRole(userRoles);
    }

    public boolean isUnauthorized(Set<UserRole> userRoles){
        return !isAuthorized(userRoles);
    }

    public boolean matchesAnyRole(Set<UserRole> allowedRoles){
        if (CollectionUtils.isEmpty(allowedRoles)) {
            return false;
        }
        if (allowedRoles.contains(UserRole.ALL)) {
            return true;
        }
        return allowedRoles.contains(userRole);
    }

    public boolean matchesNoRole(Set<UserRole> userRoles){
        return !matchesAnyRole(userRoles);
    }

}
