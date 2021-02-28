package com.tourism.controller.dto;

import com.tourism.model.entity.enums.HotelType;
import com.tourism.model.entity.enums.TourType;

public class TourDTO {
    private String name;
    private TourType type;
    private double price;
    private int groupSize;
    private HotelType hotel;
    private String description;

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private String name;
        private TourType type;
        private double price;
        private int groupSize;
        private HotelType hotel;
        private String description;

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Builder type(TourType type) {
            this.type = type;
            return this;
        }

        public Builder price(double price) {
            this.price = price;
            return this;
        }

        public Builder groupSize(int groupSize) {
            this.groupSize = groupSize;
            return this;
        }

        public Builder hotel(HotelType hotel) {
            this.hotel = hotel;
            return this;
        }

        public Builder description(String description) {
            this.description = description;
            return this;
        }

        public TourDTO build() {
            TourDTO tourDTO = new TourDTO();
            tourDTO.setName(name);
            tourDTO.setType(type);
            tourDTO.setPrice(price);
            tourDTO.setGroupSize(groupSize);
            tourDTO.setHotel(hotel);
            tourDTO.setDescription(description);
            return tourDTO;
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public TourType getType() {
        return type;
    }

    public void setType(TourType type) {
        this.type = type;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getGroupSize() {
        return groupSize;
    }

    public void setGroupSize(int groupSize) {
        this.groupSize = groupSize;
    }

    public HotelType getHotel() {
        return hotel;
    }

    public void setHotel(HotelType hotel) {
        this.hotel = hotel;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
