package io.github.zyngjaku.tmsbackend.request;

import java.io.Serializable;
import java.time.LocalDate;

public class VehicleRequest implements Serializable {
    private String name;
    private String registration;
    private LocalDate reviewDate;
    private String companyName;

    public VehicleRequest() {
    }

    public VehicleRequest(String name, String registration, LocalDate reviewDate, String companyName) {
        this.name = name;
        this.registration = registration;
        this.reviewDate = reviewDate;
        this.companyName = companyName;
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

    public String getCompanyName() {
        return companyName;
    }
    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }
}
