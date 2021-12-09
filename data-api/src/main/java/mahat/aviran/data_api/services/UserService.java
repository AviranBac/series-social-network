package mahat.aviran.data_api.services;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import mahat.aviran.data_api.dtos.PageDto;
import mahat.aviran.common.entities.dtos.UserDto;
import mahat.aviran.common.entities.persistence.PersistentUser;
import mahat.aviran.common.repositories.FollowRepository;
import mahat.aviran.common.repositories.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Getter @RequiredArgsConstructor
@Log4j2
public class UserService {

    private final UserRepository userRepository;
    private final FollowRepository followRepository;
    private final int PAGE_SIZE = 10;

    public Optional<UserDto> getUserDetails(String username) {
        return this.userRepository.findById(username)
                .map(this::convertUserToDto);
    }

    public Optional<PageDto<UserDto>> getUserFollowers(String username, int page) {
        if (!this.userRepository.existsById(username)) {
            return Optional.empty();
        }

        PersistentUser tempPersistentUser = new PersistentUser().setUserName(username);

        return Optional.of(this.followRepository.findAllByUsernameTo(tempPersistentUser, PageRequest.of(page, PAGE_SIZE))
                .map(FollowRepository.Follower::getUsernameFrom)
                .map(this::convertUserToDto))
                .map(this::convertPageToDto);
    }

    public Optional<PageDto<UserDto>> getUserFollowing(String username, int page) {
        if (!this.userRepository.existsById(username)) {
            return Optional.empty();
        }

        PersistentUser tempPersistentUser = new PersistentUser().setUserName(username);

        return Optional.of(this.followRepository.findAllByUsernameFrom(tempPersistentUser, PageRequest.of(page, PAGE_SIZE))
                .map(FollowRepository.Following::getUsernameTo)
                .map(this::convertUserToDto))
                .map(this::convertPageToDto);
    }

    private UserDto convertUserToDto(PersistentUser persistentUser) {
        return new UserDto()
                .setUserName(persistentUser.getUserName())
                .setFirstName(persistentUser.getFirstName())
                .setLastName(persistentUser.getLastName());
    }

    private <T> PageDto<T> convertPageToDto(Page<T> page) {
        return new PageDto<T>()
                .setContent(page.getContent())
                .setTotalPages(page.getTotalPages())
                .setTotalElements(page.getTotalElements());
    }
}
