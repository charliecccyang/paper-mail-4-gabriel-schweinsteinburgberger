package com.spotlight.platform.userprofile.api.model.commands;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class UserProfileCommandsTest {

    @Test
    void testConstructorAndGetters() {
        List<UserProfileCommand> commands = new ArrayList<>();
        UserProfileCommands userProfileCommands = new UserProfileCommands(commands);

        assertEquals(commands, userProfileCommands.getCommands());
    }

    @Test
    void testSetters() {
        List<UserProfileCommand> commands = new ArrayList<>();
        UserProfileCommands userProfileCommands = new UserProfileCommands(null);

        userProfileCommands.setCommands(commands);

        assertEquals(commands, userProfileCommands.getCommands());
    }
}
