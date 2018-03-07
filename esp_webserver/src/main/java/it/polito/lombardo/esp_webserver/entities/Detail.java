package it.polito.lombardo.esp_webserver.entities;

import java.util.ArrayList;
import java.util.List;

/*
* information obtained from a single sniffed probe request
* */
public class Detail {
    /*
    * the Ssid of the network that the device is looking for
    * */
    public String ssid;
    /*
    * the int hash of the ssid
    * */
    public Long ssid_hash;
    /*
    * the list of timestamp in which the same ssid is looked for from the device
    * */
    public List<String> timestamps;

    public Detail() {
        timestamps = new ArrayList<String>();
    }
}
