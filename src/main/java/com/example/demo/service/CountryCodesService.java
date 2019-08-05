package com.example.demo.service;

import com.example.demo.servicedto.response.CountryCodesResponse;
import rx.Single;

public interface CountryCodesService {

    /**
     * @return country codes fetched from https://restcountries.eu/ service
     */
    Single<CountryCodesResponse> getCountryCodes();

}
