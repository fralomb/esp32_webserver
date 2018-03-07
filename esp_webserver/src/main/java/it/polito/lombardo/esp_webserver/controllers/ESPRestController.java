package it.polito.lombardo.esp_webserver.controllers;

import it.polito.lombardo.esp_webserver.entities.ActualTimestamp;
import it.polito.lombardo.esp_webserver.entities.CollectedData;
import it.polito.lombardo.esp_webserver.services.GlobalDevicesService;
import it.polito.lombardo.esp_webserver.util.ActualTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Timestamp;
import java.util.List;

@RestController
@RequestMapping("/collect_data")
public class ESPRestController {

    @Autowired
    private GlobalDevicesService globalDevicesService;
    /*
     * return the actual timestamp of the machine
     * */
    @RequestMapping(method = RequestMethod.GET, value = "/actual_timestamp")
    ActualTimestamp retrieveActualTimestamp() {
        //get the actual timestamp and get it in milliseconds from epoch
        return new ActualTimestamp(ActualTime.getActualTimestampInMillis());
    }

    /*
    * get the collected data in json
    * */
    @RequestMapping(method = RequestMethod.POST, value = "/upload", consumes="application/json")
    ResponseEntity retrieveCollectedData(@RequestBody CollectedData data) {
        //save data into mongo collection
        System.out.println("data correctly received. Persisting in the db");
        CollectedData ret = globalDevicesService.saveCollectedData(data);
        System.out.println("Analyzing data received...");
        //analyze data
        //globalDevicesService.analyzeCollectedData(data.CHIP_ID);
        System.out.println("Everything done.");
        return ResponseEntity.ok(ret);
    }

}
