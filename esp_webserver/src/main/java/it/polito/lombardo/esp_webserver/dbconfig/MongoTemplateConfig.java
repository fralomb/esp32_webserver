package it.polito.lombardo.esp_webserver.dbconfig;

import com.mongodb.Mongo;
import com.mongodb.MongoClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.data.mongodb.core.MongoTemplate;

/*
 * Classe di configurazione per Mongo.
 * */
@Configuration
public class MongoTemplateConfig {

    @Autowired
    Environment env;

    public @Bean
    Mongo mongo() throws Exception {
        return new MongoClient();
    }

    /*
     * 		MongoTemplate builded starting from a MongoClient and database name.
     * 		Base on this assumption, MongoClient when builded (at invocation) must
     *		have same configuration about mongo db URI:
     * 			--> so need set proper MongoFactory configurations
     * */
    public @Bean
    MongoTemplate mongoTemplate() throws Exception {
        return new MongoTemplate(mongo(), env.getProperty("spring.data.mongodb.database"));
    }
}
