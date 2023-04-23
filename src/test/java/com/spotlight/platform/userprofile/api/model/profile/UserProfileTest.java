package com.spotlight.platform.userprofile.api.model.profile;

import com.spotlight.platform.userprofile.api.model.profile.primitives.UserProfileFixtures;

import com.spotlight.platform.userprofile.api.model.profile.primitives.UserProfilePropertyName;
import com.spotlight.platform.userprofile.api.model.profile.primitives.UserProfilePropertyValue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import static net.javacrumbs.jsonunit.assertj.JsonAssertions.assertThatJson;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class UserProfileTest {

    @Test
    void serialization_WorksAsExpected() {
        assertThatJson(UserProfileFixtures.USER_PROFILE).isEqualTo(UserProfileFixtures.SERIALIZED_USER_PROFILE);
    }

    private UserProfile userProfile;
    private Instant latestUpdateTime;
    private Map<UserProfilePropertyName, UserProfilePropertyValue> userProfileProperties;

    @BeforeEach
    void setUp() {
        latestUpdateTime = Instant.now();
        userProfileProperties = new HashMap<>();
        userProfile = new UserProfile(UserProfileFixtures.USER_ID, latestUpdateTime, userProfileProperties);
    }

    @Test
    @DisplayName("replaceProperty should replace an existing property or add a new property")
    void replaceProperty() {
        UserProfilePropertyName propertyName = UserProfilePropertyName.valueOf("test-property");
        UserProfilePropertyValue propertyValue = UserProfilePropertyValue.valueOf(42);

        userProfile.replaceProperty(propertyName, propertyValue);

        assertThat(userProfile.getUserProfileProperties()).containsEntry(propertyName, propertyValue);
    }

    @Test
    @DisplayName("incrementProperty should increment an existing property or set a new property to the incrementValue if the property does not exist")
    void incrementProperty() {
        UserProfilePropertyName propertyName = UserProfilePropertyName.valueOf("test-property");
        UserProfilePropertyValue initialValue = UserProfilePropertyValue.valueOf(42);
        UserProfilePropertyValue incrementValue = UserProfilePropertyValue.valueOf(10);
        userProfileProperties.put(propertyName, initialValue);

        userProfile.incrementProperty(propertyName, incrementValue);

        assertThat(userProfile.getUserProfileProperties()).containsEntry(propertyName, UserProfilePropertyValue.valueOf(52));
    }

    @Test
    public void testCollectNonListProperty() {
        UserProfilePropertyName propertyName = UserProfilePropertyName.valueOf("property");
        UserProfilePropertyValue propertyValue = UserProfilePropertyValue.valueOf(42);

        UserProfile userProfile = new UserProfile(UserProfileFixtures.USER_ID, Instant.now(), Collections.emptyMap());

        Exception exception = assertThrows(UnsupportedOperationException.class, () -> {
            userProfile.collectProperty(propertyName, propertyValue);
        });

        String expectedMessage = "Value is not a List.";
        String actualMessage = exception.getMessage();

        assertEquals(expectedMessage, actualMessage);
    }
}
