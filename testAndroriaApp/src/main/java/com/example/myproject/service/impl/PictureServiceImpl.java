package com.example.myproject.service.impl;

import com.example.myproject.model.binding.PictureBindingModel;
import com.example.myproject.model.entities.PictureEntity;
import com.example.myproject.model.entities.enums.FacilityEnum;
import com.example.myproject.model.view.PictureViewModel;
import com.example.myproject.repository.PictureRepository;
import com.example.myproject.service.CloudinaryImage;
import com.example.myproject.service.CloudinaryService;
import com.example.myproject.service.PictureService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PictureServiceImpl implements PictureService {
    private final CloudinaryService cloudinaryService;
    private final PictureRepository pictureRepository;
    private final ModelMapper modelMapper;

    public PictureServiceImpl(CloudinaryService cloudinaryService, PictureRepository pictureRepository, ModelMapper modelMapper) {
        this.cloudinaryService = cloudinaryService;
        this.pictureRepository = pictureRepository;
        this.modelMapper = modelMapper;
    }


    @Override
    public void initPictures() {
        if (this.pictureRepository.count() == 0) {

            String url = "https://res.cloudinary.com/malmsuite/image/upload/v1637229674/Anroria2/doublerooom_zlxbpe.jpg";
            String public_id = "zlxbpe";
            FacilityEnum facility = FacilityEnum.DOUBLE_ROOM;
            addFacilitiesPictures(url, facility, public_id);

            url = "https://res.cloudinary.com/malmsuite/image/upload/v1637229674/Anroria2/apartment_rnulau.jpg";
            facility = FacilityEnum.APARTMENT;
            public_id = "rnulau";
            addFacilitiesPictures(url, facility, public_id);

            url = "https://res.cloudinary.com/malmsuite/image/upload/v1637229674/Anroria2/studio_xqoymp.jpg";
            public_id = "xqoymp";
            facility = FacilityEnum.STUDIO;
            addFacilitiesPictures(url, facility, public_id);

            url = "https://res.cloudinary.com/malmsuite/image/upload/v1637229674/Anroria2/parking_zelew1.jpg";
            public_id = "zelew1";
            facility = FacilityEnum.PARKING;
            addFacilitiesPictures(url, facility, public_id);

            url = "https://res.cloudinary.com/malmsuite/image/upload/v1637229675/Anroria2/restaurant_tyjsiw.jpg";
            public_id = "tyjsiw";
            facility = FacilityEnum.RESTAURANT;
            addFacilitiesPictures(url, facility, public_id);

        }
    }

    private void addFacilitiesPictures(String url, FacilityEnum facility, String public_id) {
        for (int i = 1; i <= 6; i++) {
            PictureEntity pictureEntity = new PictureEntity();
            pictureEntity.setPublicId(public_id);
            pictureEntity.setUrl(url);
            pictureEntity.setFacility(facility);
            pictureEntity.setTitle("dr" + i);
            this.pictureRepository.save(pictureEntity);
        }
    }


    @Override
    public void addNewPicture(PictureBindingModel pictureBindingModel) throws IOException {
        MultipartFile picture = pictureBindingModel.getPicture();
        String title = pictureBindingModel.getTitle();
        FacilityEnum facility = pictureBindingModel.getFacility();
        PictureEntity pictureEntity = createPictureEntity(picture, title);
        pictureEntity.setFacility(facility);
        this.pictureRepository.save(pictureEntity);
    }

    @Override
    public List<PictureEntity> getAllPicturesBy(FacilityEnum facilityEnum) {
        return this.pictureRepository.getAllByFacility(facilityEnum);
    }

    @Override
    public List<PictureViewModel> getAllDoubleRoomPictures() {
        FacilityEnum doubleRoom = FacilityEnum.DOUBLE_ROOM;
        List<PictureEntity> allPicturesBy = getAllPicturesBy(doubleRoom);
        return allPicturesBy.stream().map(pictureEntity ->
                        this.modelMapper.map(pictureEntity, PictureViewModel.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<PictureViewModel> getAllApartmentPictures() {
        FacilityEnum apartment = FacilityEnum.APARTMENT;
        List<PictureEntity> allPicturesBy = getAllPicturesBy(apartment);
        return allPicturesBy.stream().map(pictureEntity ->
                        this.modelMapper.map(pictureEntity, PictureViewModel.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<PictureViewModel> getAllStudioPictures() {
        FacilityEnum studio = FacilityEnum.STUDIO;
        List<PictureEntity> allPicturesBy = getAllPicturesBy(studio);
        return allPicturesBy.stream().map(pictureEntity ->
                        this.modelMapper.map(pictureEntity, PictureViewModel.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<PictureViewModel> getAllParkingPictures() {
        FacilityEnum parking = FacilityEnum.PARKING;
        List<PictureEntity> allPicturesBy = getAllPicturesBy(parking);
        return allPicturesBy.stream().map(pictureEntity ->
                        this.modelMapper.map(pictureEntity, PictureViewModel.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<PictureViewModel> getAllRestaurantPictures() {
        FacilityEnum restaurant = FacilityEnum.RESTAURANT;
        List<PictureEntity> allPicturesBy = getAllPicturesBy(restaurant);
        return allPicturesBy.stream().map(pictureEntity ->
                        this.modelMapper.map(pictureEntity, PictureViewModel.class))
                .collect(Collectors.toList());
    }


    private PictureEntity createPictureEntity(MultipartFile file, String title) throws IOException {
        CloudinaryImage uploaded = this.cloudinaryService.upload(file);
        return new PictureEntity()
                .setPublicId(uploaded.getPublicId())
                .setTitle(title)
                .setUrl(uploaded.getUrl());
    }
}
