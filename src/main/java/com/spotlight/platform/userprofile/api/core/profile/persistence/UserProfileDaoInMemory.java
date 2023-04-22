package com.spotlight.platform.userprofile.api.core.profile.persistence;

import com.spotlight.platform.userprofile.api.model.profile.UserProfile;
import com.spotlight.platform.userprofile.api.model.profile.primitives.UserId;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

public class UserProfileDaoInMemory implements UserProfileDao {
    private final Map<UserId, UserProfile> storage = new ConcurrentHashMap<>();

    @Override
    public Optional<UserProfile> get(UserId userId) {

        final UserProfile userProfile = storage.get(userId);
        if(userProfile != null){
            return Optional.of(userProfile);
        }

        final UserProfile newProfile = new UserProfile(userId, Instant.now(), new HashMap<>());
        put(newProfile);
        return Optional.of(newProfile);
    }

    @Override
    public void put(UserProfile userProfile) {
        storage.put(userProfile.userId(), userProfile);
    }
}
