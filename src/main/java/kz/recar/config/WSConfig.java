package kz.recar.config;

import kz.recar.repository.UserRepository;
import kz.recar.services.ws.WSUserServiceImpl;
import org.apache.cxf.Bus;
import org.apache.cxf.jaxws.EndpointImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.xml.ws.Endpoint;

@Configuration
public class WSConfig {
    @Autowired
    private Bus bus;

    @Autowired
    private UserRepository userRepository;


    @Bean
    public Endpoint userEndpoint() {

        EndpointImpl endpoint = new EndpointImpl(bus, new WSUserServiceImpl(userRepository));
        endpoint.publish("/user");
        return endpoint;
    }
}
