package com.example.demo.service;

import com.example.demo.servicedto.response.CountryCodeToCapitalResponse;
import rx.Single;

public interface CountryCodeToCapitalService {

    /**
     * @param code Country code to fetch country name & capital name
     * @return name and capital fetched from https://restcountries.eu/ service
     */
    Single<CountryCodeToCapitalResponse> getCapitalFromCountryCode(String code);

}
