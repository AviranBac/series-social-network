package mahat.aviran.user_actions_service.services;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import mahat.aviran.common.entities.dtos.UserDto;
import mahat.aviran.common.entities.persistence.PersistentUser;
import mahat.aviran.common.repositories.FollowRepository;
import mahat.aviran.common.repositories.UserRepository;
import mahat.aviran.user_actions_service.entities.UserCredentials;
import mahat.aviran.user_actions_service.entities.UserRegistrationDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.persistence.EntityExistsException;
import javax.transaction.Transactional;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Getter @RequiredArgsConstructor
@Log4j2
public class UserService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final FollowRepository followRepository;

    public boolean isUserAuthenticated(UserCredentials credentials) {
        Optional<PersistentUser> optionalUser = this.findUserById(credentials.getUserName());

        return optionalUser
                .filter(persistentUser -> passwordEncoder.matches(credentials.getPassword(), persistentUser.getPassword()))
                .isPresent();
    }

    public Optional<UserDto> findUser(String userName) {
        return this.findUserById(userName).map(this::convertPersistentUserToDto);
    }

    @Transactional
    public UserDto saveUser(UserRegistrationDetails userRegistrationDetails) throws EntityExistsException {
        Optional<PersistentUser> optionalPersistentUser = this.findUserById(userRegistrationDetails.getUserName());
        if (optionalPersistentUser.isPresent()) {
            throw new EntityExistsException();
        }

        PersistentUser persistentUser = new PersistentUser()
                .setUserName(userRegistrationDetails.getUserName())
                .setFirstName(userRegistrationDetails.getFirstName())
                .setLastName(userRegistrationDetails.getLastName())
                .setPassword(passwordEncoder.encode(userRegistrationDetails.getPassword()));

        return this.convertPersistentUserToDto(userRepository.save(persistentUser));
    }

    public Optional<PersistentUser> findUserById(String userName) {
        return this.userRepository.findById(userName);
    }

    private UserDto convertPersistentUserToDto(PersistentUser persistentUser) {
        return this.convertPersistentUserToDtoWithoutRelations(persistentUser)
                .setFollowers(this.followRepository.findAllByUsernameFrom(persistentUser).stream()
                                .map(FollowRepository.Following::getUsernameTo)
                                .map(this::convertPersistentUserToDtoWithoutRelations)
                                .collect(Collectors.toList()))
                .setFollowing(this.followRepository.findAllByUsernameTo(persistentUser).stream()
                                .map(FollowRepository.Follower::getUsernameFrom)
                                .map(this::convertPersistentUserToDtoWithoutRelations)
                                .collect(Collectors.toList()));
    }

    private UserDto convertPersistentUserToDtoWithoutRelations(PersistentUser persistentUser) {
        return new UserDto()
                .setUserName(persistentUser.getUserName())
                .setFirstName(persistentUser.getFirstName())
                .setLastName(persistentUser.getLastName());
    }
}
