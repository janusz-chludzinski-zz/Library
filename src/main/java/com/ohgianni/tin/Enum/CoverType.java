package com.ohgianni.tin.Enum;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public enum CoverType {
    HARD("Twarda"), SOFT("Miękka");

    private final String coverType;

    CoverType (String coverType) {
        this.coverType = coverType;
    }

    public String getCoverType() {
        return coverType;
    }

    public static List<String> getAllAsString() {
        return Arrays.stream(CoverType.values())
                .map(Enum::toString)
                .collect(Collectors.toList());
    }
}
