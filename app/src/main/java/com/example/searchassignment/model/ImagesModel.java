package com.example.searchassignment.model;

public class ImagesModel {
    String blur_hash ;
    String updated_at;
    UrlsModel urlsModel;

    public ImagesModel(String blur_hash,String updated_at,UrlsModel urlsModel)
    {
        this.blur_hash=blur_hash;
        this.updated_at=updated_at;
        this.urlsModel = urlsModel;

    }
    public ImagesModel(){

    }

    public String getBlur_hash() {
        return blur_hash;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    public void setBlur_hash(String blur_hash) {
        this.blur_hash = blur_hash;
    }

    public void setUpdated_at(String updated_at) {
        this.updated_at = updated_at;
    }

    public UrlsModel getUrlsModel() {
        return urlsModel;
    }

    public void setUrlsModel(UrlsModel urlsModel) {
        this.urlsModel = urlsModel;
    }
}
