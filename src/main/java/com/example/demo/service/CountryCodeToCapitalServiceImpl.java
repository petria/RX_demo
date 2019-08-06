package com.example.demo.service;

import com.example.demo.servicedto.response.CountryCodeToCapitalResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rx.Single;

import java.io.IOException;

@Service
@Slf4j
public class CountryCodeToCapitalServiceImpl implements CountryCodeToCapitalService {

    @Autowired
    private RestFetcher restFetcher;

    @Override
    public Single<CountryCodeToCapitalResponse> getCapitalFromCountryCode(String countryCode) {
        return Single.create(singleSubscriber -> {
            try {
                CountryCodeToCapitalResponse countryCodesResponse = restFetcher.fetchNameAndCapitalByCountryCode(countryCode);
                singleSubscriber.onSuccess(countryCodesResponse);
            } catch (IOException e) {
                log.error("country capital from country code fetch fail: {}", countryCode, e);
                singleSubscriber.onError(new InternalError("Country capital fetch fail"));
            }
        });
    }

}
