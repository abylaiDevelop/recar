package kz.recar.services.ws;

import kz.recar.model.User;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.xml.ws.RequestWrapper;
import javax.xml.ws.ResponseWrapper;

@WebService(name = "UserService",
        targetNamespace = "http://service.ws.sample/")

public interface WSUserService {
    @WebResult(name = "user", targetNamespace = "")
    @RequestWrapper(
            localName = "getUser",
            targetNamespace = "http://service.ws.sample/",
            className = "sample.ws.GetUser"

    )
    @WebMethod(action = "urn:getUser")
    @ResponseWrapper(
            localName = "getUserResponse",
            targetNamespace = "http://service.ws.sample/",
            className = "sample.ws.GetUserResponse"
    )
    User getUser(@WebParam(name = "login" , targetNamespace = "") String login);

    @WebResult(name = "NewUser", targetNamespace = "")
    @RequestWrapper(
            localName = "addUser",
            targetNamespace = "http://service.ws.sample/",
            className = "sample.ws.AddUser"

    )
    @WebMethod(action = "urn:addUser")
    @ResponseWrapper(
            localName = "addUserResponse",
            targetNamespace = "http://service.ws.sample/",
            className = "sample.ws.AddUserResponse"
    )
    User createUser(@WebParam( name = "login" , targetNamespace = "")
                            String email, @WebParam(name = "name", targetNamespace = "") String name,
                    @WebParam(name = "password")
                            String password);


    @WebResult(name = "NewUser", targetNamespace = "")
    @RequestWrapper(
            localName = "updateUser",
            targetNamespace = "http://service.ws.sample/",
            className = "sample.ws.UpdateUser"

    )
    @WebMethod(action = "urn:updateUser")
    @ResponseWrapper(
            localName = "updateUserResponse",
            targetNamespace = "http://service.ws.sample/",
            className = "sample.ws.UpdateUserResponse"
    )
    User updateUser(@WebParam(name = "login") String email, @WebParam(name = "name") String name);


    @WebResult(name = "return", targetNamespace = "")
    @RequestWrapper(
            localName = "deleteUser",
            targetNamespace = "http://service.ws.sample/",
            className = "sample.ws.DeleteUser"

    )
    @WebMethod(action = "urn:deleteUser")
    @ResponseWrapper(
            localName = "deleteUserResponse",
            targetNamespace = "http://service.ws.sample/",
            className = "sample.ws.DeleteUserResponse"
    )
    String deleteUser(@WebParam(name = "login") String login);

}
