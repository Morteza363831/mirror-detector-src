package org.khu.system.country.mock;

import org.khu.system.country.domain.dto.CountryResponseDto;
import org.khu.system.country.domain.dto.CountryResultDto;
import org.khu.system.country.domain.model.Country;

import java.util.List;

public final class CountryTestFactory {

    private CountryTestFactory() {
    }

    /* ---------------------------------------------------
     * Domain Models
     * --------------------------------------------------- */

    public static Country iran() {

        Country country = new Country();

        country.setName("Iran");
        country.setCode2("IR");
        country.setCode3("IRN");
        country.setTitle("Iran");
        country.setDescription("Iran");
        country.setSelfLink("https://launchpad.net/+country/iran");

        return country;
    }

    public static Country germany() {

        Country country = new Country();

        country.setName("Germany");
        country.setCode2("DE");
        country.setCode3("DEU");
        country.setTitle("Germany");
        country.setDescription("Germany");
        country.setSelfLink("https://launchpad.net/+country/germany");

        return country;
    }

    /* ---------------------------------------------------
     * Response DTOs (Launchpad API)
     * --------------------------------------------------- */

    public static CountryResponseDto iranResponseDto() {

        return new CountryResponseDto(
                "https://launchpad.net/+country/iran",
                "https://api.launchpad.net/1.0/#country",
                "IR",
                "IRN",
                "Iran",
                "Iran",
                "Iran"
        );
    }

    public static CountryResponseDto germanyResponseDto() {

        return new CountryResponseDto(
                "https://launchpad.net/+country/germany",
                "https://api.launchpad.net/1.0/#country",
                "DE",
                "DEU",
                "Germany",
                "Germany",
                "Germany"
        );
    }

    public static List<CountryResponseDto> responseDtoList() {
        return List.of(
                iranResponseDto(),
                germanyResponseDto()
        );
    }

    /* ---------------------------------------------------
     * Result DTOs
     * --------------------------------------------------- */

    public static CountryResultDto iranResultDto() {

        return new CountryResultDto(
                "IR",
                "IRN",
                "Iran",
                "Iran",
                "Iran"
        );
    }

    public static CountryResultDto germanyResultDto() {

        return new CountryResultDto(
                "DE",
                "DEU",
                "Germany",
                "Germany",
                "Germany"
        );
    }

    public static List<CountryResultDto> resultDtoList() {
        return List.of(
                iranResultDto(),
                germanyResultDto()
        );
    }
}