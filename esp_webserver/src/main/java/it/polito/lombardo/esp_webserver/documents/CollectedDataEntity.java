package it.polito.lombardo.esp_webserver.documents;

import com.sun.org.apache.xpath.internal.operations.Bool;
import it.polito.lombardo.esp_webserver.entities.CollectedData;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Document(collection = "collected_data")
public class CollectedDataEntity {
    /*
    * the id in the mongo collection
    * */
    @Id
    private String _id;

    /*
    * the chipid of the device that has sent the data
    * */
    @Indexed
    @Field("chip_id")
    private String chipId;

    /*
    * the time in which the collected data has been received
    * */
    @Indexed
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    @Field("upload_timestamp")
    private Date uploadTimestamp;

    /*
    * the timestamp in millis
    * */
    @Field("upload_timestamp_millis")
    private Long uploadTimestampInMillis;

    /*
    * the amout of time between two uploads
    * */
    @Field("upload_timeout")
    private Integer uploadTimeout;

    /*
    * the list of all known devices
    * */
    private List<CollectedDeviceEntity> collectedDevices;

    /*getters and setters*/
    public CollectedDataEntity() {
        collectedDevices = new ArrayList<CollectedDeviceEntity>();
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getChipId() {
        return chipId;
    }

    public void setChipId(String chipId) {
        this.chipId = chipId;
    }

    public Date getUploadTimestamp() {
        return uploadTimestamp;
    }

    public void setUploadTimestamp(Date uploadTimestamp) {
        this.uploadTimestamp = uploadTimestamp;
    }

    public Long getUploadTimestampInMillis() {
        return uploadTimestampInMillis;
    }

    public void setUploadTimestampInMillis(Long uploadTimestampInMillis) {
        this.uploadTimestampInMillis = uploadTimestampInMillis;
    }

    public Integer getUploadTimeout() {
        return uploadTimeout;
    }

    public void setUploadTimeout(Integer uploadTimeout) {
        this.uploadTimeout = uploadTimeout;
    }

    public List<CollectedDeviceEntity> getCollectedDevices() {
        return collectedDevices;
    }

    public void setCollectedDevices(List<CollectedDeviceEntity> collectedDevices) {
        this.collectedDevices = collectedDevices;
    }

}
