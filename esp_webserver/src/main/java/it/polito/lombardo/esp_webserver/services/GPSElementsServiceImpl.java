package it.polito.lombardo.esp_webserver.services;

import it.polito.lombardo.esp_webserver.documents.GPSElement;
import it.polito.lombardo.esp_webserver.documents.GPSElements;
import it.polito.lombardo.esp_webserver.entities.CollectedGPS;
import it.polito.lombardo.esp_webserver.entities.GPSDetail;
import it.polito.lombardo.esp_webserver.repositories.GPSElementsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class GPSElementsServiceImpl implements GPSElementsService {
    @Autowired
    private GPSElementsRepository GPSElementsRepo;

    @Override
    public void saveCollectedData(CollectedGPS data) {
        GPSElements elements = new GPSElements();
        elements.setImei(data.imei);
        elements.setTimestamp(data.timestamp);

        for(GPSDetail detail : data.gpsData) {
            GPSElement elem = new GPSElement();
            elem.setAltitude(detail.altitude);
            elem.setAngle(detail.angle);
            elem.setLat(detail.lat);
            elem.setLng(detail.lng);
            elem.setPriority(detail.priority);
            elem.setSatellites(detail.satellites);
            elem.setSpeed(detail.speed);
            elem.setTimestamp(detail.timestamp);
            elements.getGpsData().add(elem);
        }
        //store into the mongo collection
        GPSElementsRepo.save(elements);
        System.out.println("Data successfully added with _id:" + elements.get_id() + ".");
    }
}
