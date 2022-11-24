package kz.recar.repository;

import kz.recar.model.Auto;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface AutoRepository extends MongoRepository<Auto, String> {
    Auto findByLogin(String login);

}
