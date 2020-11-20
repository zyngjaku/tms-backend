package io.github.zyngjaku.tmsbackend.dao.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.github.zyngjaku.tmsbackend.utils.Utils;
import org.json.JSONException;

import javax.persistence.*;
import java.io.IOException;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "Orders")
public class Order {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;
    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "company_id", nullable = false)
    private Company company;
    @JsonFormat(pattern = "HH:mm dd-MM-yyyy")
    @Column(name = "estimated_time", nullable = false)
    private Long estimatedTime; //in seconds
    @JsonFormat(pattern = "HH:mm dd-MM-yyyy")
    @Column(name = "min_arrival_time", nullable = false)
    private Date minArrivalTime;
    @JsonFormat(pattern = "HH:mm dd-MM-yyyy")
    @Column(name = "max_arrival_time", nullable = false)
    private Date maxArrivalTime;
    @JsonFormat(pattern = "HH:mm dd-MM-yyyy")
    @Column(name = "calculated_start_time")
    private LocalDate calculatedStartTime;
    @JsonFormat(pattern = "HH:mm dd-MM-yyyy")
    @Column(name = "calculated_end_time")
    private LocalDate calculatedEndTime;
    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "user_id")
    private User user;
    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "vehicle_id")
    private Vehicle vehicle;
    @Column(name = "is_calculated_time_fixed")
    private boolean isCalculatedTimeFixed;
    private String description;
    @OneToMany(cascade = CascadeType.MERGE)
    @JoinTable(
            name="MappingOrderToTransshipmentTerminal",
            joinColumns = @JoinColumn(name="order_id"),
            inverseJoinColumns = @JoinColumn(name="transshipment_terminal_id")
    )
    @Column(nullable = false)
    private List<TransshipmentTerminal> transshipmentTerminalList;

    public Order() {
    }

    public Order(Company company, Date minArrivalTime, Date maxArrivalTime, boolean isCalculatedTimeFixed, String description, List<TransshipmentTerminal> transshipmentTerminalList) {
        this.company = company;
        this.minArrivalTime = minArrivalTime;
        this.maxArrivalTime = maxArrivalTime;
        this.isCalculatedTimeFixed = isCalculatedTimeFixed;
        this.description = description;
        setTransshipmentTerminalList(transshipmentTerminalList);
    }

    public Order(Company company, Date minArrivalTime, Date maxArrivalTime, User user, Vehicle vehicle, boolean isCalculatedTimeFixed, String description, List<TransshipmentTerminal> transshipmentTerminalList) {
        this.company = company;
        this.minArrivalTime = minArrivalTime;
        this.maxArrivalTime = maxArrivalTime;
        this.vehicle = vehicle;
        this.user = user;
        this.isCalculatedTimeFixed = isCalculatedTimeFixed;
        this.description = description;
        setTransshipmentTerminalList(transshipmentTerminalList);
    }

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    public Company getCompany() {
        return company;
    }
    public void setCompany(Company company) {
        this.company = company;
    }

    public Long getEstimatedTime() {
        return estimatedTime;
    }
    public void setEstimatedTime(Long estimatedTime) {
        this.estimatedTime = estimatedTime;
    }

    public Date getMinArrivalTime() {
        return minArrivalTime;
    }
    public void setMinArrivalTime(Date minArrivalTime) {
        this.minArrivalTime = minArrivalTime;
    }

    public Date getMaxArrivalTime() {
        return maxArrivalTime;
    }
    public void setMaxArrivalTime(Date maxArrivalTime) {
        this.maxArrivalTime = maxArrivalTime;
    }

    public LocalDate getCalculatedStartTime() {
        return calculatedStartTime;
    }
    public void setCalculatedStartTime(LocalDate calculatedStartTime) {
        this.calculatedStartTime = calculatedStartTime;
    }

    public LocalDate getCalculatedEndTime() {
        return calculatedEndTime;
    }
    public void setCalculatedEndTime(LocalDate calculatedEndTime) {
        this.calculatedEndTime = calculatedEndTime;
    }

    public boolean isCalculatedTimeFixed() {
        return isCalculatedTimeFixed;
    }
    public void setCalculatedTimeFixed(boolean calculatedTimeFixed) {
        isCalculatedTimeFixed = calculatedTimeFixed;
    }

    public User getUser() {
        return user;
    }
    public void setUser(User user) {
        this.user = user;
    }

    public Vehicle getVehicle() {
        return vehicle;
    }
    public void setVehicle(Vehicle vehicle) {
        this.vehicle = vehicle;
    }

    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }

    public List<TransshipmentTerminal> getTransshipmentTerminalList() {
        return transshipmentTerminalList;
    }
    public void setTransshipmentTerminalList(List<TransshipmentTerminal> transshipmentTerminalList) {
        this.transshipmentTerminalList = transshipmentTerminalList;

        //this.estimatedTime = Utils.testGET(transshipmentTerminalList);
        this.estimatedTime = 0L;
        for (TransshipmentTerminal tran : transshipmentTerminalList) {
            this.estimatedTime += tran.getTakingTime();
        }
    }
}
