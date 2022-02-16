package nl.han.resttest.core.controllers;

import javax.ws.rs.GET;
import javax.ws.rs.Path;

// JAX-RS endpoint
@Path("/health")
public class HealthController
{
    @GET
    public String healthy() {
        return "Up & Running";
    }
}
