package com.sychev.gateway.converter;

import com.sychev.coffeehouse.grpc.model.v1.GLocation;
import com.sychev.common.grpc.model.GPageable;
import com.sychev.common.grpc.model.GUuid;

import java.util.UUID;

public class CommonConverter {

    public static GUuid convert(UUID uuid) {
        return GUuid.newBuilder()
                .setUuid(String.valueOf(uuid))
                .build();
    }

    public static UUID convert(GUuid guuid) {
        return UUID.fromString(guuid.getUuid());
    }

    public static GPageable convert(int page, int size) {
        return GPageable.newBuilder()
                .setPage(page)
                .setSize(size)
                .build();
    }

    public static GLocation convert(Double latitude, Double longitude) {
        return GLocation.newBuilder()
                .setLatitude(latitude)
                .setLongitude(longitude)
                .build();
    }
}
