package it.polito.lombardo.esp_webserver.documents;

import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

public class IntervalEntity {
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

    public IntervalEntity() {

    }

    public IntervalEntity(Date start_time, Long start_time_millis, Date end_time, Long end_time_millis) {
        this.start_time = start_time;
        this.start_time_millis = start_time_millis;
        this.end_time = end_time;
        this.end_time_millis = end_time_millis;
    }

    /*getters and setters*/
    public Date getStart_time() {
        return start_time;
    }

    public void setStart_time(Date start_time) {
        this.start_time = start_time;
    }

    public Long getStart_time_millis() {
        return start_time_millis;
    }

    public void setStart_time_millis(Long start_time_millis) {
        this.start_time_millis = start_time_millis;
    }

    public Date getEnd_time() {
        return end_time;
    }

    public void setEnd_time(Date end_time) {
        this.end_time = end_time;
    }

    public Long getEnd_time_millis() {
        return end_time_millis;
    }

    public void setEnd_time_millis(Long end_time_millis) {
        this.end_time_millis = end_time_millis;
    }
}
