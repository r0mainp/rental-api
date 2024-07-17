package com.rental.api.dto;

public class RentalUpdateDto {
        private String name;
        private int surface;
        private double price;
        private String description;

        public String getName() {
            return name;
        }
        public int getSurface() {
            return surface;
        }
        public double getPrice() {
            return price;
        }
        public String getDescription() {
            return description;
        }

        
        public void setName(String name) {
            this.name = name;
        }
        public void setSurface(int surface) {
            this.surface = surface;
        }
        public void setPrice(double price) {
            this.price = price;
        }
        public void setDescription(String description) {
            this.description = description;
        }


}
