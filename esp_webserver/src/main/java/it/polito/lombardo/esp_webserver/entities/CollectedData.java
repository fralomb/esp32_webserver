package it.polito.lombardo.esp_webserver.entities;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/*
* the collection of Details collected from the same device
* */
public class CollectedData {
    /*
    * the timestamp in which the request has been uploaded
    * */
    public String upload_timestamp;
    /*
    * time between the consecutive reception of data
    * */
    public Integer upload_timeout;
    /*
    * the chipid of the esp32 which collected these data
    * */
    public String CHIP_ID;
    /*
    * the set of all collected devices
    * */
    public List<KnownDevice> collected_data;

    public CollectedData() {
        collected_data = new ArrayList<KnownDevice>();
    }

}
