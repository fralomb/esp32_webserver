package it.polito.lombardo.esp_webserver;

import it.polito.lombardo.esp_webserver.util.MergingThread;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication
@EnableMongoRepositories("it.polito.lombardo.esp_webserver.repositories")
public class EspWebserverApplication {

	public static void main(String[] args) {
		SpringApplication.run(EspWebserverApplication.class, args);
	}

	/*@Bean
	public MergingThread reportCleanupThread(){
		MergingThread rct=new MergingThread();
		rct.start();
		return rct;
	}*/
}
