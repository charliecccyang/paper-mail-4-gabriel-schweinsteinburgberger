package com.spotlight.platform.userprofile.api.model.commands;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.spotlight.platform.userprofile.api.model.profile.UserProfile;
import com.spotlight.platform.userprofile.api.model.profile.primitives.UserId;
import com.spotlight.platform.userprofile.api.model.profile.primitives.UserProfilePropertyName;
import com.spotlight.platform.userprofile.api.model.profile.primitives.UserProfilePropertyValue;

import java.util.Map;

public class ReplaceCommand extends UserProfileCommand {
    private final Map<UserProfilePropertyName, UserProfilePropertyValue> properties;

    @JsonCreator
    public ReplaceCommand(@JsonProperty("userId") UserId userId,
                          @JsonProperty("properties") Map<UserProfilePropertyName, UserProfilePropertyValue> properties) {
        super(userId);
        this.properties = properties;
    }

    @Override
    public void apply(UserProfile userProfile) {
        properties.forEach(userProfile::replaceProperty);
    }
}
