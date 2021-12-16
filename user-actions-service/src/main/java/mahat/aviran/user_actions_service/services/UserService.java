package mahat.aviran.user_actions_service.services;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import mahat.aviran.common.entities.dtos.UserDto;
import mahat.aviran.common.entities.persistence.PersistentUser;
import mahat.aviran.common.repositories.UserRepository;
import mahat.aviran.user_actions_service.entities.UpdateUserRequest;
import mahat.aviran.user_actions_service.utils.exceptions.UnauthorizedActionException;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;

@Service
@Getter @RequiredArgsConstructor
@Log4j2
public class UserService {

    private final UserRepository userRepository;

    public UserDto updateUserDetails(UpdateUserRequest updateUserRequest, String requestingUserName) {
        if (!this.userRepository.existsById(requestingUserName) ||
            !this.userRepository.existsById(updateUserRequest.getUserName())) {
            throw new EntityNotFoundException();
        }

        if (!requestingUserName.equals(updateUserRequest.getUserName())) {
            throw new UnauthorizedActionException();
        }

        PersistentUser updatedUser = new PersistentUser()
                .setUserName(updateUserRequest.getUserName())
                .setFirstName(updateUserRequest.getFirstName())
                .setLastName(updateUserRequest.getLastName());
        this.userRepository.save(updatedUser);

        return UserDto.from(updatedUser);
    }
}
