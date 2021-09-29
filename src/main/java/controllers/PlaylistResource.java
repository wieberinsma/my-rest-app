package controllers;

import model.api.PlaylistsResponse;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;

@Path("/playlists")
public class PlaylistResource
{
    @GET
    public PlaylistsResponse playlists(@QueryParam("token") String token)
    {
        return new PlaylistsResponse();
    }
}
