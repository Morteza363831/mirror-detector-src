package org.khu.utils;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum DomainNames {

    DISTRIBUTION("Distribution"),
    COUNTRY("Country"),
    MIRROR("Mirror"),
    UNKNOWN("Unknown");


    private final String name;

}
