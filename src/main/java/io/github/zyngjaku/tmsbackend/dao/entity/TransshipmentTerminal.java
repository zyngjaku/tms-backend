package io.github.zyngjaku.tmsbackend.dao.entity;

import javax.persistence.*;

@Entity
@Table(name = "TransshipmentTerminals")
public class TransshipmentTerminal {
    public enum Type {
        LOADING, UNLOADING
    }

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;
    @Column(nullable = false)
    private Float latitude;
    @Column(nullable = false)
    private Float longitude;
    private String description;
    @Column(name = "taking_time", nullable = false)
    private Long takingTime; //in seconds
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Type type;
    @Column(name = "order_no", nullable = false)
    private int orderNo;

    public TransshipmentTerminal() {
    }

    public TransshipmentTerminal(Float latitude, Float longitude, String description, Long takingTime, Type type, int orderNo) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.description = description;
        this.takingTime = takingTime;
        this.type = type;
        this.orderNo = orderNo;
    }

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    public Float getLatitude() {
        return latitude;
    }
    public void setLatitude(Float latitude) {
        this.latitude = latitude;
    }

    public Float getLongitude() {
        return longitude;
    }
    public void setLongitude(Float longitude) {
        this.longitude = longitude;
    }

    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }

    public Long getTakingTime() {
        return takingTime;
    }
    public void setTakingTime(Long takingTime) {
        this.takingTime = takingTime;
    }

    public Type getType() {
        return type;
    }
    public void setType(Type type) {
        this.type = type;
    }

    public int getOrderNo() {
        return orderNo;
    }
    public void setOrderNo(int orderNo) {
        this.orderNo = orderNo;
    }
}
