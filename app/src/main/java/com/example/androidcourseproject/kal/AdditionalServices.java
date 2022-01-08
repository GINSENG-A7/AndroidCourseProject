package com.example.androidcourseproject.kal;

public class AdditionalServices {
    private final int as_id;
    private final int mini_bar;
    private final int clothes_washing;
    private final int telephone;
    private final int intercity_telephone;
    private final int food;

    public AdditionalServices(int as_id, int mini_bar, int clothes_washing, int telephone, int intercity_telephone, int food) {
        this.as_id = as_id;
        this.mini_bar = mini_bar;
        this.clothes_washing = clothes_washing;
        this.telephone = telephone;
        this.intercity_telephone = intercity_telephone;
        this.food = food;
    }

    public int getAs_id() {
        return as_id;
    }

    public int getMini_bar() {
        return mini_bar;
    }

    public int getClothes_washing() {
        return clothes_washing;
    }

    public int getTelephone() {
        return telephone;
    }

    public int getIntercity_telephone() {
        return intercity_telephone;
    }

    public int getFood() {
        return food;
    }
}
