package com.fruizc.sites.modeldb;

import org.greenrobot.greendao.annotation.*;

// THIS CODE IS GENERATED BY greenDAO, EDIT ONLY INSIDE THE "KEEP"-SECTIONS

// KEEP INCLUDES - put your custom includes here
// KEEP INCLUDES END

/**
 * Entity mapped to table "PLACE".
 */
@Entity
public class Place {

    @Id(autoincrement = true)
    private Long idPlace;
    private String placeName;
    private String descPlace;
    private String createdAt;
    private String picture;

    // KEEP FIELDS - put your custom fields here
    // KEEP FIELDS END

    @Generated
    public Place() {
    }

    public Place(Long idPlace) {
        this.idPlace = idPlace;
    }

    @Generated
    public Place(Long idPlace, String placeName, String descPlace, String createdAt, String picture) {
        this.idPlace = idPlace;
        this.placeName = placeName;
        this.descPlace = descPlace;
        this.createdAt = createdAt;
        this.picture = picture;
    }

    public Long getIdPlace() {
        return idPlace;
    }

    public void setIdPlace(Long idPlace) {
        this.idPlace = idPlace;
    }

    public String getPlaceName() {
        return placeName;
    }

    public void setPlaceName(String placeName) {
        this.placeName = placeName;
    }

    public String getDescPlace() {
        return descPlace;
    }

    public void setDescPlace(String descPlace) {
        this.descPlace = descPlace;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    // KEEP METHODS - put your custom methods here
    // KEEP METHODS END

}
