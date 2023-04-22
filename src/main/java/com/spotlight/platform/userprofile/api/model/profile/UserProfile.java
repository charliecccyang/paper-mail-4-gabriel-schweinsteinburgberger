package com.spotlight.platform.userprofile.api.model.profile;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.spotlight.platform.userprofile.api.model.profile.primitives.UserId;
import com.spotlight.platform.userprofile.api.model.profile.primitives.UserProfilePropertyName;
import com.spotlight.platform.userprofile.api.model.profile.primitives.UserProfilePropertyValue;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public record UserProfile(@JsonProperty UserId userId, @JsonProperty @JsonFormat(shape = JsonFormat.Shape.STRING) Instant latestUpdateTime,
                          @JsonProperty Map<UserProfilePropertyName, UserProfilePropertyValue> userProfileProperties) {

    public Map<UserProfilePropertyName, UserProfilePropertyValue> getUserProfileProperties() {
        return userProfileProperties;
    }

    public void replaceProperty(UserProfilePropertyName propertyName, UserProfilePropertyValue propertyValue) {
        userProfileProperties.put(propertyName, propertyValue);
    }

    public void incrementProperty(UserProfilePropertyName propertyName, UserProfilePropertyValue incrementValue) {
        UserProfilePropertyValue currentValue = userProfileProperties.getOrDefault(propertyName, UserProfilePropertyValue.valueOf(0));
        UserProfilePropertyValue newValue = UserProfilePropertyValue.valueOf(currentValue.getValueAsInt() + incrementValue.getValueAsInt());
        userProfileProperties.put(propertyName, newValue);
    }

    public void collectProperty(UserProfilePropertyName propertyName, UserProfilePropertyValue valuesToCollect) {
        UserProfilePropertyValue currentValue = userProfileProperties.getOrDefault(propertyName, UserProfilePropertyValue.valueOf(new ArrayList<>()));
        List<Object> newList = new ArrayList<>(currentValue.getValueAsList());
        newList.addAll(valuesToCollect.getValueAsList());
        userProfileProperties.put(propertyName, UserProfilePropertyValue.valueOf(newList));
    }
}