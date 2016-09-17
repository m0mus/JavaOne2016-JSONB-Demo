package com.oracle.jsonb.demo.model;

import javax.json.bind.annotation.JsonbCreator;

/**
 * A container to carry an animal.
 * @param <A>
 */
public class Carrier<A extends Animal> {
    public enum TYPE {
        BAG, CRATE, TROLLEY
    }

    private TYPE carrierType;
    private A carriedPet;

    @JsonbCreator
    public Carrier(TYPE carrierType, A carriedPet) {
        this.carrierType = carrierType;
        this.carriedPet = carriedPet;
    }

    public TYPE getCarrierType() {
        return carrierType;
    }

    public A getCarriedPet() {
        return carriedPet;
    }

    public void setCarrierType(TYPE carrierType) {
        this.carrierType = carrierType;
    }

    public void setCarriedPet(A carriedPet) {
        this.carriedPet = carriedPet;
    }

    @Override
    public String toString() {
        return "Carrier{" +
                "carrierType=" + carrierType +
                ", carriedPet=" + carriedPet +
                '}';
    }
}
