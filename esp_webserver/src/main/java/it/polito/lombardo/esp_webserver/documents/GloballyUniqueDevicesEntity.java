package it.polito.lombardo.esp_webserver.documents;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.*;

@Document(collection = "global_devices")
public class GloballyUniqueDevicesEntity {
    /*
    * the id in the mongo collection
    * */
    @Id
    private String _id;

    /*
    * the chipid of the device that has sent the data
    * */
    @Field("chip_ids")
    private Set<String> chipIds;
    /*
    * the global mac address of a device
    * */
    @Field("global_mac_addresses")
    private Set<String> global_mac_addresses;

    @Field("similarity_index")
    private Double similarity_index;

    /*
    * specify if the mac address is a globally unique or local defined address
    * */
    @Field("unique")
    private Boolean unique;

    /*
    * the footprint of the device
    * */
    @Indexed
    @Field("footprints")
    private Set<String> footprints;

    /*
    * list of intervals in which the device has been listen
    * */
    @Field("intervals")
    private List<IntervalEntity> intervals;

    /*
    * the list of ssids
    * */
    @Field("ssids")
    private Set<String> ssids;

    /*
    * list of the int hash of the ssids
    * */
    @Field("ssid_hash")
    private Set<Long> ssids_hash;


    public GloballyUniqueDevicesEntity() {
        chipIds = new HashSet<String>();
        global_mac_addresses = new HashSet<String>();
        intervals = new ArrayList<IntervalEntity>();
        ssids = new HashSet<String>();
        ssids_hash = new HashSet<Long>();
        footprints = new HashSet<String>();
    }

    /*getters and setters*/
    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public Set<String> getChipIds() {
        return chipIds;
    }

    public void setChipIds(Set<String> chipIds) {
        this.chipIds = chipIds;
    }

    public Set<String> getGlobal_mac_addresses() {
        return global_mac_addresses;
    }

    public void setGlobal_mac_addresses(Set<String> global_mac_addresses) {
        this.global_mac_addresses = global_mac_addresses;
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

    public Set<String> getSsids() {
        return ssids;
    }

    public void setSsids(Set<String> ssids) {
        this.ssids = ssids;
    }

    public Set<Long> getSsids_hash() {
        return ssids_hash;
    }

    public void setSsids_hash(Set<Long> ssids_hash) {
        this.ssids_hash = ssids_hash;
    }

    public List<IntervalEntity> getIntervals() {
        return intervals;
    }

    public void setIntervals(List<IntervalEntity> intervals) {
        this.intervals = intervals;
    }

    public Double getSimilarity_index() {
        return similarity_index;
    }

    public void setSimilarity_index(Double similarity_index) {
        this.similarity_index = similarity_index;
    }
}
