package com.tourism.model.entity;


import com.tourism.model.entity.enums.HotelType;
import com.tourism.model.entity.enums.TourType;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.util.Objects;

public class Tour {
    private long id;
    private String name;
    private TourType type;
    private double price;
    private int groupSize;
    private HotelType hotel;
    private String description;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Tour tour = (Tour) o;
        return id == tour.id &&
                Double.compare(tour.price, price) == 0 &&
                groupSize == tour.groupSize &&
                isHot == tour.isHot &&
                name.equals(tour.name) &&
                type == tour.type &&
                hotel == tour.hotel &&
                description.equals(tour.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, type, price, groupSize, hotel, description, isHot);
    }

    private boolean isHot;

    public Tour() { }

    public Tour(long id, String name, TourType type, double price, int groupSize, HotelType hotel, String description, boolean isHot) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.price = price;
        this.groupSize = groupSize;
        this.hotel = hotel;
        this.description = description;
        this.isHot = isHot;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private long id;
        private String name;
        private TourType type;
        private double price;
        private int groupSize;
        private HotelType hotel;
        private String description;
        private boolean isHot;

        public Builder id(long id) {
            this.id = id;
            return this;
        }

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

        public Builder isHot(boolean isHot) {
            this.isHot = isHot;
            return this;
        }

        public Tour build() {
            Tour tour = new Tour();
            tour.setId(id);
            tour.setName(name);
            tour.setType(type);
            tour.setPrice(price);
            tour.setGroupSize(groupSize);
            tour.setHotel(hotel);
            tour.setDescription(description);
            tour.setHot(isHot);
            return tour;
        }
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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

    public boolean isHot() {
        return isHot;
    }

    public void setHot(boolean hot) {
        isHot = hot;
    }

    public void switchHot() {
        this.isHot = !isHot;
    }

    @Override
    public String toString() {
        return "Tour{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", type=" + type +
                ", price=" + price +
                ", groupSize=" + groupSize +
                ", hotel=" + hotel +
                ", description='" + description + '\'' +
                ", isHot=" + isHot +
                '}';
    }
}
