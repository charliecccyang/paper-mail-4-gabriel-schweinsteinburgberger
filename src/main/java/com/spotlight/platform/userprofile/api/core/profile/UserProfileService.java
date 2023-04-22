package com.spotlight.platform.userprofile.api.core.profile;

import com.spotlight.platform.userprofile.api.core.exceptions.EntityNotFoundException;
import com.spotlight.platform.userprofile.api.core.profile.persistence.UserProfileDao;
import com.spotlight.platform.userprofile.api.model.commands.UserProfileCommand;
import com.spotlight.platform.userprofile.api.model.profile.UserProfile;
import com.spotlight.platform.userprofile.api.model.profile.primitives.UserId;

import javax.inject.Inject;
import java.time.Instant;
import java.util.HashMap;

public class UserProfileService {
    private final UserProfileDao userProfileDao;

    @Inject
    public UserProfileService(UserProfileDao userProfileDao) {
        this.userProfileDao = userProfileDao;
    }

    public UserProfile get(UserId userId) {
        return userProfileDao.get(userId).orElseThrow(EntityNotFoundException::new);
    }

    public void applyCommand(UserProfileCommand command) {
        UserProfile userProfile = userProfileDao.get(command.getUserId()).orElseGet(() -> {
            UserProfile newProfile = new UserProfile(command.getUserId(), Instant.now(), new HashMap<>());
            userProfileDao.put(newProfile);
            return newProfile;
        });

        command.apply(userProfile);
        userProfileDao.put(userProfile);
    }
}
