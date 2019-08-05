package com.example.demo.service;

import com.example.demo.servicedto.response.CountryCodeToCapitalResponse;
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
public class CountryCodeToCapitalServiceImpl implements CountryCodeToCapitalService {

    @Override
    public Single<CountryCodeToCapitalResponse> getCapitalFromCountryCode(String code) {
        return Single.create(singleSubscriber -> {
            try {
                CountryCodeToCapitalResponse countryCodesResponse = fetchCodes(code);
                singleSubscriber.onSuccess(countryCodesResponse);
            } catch (IOException e) {
                log.error("country capital from code fetch fail: {}", code, e);
                singleSubscriber.onError(new InternalError("Country capital fetch fail"));
            }
        });
    }

    private CountryCodeToCapitalResponse fetchCodes(String code) throws IOException {
        RestTemplate restTemplate = new RestTemplate();
        String uri = "https://restcountries.eu/rest/v2/alpha/" + code + "?fields=name;capital";
        log.debug("Calling: {}", uri);
        ResponseEntity<String> response = restTemplate.exchange(
                uri, HttpMethod.GET, null,
                new ParameterizedTypeReference<String>() {
                });

        String body = response.getBody();
        ObjectMapper mapper = new ObjectMapper();

        return mapper.readValue(body, CountryCodeToCapitalResponse.class);
    }

}
