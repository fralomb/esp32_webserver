package it.polito.lombardo.esp_webserver.entities;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/*the collection of all collected GPS informations*/
public class CollectedGPS {
    /*
    * the imei of the router
    * */
    public String imei;

    /*
    * the timestamp in which the set of gps coordinates has been received
    * */
    public Date timestamp;

    /*
    * the list of all the received gps data
    * */
    public List<GPSDetail> gpsData;

    public CollectedGPS() {
        gpsData = new ArrayList<GPSDetail>();
    }

    public List<GPSDetail> getGpsData() {
        return gpsData;
    }
}
