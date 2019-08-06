package com.example.demo.service;

import com.example.demo.service.json.Alpha2CodeJson;
import com.example.demo.servicedto.response.CountryCodesResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rx.Single;

import java.io.IOException;

@Service
@Slf4j
public class CountryCodesServiceImpl implements CountryCodesService {

    @Autowired
    private RestFetcher restFetcher;

    @Override
    public Single<CountryCodesResponse> getCountryCodes() {
        log.debug("get country codes");
        return Single.create(singleSubscriber -> {
            try {
                CountryCodesResponse countryCodesResponse = new CountryCodesResponse();
                Alpha2CodeJson[] countryCodes = restFetcher.fetchCountryCodes();
                for (Alpha2CodeJson countryCode : countryCodes) {
                    countryCodesResponse.getCountry_codes().add(countryCode.getAlpha2Code());
                }
                singleSubscriber.onSuccess(countryCodesResponse);
            } catch (IOException e) {
                log.error("country code fetch fail", e);
                singleSubscriber.onError(new InternalError("country list fetch fail"));
            }

        });
    }

}
