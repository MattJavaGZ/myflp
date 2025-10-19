package matt.pas.myflp.domain.user;

import matt.pas.myflp.domain.user.dto.UserCreditialsDto;
import matt.pas.myflp.domain.user.dto.UserDto;

public class UserMapper {

    public static UserCreditialsDto mapUserToUserCreditialsDto(User user) {
        return new UserCreditialsDto(
                user.getEmail(),
                user.getPassword(),
                user.getRoles().stream()
                        .map(UserRole::getName)
                        .toList()
        );
    }

    public static UserDto mapUserToUserDto(User user) {
        return new UserDto(
                user.getId(),
                user.getFirstName(),
                user.getLastName(),
                user.getEmail(),
                user.isActiv(),
                user.getDateAdded(),
                user.getWorkStation().getName()
        );
    }

}
