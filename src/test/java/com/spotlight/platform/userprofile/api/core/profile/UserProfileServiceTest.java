package com.spotlight.platform.userprofile.api.core.profile;

import com.spotlight.platform.userprofile.api.core.exceptions.EntityNotFoundException;
import com.spotlight.platform.userprofile.api.core.profile.persistence.UserProfileDao;
import com.spotlight.platform.userprofile.api.core.profile.persistence.UserProfileDaoInMemory;
import com.spotlight.platform.userprofile.api.model.commands.CollectCommand;
import com.spotlight.platform.userprofile.api.model.commands.IncrementCommand;
import com.spotlight.platform.userprofile.api.model.commands.ReplaceCommand;
import com.spotlight.platform.userprofile.api.model.commands.UserProfileCommands;
import com.spotlight.platform.userprofile.api.model.profile.UserProfile;
import com.spotlight.platform.userprofile.api.model.profile.primitives.UserId;
import com.spotlight.platform.userprofile.api.model.profile.primitives.UserProfileFixtures;

import com.spotlight.platform.userprofile.api.model.profile.primitives.UserProfilePropertyName;
import com.spotlight.platform.userprofile.api.model.profile.primitives.UserProfilePropertyValue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.time.Instant;
import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class UserProfileServiceTest {
    private final UserProfileDao userProfileDaoMock = mock(UserProfileDao.class);
    private final UserProfileService userProfileService = new UserProfileService(userProfileDaoMock);

    @Nested
    @DisplayName("get")
    class Get {
        @Test
        void getForExistingUser_returnsUser() {
            when(userProfileDaoMock.get(any(UserId.class))).thenReturn(Optional.of(UserProfileFixtures.USER_PROFILE));

            assertThat(userProfileService.get(UserProfileFixtures.USER_ID)).usingRecursiveComparison()
                    .isEqualTo(UserProfileFixtures.USER_PROFILE);
        }

        @Test
        void getForNonExistingUser_throwsException() {
            when(userProfileDaoMock.get(any(UserId.class))).thenReturn(Optional.empty());

            assertThatThrownBy(() -> userProfileService.get(UserProfileFixtures.USER_ID)).isExactlyInstanceOf(
                    EntityNotFoundException.class);
        }
    }

    @BeforeEach
    public void setUp() {
        // Create initial UserProfile
        UserProfile initialProfile = new UserProfile(UserProfileFixtures.USER_ID, Instant.now(), new HashMap<>());

        // Set up UserProfileDao behavior
        Mockito.when(userProfileDaoMock.get(UserProfileFixtures.USER_ID)).thenReturn(Optional.of(initialProfile));
        Mockito.doAnswer(invocation -> {
            UserProfile userProfile = invocation.getArgument(0);
            Mockito.when(userProfileDaoMock.get(userProfile.userId())).thenReturn(Optional.of(userProfile));
            return null;
        }).when(userProfileDaoMock).put(Mockito.any(UserProfile.class));
    }

    @Test
    public void testApplyReplaceCommand() {
        UserProfilePropertyName propertyName = UserProfilePropertyName.valueOf("gold");
        UserProfilePropertyValue propertyValue = UserProfilePropertyValue.valueOf(100);
        Map<UserProfilePropertyName, UserProfilePropertyValue> properties = Map.of(propertyName, propertyValue);
        ReplaceCommand replaceCommand = new ReplaceCommand(UserProfileFixtures.USER_ID, properties);

        userProfileService.applyCommand(replaceCommand);

        UserProfilePropertyValue updatedValue = userProfileService.get(UserProfileFixtures.USER_ID).getUserProfileProperties().get(propertyName);
        assertThat(updatedValue).isEqualTo(propertyValue);
    }

    @Test
    public void testApplyIncrementCommand() {
        UserProfilePropertyName propertyName = UserProfilePropertyName.valueOf("gold");

        UserProfilePropertyValue initialValue = UserProfilePropertyValue.valueOf(100);
        Map<UserProfilePropertyName, UserProfilePropertyValue> properties = Map.of(propertyName, initialValue);
        IncrementCommand incrementCommand = new IncrementCommand(UserProfileFixtures.USER_ID, properties);
        userProfileService.applyCommand(incrementCommand);

        UserProfilePropertyValue incrementValue = UserProfilePropertyValue.valueOf(50);
        properties = Map.of(propertyName, incrementValue);
        incrementCommand = new IncrementCommand(UserProfileFixtures.USER_ID, properties);
        userProfileService.applyCommand(incrementCommand);

        UserProfilePropertyValue updatedValue = userProfileService.get(UserProfileFixtures.USER_ID).getUserProfileProperties().get(propertyName);
        assertThat(updatedValue).isEqualTo(UserProfilePropertyValue.valueOf(initialValue.getValueAsInt() + incrementValue.getValueAsInt()));
    }

    @Test
    void testApplyCollectCommand() {
        UserProfilePropertyName propertyName = UserProfilePropertyName.valueOf("inventory");
        List<String> itemsToAdd = List.of("item1", "item2", "item3");
        UserProfilePropertyValue items = UserProfilePropertyValue.valueOf(itemsToAdd);
        Map<UserProfilePropertyName, UserProfilePropertyValue> properties = Map.of(propertyName, items);
        CollectCommand collectCommand = new CollectCommand(UserProfileFixtures.USER_ID, properties);
        UserProfileCommands commands = new UserProfileCommands(List.of(collectCommand));

        userProfileService.applyCommands(commands);

        UserProfilePropertyValue updatedValue = userProfileService.get(UserProfileFixtures.USER_ID).getUserProfileProperties().get(propertyName);
        assertThat(updatedValue.getValueAsList()).containsAll(itemsToAdd);
    }

    @Test
    public void testApplyBatchCommands() {
        // ReplaceCommand
        UserProfilePropertyName replacePropertyName = UserProfilePropertyName.valueOf("level");
        UserProfilePropertyValue replacePropertyValue = UserProfilePropertyValue.valueOf(10);
        Map<UserProfilePropertyName, UserProfilePropertyValue> replaceProperties = Map.of(replacePropertyName, replacePropertyValue);
        ReplaceCommand replaceCommand = new ReplaceCommand(UserProfileFixtures.USER_ID, replaceProperties);

        // IncrementCommand
        UserProfilePropertyName incrementPropertyName = UserProfilePropertyName.valueOf("gold");

        UserProfilePropertyValue initialValue = UserProfilePropertyValue.valueOf(100);
        Map<UserProfilePropertyName, UserProfilePropertyValue> initialIncrementProperties = Map.of(incrementPropertyName, initialValue);
        IncrementCommand initIncrementCommand = new IncrementCommand(UserProfileFixtures.USER_ID, initialIncrementProperties);

        UserProfilePropertyValue incrementValue = UserProfilePropertyValue.valueOf(50);
        Map<UserProfilePropertyName, UserProfilePropertyValue> incrementProperties = Map.of(incrementPropertyName, incrementValue);
        IncrementCommand incrementCommand = new IncrementCommand(UserProfileFixtures.USER_ID, incrementProperties);

        // CollectCommand
        UserProfilePropertyName collectPropertyName = UserProfilePropertyName.valueOf("inventory");
        List<String> itemsToAdd = List.of("item1", "item2", "item3");
        UserProfilePropertyValue items = UserProfilePropertyValue.valueOf(itemsToAdd);
        Map<UserProfilePropertyName, UserProfilePropertyValue> collectProperties = Map.of(collectPropertyName, items);
        CollectCommand collectCommand = new CollectCommand(UserProfileFixtures.USER_ID, collectProperties);

        UserProfileCommands commands = new UserProfileCommands(List.of(replaceCommand,initIncrementCommand, incrementCommand, collectCommand));
        userProfileService.applyCommands(commands);

        // Assert ReplaceCommand
        UserProfilePropertyValue updatedReplaceValue = userProfileService.get(UserProfileFixtures.USER_ID).getUserProfileProperties().get(replacePropertyName);
        assertThat(updatedReplaceValue).isEqualTo(replacePropertyValue);

        // Assert IncrementCommand
        UserProfilePropertyValue updatedIncrementValue = userProfileService.get(UserProfileFixtures.USER_ID).getUserProfileProperties().get(incrementPropertyName);
        assertThat(updatedIncrementValue).isEqualTo(UserProfilePropertyValue.valueOf(initialValue.getValueAsInt() + incrementValue.getValueAsInt()));

        // Assert CollectCommand
        UserProfilePropertyValue updatedCollectValue = userProfileService.get(UserProfileFixtures.USER_ID).getUserProfileProperties().get(collectPropertyName);
        assertThat(updatedCollectValue.getValueAsList()).containsAll(itemsToAdd);
    }
}