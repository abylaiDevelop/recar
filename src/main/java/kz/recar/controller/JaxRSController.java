package kz.recar.controller;

import kz.recar.model.Auto;
import kz.recar.model.Message;
import kz.recar.services.AutoServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/user")
@Produces(MediaType.APPLICATION_JSON)
@AllArgsConstructor
public class JaxRSController {

    private final AutoServiceImpl autoService;

    @GET
    public List<Auto> allAutos() {
        return autoService.getAutos();
    }

    @GET
    @Path("/{id}")
    public Auto getAuto(@PathParam("id") Long id) {
        return autoService.getById(id);
    }

    @POST
    public Response createUser(Auto auto) {
        Auto newAuto = autoService.createAuto(auto);
        boolean created = newAuto.getId() != null;
        if (!created) {
            return Response.status(javax.ws.rs.core.Response.Status.BAD_REQUEST)
                    .entity(new Message()).build();
        } else {
            return Response.status(javax.ws.rs.core.Response.Status.CREATED)
                    .entity(newAuto).build();
        }
    }







}
