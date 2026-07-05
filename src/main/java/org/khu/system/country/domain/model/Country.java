package org.khu.system.country.domain.model;

import lombok.*;

@Setter
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Country {

    private String selfLink;

    private String resourceTypeLink;

    private String code2;

    private String code3;

    private String name;

    private String title;

    private String description;

}
