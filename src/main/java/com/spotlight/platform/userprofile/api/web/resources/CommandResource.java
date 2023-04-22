package com.spotlight.platform.userprofile.api.web.resources;

import com.spotlight.platform.userprofile.api.core.profile.UserProfileService;
import com.spotlight.platform.userprofile.api.model.commands.UserProfileCommand;
import com.spotlight.platform.userprofile.api.model.commands.UserProfileCommands;

import javax.inject.Inject;
import javax.validation.Valid;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/users/commands")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class CommandResource {

    private final UserProfileService userProfileService;

    @Inject
    public CommandResource(UserProfileService userProfileService) {
        this.userProfileService = userProfileService;
    }

    @POST
    public Response processCommand(@Valid UserProfileCommand command) {
        userProfileService.applyCommand(command);
        return Response.accepted().build();
    }

    @Path("/batch")
    @POST
    public void applyCommands(UserProfileCommands commands) {
        for (UserProfileCommand command : commands.getCommands()) {
            userProfileService.applyCommand(command);
        }
    }
}
