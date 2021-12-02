package com.example.myproject.model.view;

public class PictureViewModel {
    private String title;
    private String publicId;
    private String url;

    public PictureViewModel() {
    }

    public String getTitle() {
        return title;
    }

    public PictureViewModel setTitle(String title) {
        this.title = title;
        return this;
    }

    public String getPublicId() {
        return publicId;
    }

    public PictureViewModel setPublicId(String publicId) {
        this.publicId = publicId;
        return this;
    }

    public String getUrl() {
        return url;
    }

    public PictureViewModel setUrl(String url) {
        this.url = url;
        return this;
    }
}
