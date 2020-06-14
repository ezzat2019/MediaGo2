package com.example.programmer.mediago2.entity;

public class MediaEntity {
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAlbume_id() {
        return albume_id;
    }

    public void setAlbume_id(String albume_id) {
        this.albume_id = albume_id;
    }

    public String getArtist_id() {
        return artist_id;
    }

    public void setArtist_id(String artist_id) {
        this.artist_id = artist_id;
    }

    public MediaEntity(String id, String name, String albume_id, String artist_id) {
        this.id = id;
        this.name = name;
        this.albume_id = albume_id;
        this.artist_id = artist_id;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public MediaEntity() {
    }

    public MediaEntity(String id, String name, String albume_id, String artist_id, String duration) {
        this.id = id;
        this.name = name;
        this.albume_id = albume_id;
        this.artist_id = artist_id;
        this.duration = duration;
    }

    private String id,name,albume_id,artist_id,duration;
}
