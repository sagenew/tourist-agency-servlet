package com.tourism.controller.dto;

public class DiscountDTO {
    private double step;
    private double threshold;

    public static Builder builder() { return new Builder();}

    public static class Builder {
        private double step;
        private double threshold;

        public Builder step(double step) {
            this.step = step;
            return this;
        }

        public Builder threshold(double threshold) {
            this.threshold = threshold;
            return this;
        }

        public DiscountDTO build() {
            DiscountDTO discountDTO = new DiscountDTO();
            discountDTO.setStep(step);
            discountDTO.setThreshold(threshold);
            return discountDTO;
        }
    }

    public double getStep() {
        return step;
    }

    public void setStep(double step) {
        this.step = step;
    }

    public double getThreshold() {
        return threshold;
    }

    public void setThreshold(double threshold) {
        this.threshold = threshold;
    }
}

