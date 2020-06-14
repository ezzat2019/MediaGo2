package com.example.programmer.mediago2.entity;

public class AlbumEntity {
    private String name,id ,albume_id,name_aritist,albume_artist;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAlbume_id() {
        return albume_id;
    }

    public void setAlbume_id(String albume_id) {
        this.albume_id = albume_id;
    }

    public String getName_aritist() {
        return name_aritist;
    }

    public void setName_aritist(String name_aritist) {
        this.name_aritist = name_aritist;
    }

    public String getAlbume_artist() {
        return albume_artist;
    }

    public void setAlbume_artist(String albume_artist) {
        this.albume_artist = albume_artist;
    }

    public AlbumEntity(String name, String id, String albume_id, String name_aritist, String albume_artist) {
        this.name = name;
        this.id = id;
        this.albume_id = albume_id;
        this.name_aritist = name_aritist;
        this.albume_artist = albume_artist;
    }

    public AlbumEntity() {
    }
}
