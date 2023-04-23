package com.spotlight.platform.userprofile.api.model.commands;

import com.spotlight.platform.userprofile.api.model.profile.UserProfile;
import com.spotlight.platform.userprofile.api.model.profile.primitives.UserProfileFixtures;
import com.spotlight.platform.userprofile.api.model.profile.primitives.UserProfilePropertyName;
import com.spotlight.platform.userprofile.api.model.profile.primitives.UserProfilePropertyValue;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class IncrementCommandTest {

    @Test
    void apply_callsIncrementPropertyForEachProperty() {
        // Create a mock UserProfile object and define its behavior
        UserProfile userProfileMock = mock(UserProfile.class);
        doNothing().when(userProfileMock).incrementProperty(any(), any());

        // Create an IncrementCommand with some properties
        Map<UserProfilePropertyName, UserProfilePropertyValue> properties = new HashMap<>();
        properties.put(UserProfilePropertyName.valueOf("property1"), UserProfilePropertyValue.valueOf(10));
        properties.put(UserProfilePropertyName.valueOf("property2"), UserProfilePropertyValue.valueOf(5));
        IncrementCommand incrementCommand = new IncrementCommand(UserProfileFixtures.USER_ID, properties);

        // Apply the IncrementCommand to the mock UserProfile object
        incrementCommand.apply(userProfileMock);

        // Verify that the incrementProperty method was called once for each property
        verify(userProfileMock, times(properties.size())).incrementProperty(any(), any());
    }
}
