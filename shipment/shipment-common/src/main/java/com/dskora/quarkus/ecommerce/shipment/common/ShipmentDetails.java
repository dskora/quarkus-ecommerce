package com.dskora.quarkus.ecommerce.shipment.common;

import jakarta.persistence.Embeddable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Embeddable
public class ShipmentDetails {
    @Enumerated(EnumType.STRING)
    private ShipmentProvider shipmentProvider;

    private String shipmentAddress;

    public ShipmentDetails() {}

    public ShipmentDetails(ShipmentProvider shipmentProvider, String address) {
        this.shipmentProvider = shipmentProvider;
        this.shipmentAddress = address;
    }
}
