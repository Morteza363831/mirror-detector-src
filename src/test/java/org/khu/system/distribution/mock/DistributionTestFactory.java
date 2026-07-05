package org.khu.system.distribution.mock;

import org.khu.system.distribution.domain.dto.DistributionResponseDto;
import org.khu.system.distribution.domain.dto.DistributionResultDto;
import org.khu.system.distribution.domain.model.Distribution;

import java.util.List;

public final class DistributionTestFactory {

    private DistributionTestFactory() {
    }

    /* ---------------------------------------------------
     * Domain Models
     * --------------------------------------------------- */

    public static Distribution ubuntu() {

        return Distribution.builder()
                .selfLink("https://api.launchpad.net/devel/ubuntu")
                .name("ubuntu")
                .displayName("Ubuntu")
                .title("Ubuntu")
                .isActive(true)
                .isPrivate(false)
                .iconLink("https://launchpad.net/icons/ubuntu.png")
                .logoLink("https://launchpad.net/logos/ubuntu.png")
                .archiveMirrorsCollectionLink("https://api.launchpad.net/devel/ubuntu/+archive-mirrors")
                .cdImageMirrorsCollectionLink("https://api.launchpad.net/devel/ubuntu/+cdimage-mirrors")
                .build();
    }

    public static Distribution arch() {

        return Distribution.builder()
                .selfLink("https://api.launchpad.net/devel/arch")
                .name("arch")
                .displayName("Arch Linux")
                .title("Arch")
                .isActive(false)
                .isPrivate(true)
                .iconLink("https://launchpad.net/icons/arch.png")
                .logoLink("https://launchpad.net/logos/arch.png")
                .archiveMirrorsCollectionLink("https://api.launchpad.net/devel/arch/+archive-mirrors")
                .cdImageMirrorsCollectionLink("https://api.launchpad.net/devel/arch/+cdimage-mirrors")
                .build();
    }

    /* ---------------------------------------------------
     * Launchpad Response DTOs
     * --------------------------------------------------- */

    public static DistributionResponseDto ubuntuResponseDto() {

        return new DistributionResponseDto(
                "https://api.launchpad.net/devel/ubuntu",
                "ubuntu",
                "Ubuntu",
                "Ubuntu",
                true,
                false,
                "https://launchpad.net/icons/ubuntu.png",
                "https://launchpad.net/logos/ubuntu.png",
                "https://api.launchpad.net/devel/ubuntu/+archive-mirrors",
                "https://api.launchpad.net/devel/ubuntu/+cdimage-mirrors"
        );
    }

    public static DistributionResponseDto archResponseDto() {

        return new DistributionResponseDto(
                "https://api.launchpad.net/devel/arch",
                "arch",
                "Arch Linux",
                "Arch",
                false,
                true,
                "https://launchpad.net/icons/arch.png",
                "https://launchpad.net/logos/arch.png",
                "https://api.launchpad.net/devel/arch/+archive-mirrors",
                "https://api.launchpad.net/devel/arch/+cdimage-mirrors"
        );
    }

    public static List<DistributionResponseDto> responseDtoList() {

        return List.of(
                ubuntuResponseDto(),
                archResponseDto()
        );
    }

    /* ---------------------------------------------------
     * Result DTOs
     * --------------------------------------------------- */

    public static DistributionResultDto ubuntuResultDto() {

        return new DistributionResultDto(
                "https://launchpad.net/logos/ubuntu.png",
                "ubuntu",
                "Ubuntu",
                true,
                false,
                "https://api.launchpad.net/devel/ubuntu/+cdimage-mirrors"
        );
    }

    public static DistributionResultDto archResultDto() {

        return new DistributionResultDto(
                "https://launchpad.net/logos/arch.png",
                "arch",
                "Arch Linux",
                false,
                true,
                "https://api.launchpad.net/devel/arch/+cdimage-mirrors"
        );
    }

    public static List<DistributionResultDto> resultDtoList() {

        return List.of(
                ubuntuResultDto(),
                archResultDto()
        );
    }

}