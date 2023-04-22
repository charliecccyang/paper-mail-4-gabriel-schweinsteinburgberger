package com.spotlight.platform.userprofile.api.model.commands;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

public class UserProfileCommands {
    public UserProfileCommands(@JsonProperty("commands") List<UserProfileCommand> commands) {
        this.commands = commands != null ? commands : new ArrayList<>();
    }
    @JsonProperty
    private List<UserProfileCommand> commands;

    public List<UserProfileCommand> getCommands() {
        return commands;
    }

    public void setCommands(List<UserProfileCommand> commands) {
        this.commands = commands;
    }
}
