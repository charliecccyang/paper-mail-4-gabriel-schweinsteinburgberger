package com.spotlight.platform.userprofile.api.model.profile.primitives;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import java.util.List;

public class UserProfilePropertyValue {

    private final Object value;

    @JsonCreator
    private UserProfilePropertyValue(Object value) {
        this.value = value;
    }

    public static UserProfilePropertyValue valueOf(Object value) {
        return new UserProfilePropertyValue(value);
    }

    @JsonValue
    protected Object getValue() {
        return value;
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null || this.getClass() != obj.getClass()) {
            return false;
        }
        return value.equals(((UserProfilePropertyValue) obj).getValue());
    }

    public int getValueAsInt() {
        if (value instanceof Number) {
            return ((Number) value).intValue();
        }
        throw new UnsupportedOperationException("Value is not a Number.");
    }


    @SuppressWarnings("unchecked")
    public List<Object> getValueAsList() {
        if (value instanceof List) {
            return (List<Object>) value;
        }
        throw new UnsupportedOperationException("Value is not a List.");
    }
}

