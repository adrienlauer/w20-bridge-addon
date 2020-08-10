package org.seedstack.w20.fixtures;

import javax.ws.rs.GET;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.Path;

@Path("/not-found")
public class NotFoundResource {
    @GET
    public void doIt() {
        throw new NotFoundException("not found");
    }
}
