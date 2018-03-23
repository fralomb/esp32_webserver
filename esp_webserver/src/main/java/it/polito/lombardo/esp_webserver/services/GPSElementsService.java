package it.polito.lombardo.esp_webserver.services;

import it.polito.lombardo.esp_webserver.entities.CollectedGPS;

public interface GPSElementsService {
    public void saveCollectedData(CollectedGPS informations);
}
