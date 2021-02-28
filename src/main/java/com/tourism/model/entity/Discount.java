package com.tourism.model.entity;

public class Discount {
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

        public Discount build() {
            Discount discount = new Discount();
            discount.setStep(step);
            discount.setThreshold(threshold);
            return discount;
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
