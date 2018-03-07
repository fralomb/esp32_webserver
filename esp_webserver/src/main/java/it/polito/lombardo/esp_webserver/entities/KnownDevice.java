package it.polito.lombardo.esp_webserver.entities;

import java.util.*;

public class KnownDevice {
    /*
    * the mac address of the device
    * */
    public String global_mac_address;
    /*
    * is the mac address unique or not?
    * */
    public Boolean unique;
    /*
    * the footprint of the device
    * */
    public Set<String> footprints;
    /*
    * the time in which the first message from this device has been received
    * */
    public String start_time;
    /*
    * the time in qhich the last message from this device has been received
    * */
    public String end_time;
    /*
    * list of all ssids looked for by the device
    * */
    public List<Detail> details;

    public KnownDevice() {
        details = new ArrayList<Detail>();
        footprints = new HashSet<String>();
    }

}
