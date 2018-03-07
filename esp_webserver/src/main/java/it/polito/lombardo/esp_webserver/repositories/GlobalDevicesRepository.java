package it.polito.lombardo.esp_webserver.repositories;

import it.polito.lombardo.esp_webserver.documents.GloballyUniqueDevicesEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.Date;
import java.util.Set;

public interface GlobalDevicesRepository extends MongoRepository<GloballyUniqueDevicesEntity, String> {
    /*
    * find the device by chipid and footprint
    * */
    @Query("{ chip_ids: {'$in' : [?0]}, footprints : {'$in': [?1] } }")
    public GloballyUniqueDevicesEntity findByChipIdAndFootprint(String chipid, String footprints);
}
