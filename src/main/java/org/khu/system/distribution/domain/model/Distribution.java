package org.khu.system.distribution.domain.model;

import lombok.*;

@Setter
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Distribution {

    private String selfLink;

    private String name;

    private String displayName;

    private String title;

    private Boolean isActive;

    private Boolean isPrivate;

    private String iconLink;

    private String logoLink;

    private String archiveMirrorsCollectionLink;

    private String cdImageMirrorsCollectionLink;

}
