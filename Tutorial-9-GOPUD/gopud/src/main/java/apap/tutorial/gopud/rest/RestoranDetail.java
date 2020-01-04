package apap.tutorial.gopud.rest;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Date;


@JsonIgnoreProperties(ignoreUnknown = true)
public class RestoranDetail {

    private String status;

    @JsonProperty(value = "restaurant-license")
    private Integer restaurantLicense;

    @JsonProperty(value = "valid-until")
    private Date validUntil;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getRestaurantLicense() {
        return restaurantLicense;
    }

    public void setRestaurantLicense(Integer restaurantLicense) {
        this.restaurantLicense = restaurantLicense;
    }

    public Date getValidUntil() {
        return validUntil;
    }

    public void setValidUntil(Date validUntil) {
        this.validUntil = validUntil;
    }
}
