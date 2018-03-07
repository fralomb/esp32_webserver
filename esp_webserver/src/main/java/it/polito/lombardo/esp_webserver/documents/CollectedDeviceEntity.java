package it.polito.lombardo.esp_webserver.documents;

import com.sun.tracing.Probe;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.*;

/*
* a single global device collected by the esp32
* */
public class CollectedDeviceEntity {
    /*
    * the global mac address of a device
    * */
    @Field("global_mac_address")
    private String global_mac_address;

    /*
    * specify if the mac address is a globally unique or local defined address
    * */
    @Field("unique")
    private Boolean unique;

    /*
    * the footprint of the device
    * */
    @Field("footprints")
    private Set<String> footprints;

    /*
    * the time in which the first message from this device has been received
    * */
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    @Field("start_time")
    private Date start_time;

    /*
    * the start_time in millis
    * */
    @Field("start_time_millis")
    private Long start_time_millis;

    /*
    * the time in qhich the last message from this device has been received
    * */
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    @Field("end_time")
    private Date end_time;

    /*
    * the start_time in millis
    * */
    @Field("end_time_millis")
    private Long end_time_millis;

    /*
    * the list of all the probe request associated to the same device
    * */
    private List<ProbeEntity> probeEntities;

    /*getters and setters*/
    public CollectedDeviceEntity() {
        probeEntities = new ArrayList<ProbeEntity>();
        footprints = new HashSet<String>();
    }

    public String getGlobal_mac_address() {
        return global_mac_address;
    }

    public void setGlobal_mac_address(String global_mac_address) {
        this.global_mac_address = global_mac_address;
    }

    public Boolean getUnique() {
        return unique;
    }

    public void setUnique(Boolean unique) {
        this.unique = unique;
    }

    public Set<String> getFootprints() {
        return footprints;
    }

    public void setFootprints(Set<String> footprints) {
        this.footprints = footprints;
    }

    public Long getStart_time_millis() {
        return start_time_millis;
    }

    public void setStart_time_millis(Long start_time_millis) {
        this.start_time_millis = start_time_millis;
    }

    public Long getEnd_time_millis() {
        return end_time_millis;
    }

    public void setEnd_time_millis(Long end_time_millis) {
        this.end_time_millis = end_time_millis;
    }

    public List<ProbeEntity> getProbeEntities() {
        return probeEntities;
    }

    public void setProbeEntities(List<ProbeEntity> probeEntities) {
        this.probeEntities = probeEntities;
    }

    public Date getStart_time() {
        return start_time;
    }

    public void setStart_time(Date start_time) {
        this.start_time = start_time;
    }

    public Date getEnd_time() {
        return end_time;
    }

    public void setEnd_time(Date end_time) {
        this.end_time = end_time;
    }

}
