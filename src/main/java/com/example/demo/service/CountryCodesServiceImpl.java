package com.example.demo.service;

import com.example.demo.service.json.Alpha2CodeJson;
import com.example.demo.servicedto.response.CountryCodesResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import rx.Single;

import java.io.IOException;

@Service
@Slf4j
public class CountryCodesServiceImpl implements CountryCodesService {


    @Override
    public Single<CountryCodesResponse> getCountryCodes() {
        log.debug("get country codes");
        return Single.create(singleSubscriber -> {
            try {
                CountryCodesResponse countryCodesResponse = new CountryCodesResponse();
                Alpha2CodeJson[] json = fetchCodes();
                for (Alpha2CodeJson code : json) {
                    countryCodesResponse.getCountry_codes().add(code.getAlpha2Code());
                }
                singleSubscriber.onSuccess(countryCodesResponse);
            } catch (IOException e) {
                log.error("country code fetch fail", e);
                singleSubscriber.onError(new InternalError());
            }

        });
    }

    private Alpha2CodeJson[] fetchCodes() throws IOException {
        RestTemplate restTemplate = new RestTemplate();
        String uri = "https://restcountries.eu/rest/v2/all?fields=alpha2Code";

        ResponseEntity<String> response = restTemplate.exchange(
                uri, HttpMethod.GET, null,
                new ParameterizedTypeReference<String>() {
                });

        String body = response.getBody();
        ObjectMapper mapper = new ObjectMapper();

        return mapper.readValue(body, Alpha2CodeJson[].class);
    }
}
