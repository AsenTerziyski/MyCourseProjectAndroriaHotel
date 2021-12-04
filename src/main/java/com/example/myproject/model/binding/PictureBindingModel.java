package com.example.myproject.model.binding;

import com.example.myproject.model.entities.enums.FacilityEnum;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.Enumerated;
import javax.validation.constraints.NotNull;

public class PictureBindingModel {

    private String title;
    private MultipartFile picture;
    private FacilityEnum facility;

    public PictureBindingModel() {
    }

    public String getTitle() {
        return title;
    }

    public PictureBindingModel setTitle(String title) {
        this.title = title;
        return this;
    }

    public MultipartFile getPicture() {
        return picture;
    }

    public PictureBindingModel setPicture(MultipartFile picture) {
        this.picture = picture;
        return this;
    }

    @Enumerated
    @NotNull
    public FacilityEnum getFacility() {
        return facility;
    }

    public PictureBindingModel setFacility(FacilityEnum facility) {
        this.facility = facility;
        return this;
    }
}
