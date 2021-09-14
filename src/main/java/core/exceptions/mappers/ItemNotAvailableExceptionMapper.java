package core.exceptions.mappers;

import core.exceptions.ItemNotAvailableException;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class ItemNotAvailableExceptionMapper implements ExceptionMapper<ItemNotAvailableException>
{
    @Override
    public Response toResponse(ItemNotAvailableException e)
    {
        return Response.status(405).build();
    }
}
