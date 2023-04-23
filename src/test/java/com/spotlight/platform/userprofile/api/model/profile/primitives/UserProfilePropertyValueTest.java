package com.spotlight.platform.userprofile.api.model.profile.primitives;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static net.javacrumbs.jsonunit.assertj.JsonAssertions.assertThatJson;
import static org.assertj.core.api.Assertions.assertThat;

class UserProfilePropertyValueTest {
    private static final String STRING_VALUE = "someString";
    private static final int INTEGER_VALUE = 5;
    private static final List<String> LIST_VALUE = List.of("one", "two");

    @Test
    void equals_ReturnsTrueForEqualValues() {
        assertThat(UserProfilePropertyValue.valueOf(STRING_VALUE)).isEqualTo(UserProfilePropertyValue.valueOf(STRING_VALUE));
        assertThat(UserProfilePropertyValue.valueOf(INTEGER_VALUE)).isEqualTo(UserProfilePropertyValue.valueOf(INTEGER_VALUE));
        assertThat(UserProfilePropertyValue.valueOf(LIST_VALUE)).isEqualTo(UserProfilePropertyValue.valueOf(LIST_VALUE));
    }

    @Test
    void serialization_WorksCorrectly() {
        assertThatJson(UserProfilePropertyValue.valueOf(STRING_VALUE)).isEqualTo("someString");
        assertThatJson(UserProfilePropertyValue.valueOf(INTEGER_VALUE)).isEqualTo("5");
        assertThatJson(UserProfilePropertyValue.valueOf(LIST_VALUE)).isEqualTo("[\"one\",\"two\"]");
    }

    @Test
    public void valueOf_int_returnsExpectedValue() {
        UserProfilePropertyValue propertyValue = UserProfilePropertyValue.valueOf(42);
        Assertions.assertEquals(42, propertyValue.getValueAsInt());
    }

    @Test
    public void valueOf_list_returnsExpectedValue() {
        List<Object> listValue = Arrays.asList("foo", "bar", "baz");
        UserProfilePropertyValue propertyValue = UserProfilePropertyValue.valueOf(listValue);
        Assertions.assertEquals(listValue, propertyValue.getValueAsList());
    }

    @Test
    public void getValueAsInt_valueNotNumber_throwsException() {
        UserProfilePropertyValue propertyValue = UserProfilePropertyValue.valueOf("not a number");
        Assertions.assertThrows(UnsupportedOperationException.class, propertyValue::getValueAsInt);
    }

    @Test
    public void getValueAsList_valueNotList_throwsException() {
        UserProfilePropertyValue propertyValue = UserProfilePropertyValue.valueOf(42);
        Assertions.assertThrows(UnsupportedOperationException.class, propertyValue::getValueAsList);
    }

    @Test
    public void equals_sameValue_returnsTrue() {
        UserProfilePropertyValue propertyValue1 = UserProfilePropertyValue.valueOf(42);
        UserProfilePropertyValue propertyValue2 = UserProfilePropertyValue.valueOf(42);
        Assertions.assertEquals(propertyValue1, propertyValue2);
    }

    @Test
    public void equals_differentValue_returnsFalse() {
        UserProfilePropertyValue propertyValue1 = UserProfilePropertyValue.valueOf(42);
        UserProfilePropertyValue propertyValue2 = UserProfilePropertyValue.valueOf(43);
        Assertions.assertNotEquals(propertyValue1, propertyValue2);
    }

    @Test
    public void equals_null_returnsFalse() {
        UserProfilePropertyValue propertyValue = UserProfilePropertyValue.valueOf(42);
        Assertions.assertNotEquals(propertyValue, null);
    }

    @Test
    public void equals_differentClass_returnsFalse() {
        UserProfilePropertyValue propertyValue = UserProfilePropertyValue.valueOf(42);
        Assertions.assertNotEquals(propertyValue, "not a UserProfilePropertyValue");
    }

    @Test
    public void hashCode_sameValue_returnsSameHashCode() {
        UserProfilePropertyValue propertyValue1 = UserProfilePropertyValue.valueOf(42);
        UserProfilePropertyValue propertyValue2 = UserProfilePropertyValue.valueOf(42);
        Assertions.assertEquals(propertyValue1.hashCode(), propertyValue2.hashCode());
    }

    @Test
    public void getValue_valueSet_returnsExpectedValue() {
        UserProfilePropertyValue propertyValue = UserProfilePropertyValue.valueOf(42);
        Assertions.assertEquals(42, propertyValue.getValue());
    }

    @Test
    public void getValueAsList_valueList_returnsList() {
        List<String> listValue = Arrays.asList("foo", "bar", "baz");
        UserProfilePropertyValue propertyValue = UserProfilePropertyValue.valueOf(listValue);
        Assertions.assertEquals(listValue, propertyValue.getValueAsList());
    }
}