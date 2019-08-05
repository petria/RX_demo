package com.example.demo.service;

import com.example.demo.servicedto.response.CountryCodesResponse;
import rx.Single;

public interface CountryCodesService {

    Single<CountryCodesResponse> getCountryCodes();

}
