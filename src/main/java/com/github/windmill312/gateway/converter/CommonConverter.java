package com.github.windmill312.gateway.converter;

import com.github.windmill312.coffeehouse.grpc.model.v1.GLocation;
import com.github.windmill312.common.grpc.model.GPageable;
import com.github.windmill312.common.grpc.model.GUuid;

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
