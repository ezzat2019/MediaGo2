package com.example.programmer.mediago2.entity;

public class AritistEntity {
    private String id,name;
private String name_abum_ar;

    public String getName_abum_ar() {
        return name_abum_ar;
    }

    public void setName_abum_ar(String name_abum_ar) {
        this.name_abum_ar = name_abum_ar;
    }

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

    public AritistEntity() {
    }

    public AritistEntity(String id, String name) {
        this.id = id;
        this.name = name;
    }
}
