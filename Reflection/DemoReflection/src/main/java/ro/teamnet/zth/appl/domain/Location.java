package ro.teamnet.zth.appl.domain;

/**
 * Created by user on 7/7/2016.
 */

import ro.teamnet.zth.api.annotation.Column;
import ro.teamnet.zth.api.annotation.Id;
import ro.teamnet.zth.api.annotation.Table;

import ro.teamnet.zth.api.annotation.Column;
import ro.teamnet.zth.api.annotation.Id;
import ro.teamnet.zth.api.annotation.Table;

/**
 * Created by user on 7/7/2016.
 */
@Table(name="location")
public class Location {

    @Id(name="location_id")
    private long id;
    @Column(name="street_address")
    private String streetAddress;
    @Column(name="postal_code")
    private String postalCode;
    @Column(name="city")
    private String city;
    @Column(name="state_province")
    private String stateProvince;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getStreetAddress() {
        return streetAddress;
    }

    public void setStreetAddress(String streetAdress) {
        this.streetAddress = streetAdress;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getStateProvince() {
        return stateProvince;
    }

    public void setStateProvince(String stateProvince) {
        this.stateProvince = stateProvince;
    }

    @Override
    public boolean equals(Object obj) {
       /* if(obj instanceof Location)
        {
            if(id == ((Location) obj).getId())
                return
        }*/
        return super.equals(obj);
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
