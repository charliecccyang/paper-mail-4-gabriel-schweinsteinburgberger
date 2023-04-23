package com.spotlight.platform.userprofile.api.model.commands;

import com.spotlight.platform.userprofile.api.model.profile.UserProfile;
import com.spotlight.platform.userprofile.api.model.profile.primitives.UserId;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class UserProfileCommandTest {

    @Test
    void apply_callsSubclassMethod() {
        UserProfileCommand command = Mockito.mock(UserProfileCommand.class, Mockito.CALLS_REAL_METHODS);
        UserProfile userProfile = Mockito.mock(UserProfile.class);
        UserId userId = Mockito.mock(UserId.class);
        Mockito.when(command.getUserId()).thenReturn(userId);

        command.apply(userProfile);

        // verify that the apply method of the concrete subclass is called
        Mockito.verify(command, Mockito.times(1)).apply(userProfile);
    }
}
