package com.example.demo.service;

import com.example.demo.service.json.Alpha2CodeJson;
import com.example.demo.servicedto.response.CountryCodeToCapitalResponse;

import java.io.IOException;

public interface RestFetcher {

    Alpha2CodeJson[] fetchCountryCodes() throws IOException;

    CountryCodeToCapitalResponse fetchNameAndCapitalByCountryCode(String countryCode) throws IOException;

}
