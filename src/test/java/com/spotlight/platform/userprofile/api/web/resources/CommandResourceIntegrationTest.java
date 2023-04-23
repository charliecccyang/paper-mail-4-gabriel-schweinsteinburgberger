package com.spotlight.platform.userprofile.api.web.resources;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import com.spotlight.platform.helpers.FixtureHelpers;
import com.spotlight.platform.userprofile.api.core.profile.persistence.UserProfileDao;
import com.spotlight.platform.userprofile.api.web.UserProfileApiApplication;
import io.dropwizard.jackson.Jackson;
import org.eclipse.jetty.http.HttpStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;
import ru.vyarus.dropwizard.guice.test.ClientSupport;
import ru.vyarus.dropwizard.guice.test.jupiter.ext.TestDropwizardAppExtension;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.reset;

@Execution(ExecutionMode.SAME_THREAD)
class CommandResourceIntegrationTest {

    @RegisterExtension
    static TestDropwizardAppExtension APP = TestDropwizardAppExtension.forApp(UserProfileApiApplication.class)
            .randomPorts()
            .hooks(builder -> builder.modulesOverride(new AbstractModule() {
                @Provides
                @Singleton
                public UserProfileDao getUserProfileDao() {
                    return mock(UserProfileDao.class);
                }
            }))
            .randomPorts()
            .create();

    private final ObjectMapper objectMapper = Jackson.newObjectMapper();

    @BeforeEach
    void beforeEach(UserProfileDao userProfileDao) {
        reset(userProfileDao);
    }

    @Nested
    @DisplayName("processCommand")
    class ProcessCommand {

        private static final String URL = "/users/commands";

        private static final String BATCH_URL = "/users/commands/batch";

        @Test
        void validReplaceCommand_returns202(ClientSupport client) throws IOException {
            var command = objectMapper.readValue(FixtureHelpers.fixture("/fixtures/model.commands/replace.json"), Object.class);

            var response = client.targetRest().path(URL).request().post(Entity.entity(command, MediaType.APPLICATION_JSON_TYPE));

            assertThat(response.getStatus()).isEqualTo(HttpStatus.ACCEPTED_202);
        }

        @Test
        void validIncrementCommand_returns202(ClientSupport client) throws IOException {
            var command = objectMapper.readValue(FixtureHelpers.fixture("/fixtures/model.commands/increment.json"), Object.class);

            var response = client.targetRest().path(URL).request().post(Entity.entity(command, MediaType.APPLICATION_JSON_TYPE));

            assertThat(response.getStatus()).isEqualTo(HttpStatus.ACCEPTED_202);
        }

        @Test
        void validCollectCommand_returns202(ClientSupport client) throws IOException {
            var command = objectMapper.readValue(FixtureHelpers.fixture("/fixtures/model.commands/collect.json"), Object.class);

            var response = client.targetRest().path(URL).request().post(Entity.entity(command, MediaType.APPLICATION_JSON_TYPE));

            assertThat(response.getStatus()).isEqualTo(HttpStatus.ACCEPTED_202);
        }

        @Test
        void validBatchCommand_returns202(ClientSupport client) throws IOException {
            var command = objectMapper.readValue(FixtureHelpers.fixture("/fixtures/model.commands/commands.json"), Object.class);

            var response = client.targetRest().path(BATCH_URL).request().post(Entity.entity(command, MediaType.APPLICATION_JSON_TYPE));

            assertThat(response.getStatus()).isEqualTo(HttpStatus.ACCEPTED_202);
        }

    }
}