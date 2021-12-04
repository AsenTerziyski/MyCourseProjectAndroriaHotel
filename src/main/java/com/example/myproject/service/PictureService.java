package com.example.myproject.service;

import com.example.myproject.model.binding.PictureBindingModel;
import com.example.myproject.model.entities.PictureEntity;
import com.example.myproject.model.entities.enums.FacilityEnum;
import com.example.myproject.model.view.PictureViewModel;

import java.io.IOException;
import java.util.List;

public interface PictureService {
    void addNewPicture(PictureBindingModel pictureBindingModel) throws IOException;
    List<PictureEntity> getAllPicturesBy(FacilityEnum facilityEnum);
    List<PictureViewModel> getAllDoubleRoomPictures();
    List<PictureViewModel> getAllApartmentPictures();
    List<PictureViewModel> getAllStudioPictures();
    List<PictureViewModel> getAllParkingPictures();
    List<PictureViewModel> getAllRestaurantPictures();

    void initPictures();
}
