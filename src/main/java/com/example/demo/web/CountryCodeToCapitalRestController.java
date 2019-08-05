package com.example.demo.web;

import com.example.demo.service.CountryCodeToCapitalService;
import com.example.demo.servicedto.response.CountryCodeToCapitalResponse;
import com.example.demo.webdto.response.CountryCodeToCapitalWebResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import rx.Single;
import rx.schedulers.Schedulers;

@RestController
@RequestMapping(value = "/capital")
@Slf4j
public class CountryCodeToCapitalRestController {

    private final CountryCodeToCapitalService codeToCapitalService;

    public CountryCodeToCapitalRestController(CountryCodeToCapitalService codeToCapitalService) {
        this.codeToCapitalService = codeToCapitalService;
    }

    @GetMapping(
            value = "/{code}",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public Single<ResponseEntity<CountryCodeToCapitalWebResponse>> getCapital(@PathVariable(value = "code") String code) {
        return codeToCapitalService.getCapitalFromCountryCode(code)
                .subscribeOn(Schedulers.io())
                .map(countryCodesWebResponse -> ResponseEntity.ok(toCountryCodeToCapitalWebResponse(countryCodesWebResponse)));
    }

    private CountryCodeToCapitalWebResponse toCountryCodeToCapitalWebResponse(CountryCodeToCapitalResponse countryCodeToCapitalResponse) {
        CountryCodeToCapitalWebResponse response = new CountryCodeToCapitalWebResponse();
        BeanUtils.copyProperties(countryCodeToCapitalResponse, response);
        return response;
    }

}
