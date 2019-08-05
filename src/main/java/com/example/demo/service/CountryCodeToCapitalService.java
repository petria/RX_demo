package com.example.demo.service;

import com.example.demo.servicedto.response.CountryCodeToCapitalResponse;
import rx.Single;

public interface CountryCodeToCapitalService {

    Single<CountryCodeToCapitalResponse> getCountryCodes(String code);

}
