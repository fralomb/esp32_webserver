package it.polito.lombardo.esp_webserver.dbconfig;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.stereotype.Component;

@Component
public class MongoFactoryConfig {


    private final MongoDbFactory mongo;

    /*
     * 		When MongoDbFactory instance injected, 'spring.data.mongodb.uri'
     * 		system property will be exploit.
     * */
    @Autowired
    public MongoFactoryConfig(MongoDbFactory mongo) {
        this.mongo = mongo;
    }

    public MongoDbFactory getMongo() {
        return mongo;
    }
}