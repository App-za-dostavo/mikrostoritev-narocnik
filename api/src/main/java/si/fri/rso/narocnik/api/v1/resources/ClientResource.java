package si.fri.rso.narocnik.api.v1.resources;

import si.fri.rso.narocnik.lib.Client;
import si.fri.rso.narocnik.services.beans.ClientBean;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.util.List;

@ApplicationScoped
@Path("/narocnik")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ClientResource {

    @Inject
    private ClientBean clientBean;

    @Context
    protected UriInfo uriInfo;

    @GET
    public Response getClients() {

        List<Client> client = clientBean.getClients();

        return Response.status(Response.Status.OK).entity(client).build();
    }

    @GET
    @Path("/{id}")
    public Response getClient(@PathParam("id") Integer id) {

        Client client = clientBean.getClient(id);

        if (client == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        return Response.status(Response.Status.OK).entity(client).build();
    }

    @POST
    public Response createClient(Client person) {

        if (person.getFirstName() == null || person.getVip() == null || person.getLocation() == null) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        } else {
            person = clientBean.createClient(person);
        }

        return Response.status(Response.Status.CONFLICT).entity(person).build();
    }

    @PUT
    @Path("{id}")
    public Response putClient(@PathParam("id") Integer id, Client person) {

        person = clientBean.putClient(id, person);

        if (person == null) {
            return Response.status(Response.Status.NOT_MODIFIED).build();
        }
        return Response.status(Response.Status.OK).entity(person).build();
    }

    @DELETE
    @Path("{id}")
    public Response deleteClient(@PathParam("id") Integer id, Client person) {

        boolean deleted = clientBean.deleteClient(id);

        if (deleted) {
            return  Response.status(Response.Status.OK).build();
        } else return Response.status(Response.Status.NOT_FOUND).entity(person).build();
    }
}

