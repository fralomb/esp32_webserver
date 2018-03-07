package it.polito.lombardo.esp_webserver.repositories;

import it.polito.lombardo.esp_webserver.documents.CollectedDataEntity;
import it.polito.lombardo.esp_webserver.documents.CollectedDeviceEntity;
import it.polito.lombardo.esp_webserver.entities.CollectedData;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.Date;
import java.util.List;

/*
* DAO of the MongoDB's CollectedDataEntity
* */
public interface GlobalDataRepository extends MongoRepository<CollectedDataEntity, String> {
    /*
    * find the globalDevice by chipid and timestamp
    * */
    public CollectedDataEntity findByChipIdAndUploadTimeout(String chipid, Date upload_timeout);

    /*
    * find all the data collected by a specific esp32
    * */
    @Query("{ 'chip_id': ?0}")
    public List<CollectedDataEntity> findByChipId(String chipid);
}
