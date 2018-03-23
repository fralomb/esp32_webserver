package it.polito.lombardo.esp_webserver.repositories;

import it.polito.lombardo.esp_webserver.documents.GPSElements;
import org.springframework.data.mongodb.repository.MongoRepository;

/*
* the repository for query/save the collected gps information
* */
public interface GPSElementsRepository extends MongoRepository<GPSElements, String> {

}
