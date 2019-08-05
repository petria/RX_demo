package com.example.demo.web;

import com.example.demo.service.CountryCodesService;
import com.example.demo.servicedto.response.CountryCodesResponse;
import com.example.demo.webdto.response.CountryCodesWebResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import rx.Single;
import rx.schedulers.Schedulers;

@RestController
@RequestMapping(value = "/country_codes")
@Slf4j
public class CountryCodesRestController {

    private final CountryCodesService countryCodesService;

    public CountryCodesRestController(CountryCodesService countryCodesService) {
        this.countryCodesService = countryCodesService;
    }


    @GetMapping(
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public Single<ResponseEntity<CountryCodesWebResponse>> getCountryCodesl() {
        return countryCodesService.getCountryCodes()
                .subscribeOn(Schedulers.io())
                .map(countryCodesWebResponse -> ResponseEntity.ok(toCountryCodesWebResponse(countryCodesWebResponse)));
    }

    private CountryCodesWebResponse toCountryCodesWebResponse(CountryCodesResponse countryCodesResponse) {
        log.debug("convert");
        CountryCodesWebResponse webResponse = new CountryCodesWebResponse();
        webResponse.getCountry_codes().addAll(countryCodesResponse.getCountry_codes());
        return webResponse;
    }


}
