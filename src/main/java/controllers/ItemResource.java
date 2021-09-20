package controllers;

import model.Item;
import services.ItemService;

import javax.ejb.Singleton;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Singleton
@Path("/items")
public class ItemResource
{
    @Inject
    private ItemService itemService;

    @Inject
    private int defaultInt;

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String getTextItems()
    {
        return "bread, butter";
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getJsonItems()
    {
        List<Item> items = itemService.getAll();
        return Response.status(Response.Status.OK).entity(items).build();
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getItem(@PathParam("id") int id)
    {
        Item item = itemService.getItem(id);
        return Response.status(Response.Status.OK).entity(item).build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addItem(Item item)
    {
        itemService.addItem(item);
        return Response.status(Response.Status.CREATED).build();
    }

    @DELETE
    @Path("/{id}")
    public Response deleteItem(@PathParam("id") int id)
    {
        itemService.deleteItem(id);
        return Response.status(Response.Status.NO_CONTENT).build();
    }
}
