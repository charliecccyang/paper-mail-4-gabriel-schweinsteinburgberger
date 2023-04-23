package com.spotlight.platform.userprofile.api.model.commands;

import com.spotlight.platform.userprofile.api.model.profile.UserProfile;
import com.spotlight.platform.userprofile.api.model.profile.primitives.UserProfileFixtures;
import com.spotlight.platform.userprofile.api.model.profile.primitives.UserProfilePropertyName;
import com.spotlight.platform.userprofile.api.model.profile.primitives.UserProfilePropertyValue;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.Map;

import static org.mockito.Mockito.*;

@DisplayName("CollectCommand tests")
class CollectCommandTest {
    @Test
    @DisplayName("apply() should call collectProperty() on the given UserProfile with the command's properties")
    void apply_callsCollectPropertyWithProperties() {
        // Given
        UserProfile userProfile = mock(UserProfile.class);
        Map<UserProfilePropertyName, UserProfilePropertyValue> properties = Collections.singletonMap(UserProfilePropertyName.valueOf("property"), UserProfilePropertyValue.valueOf("value"));
        CollectCommand command = new CollectCommand(UserProfileFixtures.USER_ID, properties);

        // When
        command.apply(userProfile);

        // Then
        verify(userProfile).collectProperty(UserProfilePropertyName.valueOf("property"), UserProfilePropertyValue.valueOf("value"));
    }
}
