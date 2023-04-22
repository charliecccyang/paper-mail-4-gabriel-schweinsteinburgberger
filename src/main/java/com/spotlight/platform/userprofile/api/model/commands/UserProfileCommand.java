package com.spotlight.platform.userprofile.api.model.commands;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.spotlight.platform.userprofile.api.model.profile.UserProfile;
import com.spotlight.platform.userprofile.api.model.profile.primitives.UserId;

@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        property = "type",
        visible = true)
@JsonSubTypes({
        @JsonSubTypes.Type(value = ReplaceCommand.class, name = "replace"),
        @JsonSubTypes.Type(value = IncrementCommand.class, name = "increment"),
        @JsonSubTypes.Type(value = CollectCommand.class, name = "collect")
})
public abstract class UserProfileCommand {
    private final UserId userId;

    protected UserProfileCommand(UserId userId) {
        this.userId = userId;
    }

    public UserId getUserId() {
        return userId;
    }

    public abstract void apply(UserProfile userProfile);
}
