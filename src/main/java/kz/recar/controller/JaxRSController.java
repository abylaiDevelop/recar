package kz.recar.controller;

import kz.recar.model.User;
import kz.recar.model.Message;
import kz.recar.services.UserServiceImpl;
import lombok.AllArgsConstructor;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/user")
@Produces(MediaType.APPLICATION_JSON)
@AllArgsConstructor
public class JaxRSController {

  private final UserServiceImpl autoService;

  @GET
  public List<User> allUsers() {
    return autoService.getUsers();
  }

  @GET
  @Path("/{id}")
  public User getUser(@PathParam("id") Long id) {
    return autoService.getById(id);
  }

//  @POST
//  public Response createUser(User auto) {
//    User newUser = autoService.createUser(auto);
//    boolean created = newUser.getId() != null;
//    if (!created) {
//      return Response.status(javax.ws.rs.core.Response.Status.BAD_REQUEST)
//              .entity(new Message()).build();
//    } else {
//      return Response.status(javax.ws.rs.core.Response.Status.CREATED)
//              .entity(newUser).build();
//    }
//  }







}
