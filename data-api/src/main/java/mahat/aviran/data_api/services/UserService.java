package mahat.aviran.data_api.services;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import mahat.aviran.common.entities.dtos.UserDto;
import mahat.aviran.common.entities.persistence.PersistentUser;
import mahat.aviran.common.repositories.FollowRepository;
import mahat.aviran.common.repositories.UserRepository;
import mahat.aviran.data_api.dtos.PageDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static mahat.aviran.common.repositories.UserRepository.*;

@Service
@Getter @RequiredArgsConstructor
@Log4j2
public class UserService {

    private final UserRepository userRepository;
    private final FollowRepository followRepository;
    private final int PAGE_SIZE = 10;

    public PageDto<UserDto> getUsersByFilter(int page, String userName, String firstName, String lastName) {
        Specification<PersistentUser> specification = userNameStartsWith(userName)
                .and(firstNameStartsWith(firstName))
                .and(lastNameStartsWith(lastName));

        Page<UserDto> pageResult = this.userRepository
                .findAll(specification, PageRequest.of(page, PAGE_SIZE))
                .map(UserDto::from);

        return PageDto.from(pageResult);
    }

    public Optional<UserDto> getUserDetails(String username) {
        return this.userRepository.findById(username)
                .map(UserDto::from);
    }

    public Optional<PageDto<UserDto>> getUserFollowers(String username, int page) {
        if (!this.userRepository.existsById(username)) {
            return Optional.empty();
        }

        PersistentUser tempPersistentUser = new PersistentUser().setUserName(username);

        return Optional.of(this.followRepository.findAllByUsernameTo(tempPersistentUser, PageRequest.of(page, PAGE_SIZE))
                .map(FollowRepository.Follower::getUsernameFrom)
                .map(UserDto::from))
                .map(PageDto::from);
    }

    public Optional<PageDto<UserDto>> getUserFollowing(String username, int page) {
        if (!this.userRepository.existsById(username)) {
            return Optional.empty();
        }

        PersistentUser tempPersistentUser = new PersistentUser().setUserName(username);

        return Optional.of(this.followRepository.findAllByUsernameFrom(tempPersistentUser, PageRequest.of(page, PAGE_SIZE))
                .map(FollowRepository.Following::getUsernameTo)
                .map(UserDto::from))
                .map(PageDto::from);
    }

    public PageDto<UserDto> getFollowedUsers(int page, Sort.Direction direction) {
        Page<UserDto> sortedFollowedUsers = this.userRepository.findFollowedUsers(
                PageRequest.of(page, PAGE_SIZE, Sort.by(direction, "followed_users.count"))
        ).map(UserDto::from);

        return PageDto.from(sortedFollowedUsers);
    }
}
