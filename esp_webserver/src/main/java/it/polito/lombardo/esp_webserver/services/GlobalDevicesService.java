package it.polito.lombardo.esp_webserver.services;

import it.polito.lombardo.esp_webserver.entities.CollectedData;

import java.util.Date;
import java.util.List;

public interface GlobalDevicesService {
    /*save the received data into mongodb */
    public CollectedData saveCollectedData(CollectedData collectedData);
    /*analyze the stored information and guess global devices*/
    public void analyzeCollectedDataBy(String chip_id);
}
