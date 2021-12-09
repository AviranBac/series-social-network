package mahat.aviran.user_actions_service.services;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import mahat.aviran.common.entities.persistence.PersistentFollow;
import mahat.aviran.common.repositories.FollowRepository;
import mahat.aviran.common.repositories.UserRepository;
import mahat.aviran.user_actions_service.dtos.FollowDto;
import mahat.aviran.user_actions_service.utils.exceptions.UsernameFromNotFoundException;
import mahat.aviran.user_actions_service.utils.exceptions.UsernameToNotFoundException;
import org.springframework.stereotype.Service;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;

@Service
@Getter @RequiredArgsConstructor
@Log4j2
public class FollowService {

    private final FollowRepository followRepository;
    private final UserRepository userRepository;

    @Transactional
    public FollowDto addFollow(String usernameFrom, String usernameTo) throws EntityNotFoundException, EntityExistsException {
        this.validateUserInput(usernameFrom, usernameTo);

        PersistentFollow follow = this.followRepository.findFollow(usernameFrom, usernameTo);

        if (follow != null) {
            throw new EntityExistsException();
        }

        follow = new PersistentFollow()
                .setUsernameFrom(this.userRepository.findById(usernameFrom).get())
                .setUsernameTo(this.userRepository.findById(usernameTo).get());
        return this.convertPersistentFollowToDto(this.followRepository.save(follow));
    }

    @Transactional
    public FollowDto removeFollow(String usernameFrom, String usernameTo) throws EntityNotFoundException {
        this.validateUserInput(usernameFrom, usernameTo);

        PersistentFollow follow = this.followRepository.findFollow(usernameFrom, usernameTo);

        if (follow == null) {
            throw new EntityNotFoundException();
        }

        this.followRepository.delete(follow);
        return this.convertPersistentFollowToDto(follow);
    }

    private void validateUserInput(String usernameFrom, String usernameTo) {
        if (this.userRepository.existsById(usernameFrom)) {
            throw new UsernameFromNotFoundException();
        }

        if (this.userRepository.existsById(usernameTo)) {
            throw new UsernameToNotFoundException();
        }
    }

    private FollowDto convertPersistentFollowToDto(PersistentFollow persistentFollow) {
        return new FollowDto()
                .setFrom(persistentFollow.getUsernameFrom().getUserName())
                .setTo(persistentFollow.getUsernameTo().getUserName());
    }
}
