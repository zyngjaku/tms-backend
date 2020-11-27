package io.github.zyngjaku.tmsbackend.dao.entity;

import com.google.gson.annotations.Expose;
import io.github.zyngjaku.tmsbackend.dao.VehicleRepo;
import io.github.zyngjaku.tmsbackend.request.VehicleRequest;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "Vehicles")
public class Vehicle {
    @Expose
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;
    @Column(nullable = false)
    @Expose
    private String name;
    @Expose
    @Column(unique = true)
    private String registration;
    @Expose
    @Column(name = "review_date")
    private LocalDate reviewDate;
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "company_id", nullable = false)
    private Company company;

    public Vehicle() {
    }

    public Vehicle(String name, String registration, Company company) {
        this.name = name;
        this.registration = registration;
        this.company = company;
    }

    public Vehicle(String name, String registration, LocalDate reviewDate, Company company) {
        this(name, registration, company);
        this.reviewDate = reviewDate;
    }

    public Vehicle(VehicleRequest vehicleRequest, Company company) {
        this(vehicleRequest.getName(), vehicleRequest.getRegistration(), vehicleRequest.getReviewDate(), company);
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public String getRegistration() {
        return registration;
    }
    public void setRegistration(String registration) {
        this.registration = registration;
    }

    public LocalDate getReviewDate() {
        return reviewDate;
    }
    public void setReviewDate(LocalDate reviewDate) {
        this.reviewDate = reviewDate;
    }

    public Company getCompany() {
        return company;
    }
    public void setCompany(Company company) {
        this.company = company;
    }
}
