package org.khu.system.mirror.domain.model;

import lombok.*;

@Setter
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Mirror {

    private String name;

    private String displayName;

    private String selfLink;

    private Boolean enabled;

    private String speed;

    private String baseUrl;

}
