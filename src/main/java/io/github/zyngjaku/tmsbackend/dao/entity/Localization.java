package io.github.zyngjaku.tmsbackend.dao.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.google.gson.annotations.Expose;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Date;

@Entity
@Table(name = "Localizations")
public class Localization {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;
    @Column(nullable = true)
    private Float latitude;
    @Column(nullable = true)
    private Float longitude;
    @Column(nullable = true)
    private int heading;
    @Column(nullable = true)
    private int speed;
    @JsonFormat(pattern = "HH:mm dd-MM-yyyy")
    @Column(name = "last_update", nullable = false)
    private Date lastUpdate;

    public Localization() {
        setLastUpdate();
    }

    public Localization(Float latitude, Float longitude, int heading, int speed) {
        this();
        setLatitude(latitude);
        setLongitude(longitude);
        setHeading(heading);
        setSpeed(speed);
    }

    public Float getLatitude() {
        return latitude;
    }
    public void setLatitude(Float latitude) {
        setLastUpdate();
        this.latitude = latitude;
    }

    public Float getLongitude() {
        return longitude;
    }
    public void setLongitude(Float longitude) {
        setLastUpdate();
        this.longitude = longitude;
    }

    public int getHeading() {
        return heading;
    }
    public void setHeading(int heading) {
        setLastUpdate();
        this.heading = heading;
    }

    public int getSpeed() {
        return speed;
    }
    public void setSpeed(int speed) {
        setLastUpdate();
        this.speed = speed;
    }

    public Date getLastUpdate() {
        return lastUpdate;
    }
    public void setLastUpdate() {
        this.lastUpdate = new Date();
    }
}
