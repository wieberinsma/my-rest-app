package controllers;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("")
public class TrackResource
{
    @GET
    @Path("tracks")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getTracks(@QueryParam("forPlaylist") int id, @QueryParam("token") String token)
    {
        return Response
                .status(200)
                .build();
    }

    @GET
    @Path("playlists/{id}/tracks")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getTracksOfPlaylist(@PathParam("id") int id, @QueryParam("token") String token)
    {
        return Response
                .status(200)
                .build();
    }

}

