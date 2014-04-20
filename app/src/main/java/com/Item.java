package com;

import android.os.Parcelable;

import java.io.Serializable;

public class Item implements Serializable {

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        if(description == "null"){
            this.description = "";
        }else {
            this.description = description;
        }
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    private String title;
    private String description;

    public Long getPrice() {
        return price;
    }

    public void setPrice(Long price) {
        this.price = price;
    }

    private Long price;


    public Item(String title, String description, Long price) {
        super();
        this.title = title;
        if(description == "null"){
            this.description = "";
        }else {
            this.description = description;
        }

        this.price = price;
    }
}