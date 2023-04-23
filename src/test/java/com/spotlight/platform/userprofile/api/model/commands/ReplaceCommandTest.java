package com.spotlight.platform.userprofile.api.model.commands;

import com.spotlight.platform.userprofile.api.model.profile.UserProfile;
import com.spotlight.platform.userprofile.api.model.profile.primitives.UserProfileFixtures;
import com.spotlight.platform.userprofile.api.model.profile.primitives.UserProfilePropertyName;
import com.spotlight.platform.userprofile.api.model.profile.primitives.UserProfilePropertyValue;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.mockito.Mockito.*;

class ReplaceCommandTest {

    @Test
    void apply_callsReplacePropertyOnUserProfile() {
        // Arrange
        UserProfile mockUserProfile = mock(UserProfile.class);
        UserProfilePropertyName propertyName1 = UserProfilePropertyName.valueOf("property1");
        UserProfilePropertyName propertyName2 = UserProfilePropertyName.valueOf("property2");
        UserProfilePropertyValue propertyValue1 = UserProfilePropertyValue.valueOf("value1");
        UserProfilePropertyValue propertyValue2 = UserProfilePropertyValue.valueOf("value2");
        Map<UserProfilePropertyName, UserProfilePropertyValue> properties = new HashMap<>();
        properties.put(propertyName1, propertyValue1);
        properties.put(propertyName2, propertyValue2);
        ReplaceCommand replaceCommand = new ReplaceCommand(UserProfileFixtures.USER_ID, properties);

        // Act
        replaceCommand.apply(mockUserProfile);

        // Assert
        verify(mockUserProfile).replaceProperty(propertyName1, propertyValue1);
        verify(mockUserProfile).replaceProperty(propertyName2, propertyValue2);
    }
}
