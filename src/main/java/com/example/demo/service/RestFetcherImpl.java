package com.example.demo.service;

import com.example.demo.service.json.Alpha2CodeJson;
import com.example.demo.servicedto.response.CountryCodeToCapitalResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;

@Service
@Slf4j
public class RestFetcherImpl implements RestFetcher {

    @Override
    public Alpha2CodeJson[] fetchCountryCodes() throws IOException {
        RestTemplate restTemplate = new RestTemplate();
        String uri = "https://restcountries.eu/rest/v2/all?fields=alpha2Code";

        log.debug("Calling: {}", uri);

        ResponseEntity<String> response = restTemplate.exchange(
                uri, HttpMethod.GET, null,
                new ParameterizedTypeReference<String>() {
                });

        String body = response.getBody();
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(body, Alpha2CodeJson[].class);
    }

    @Override
    public CountryCodeToCapitalResponse fetchNameAndCapitalByCountryCode(String countryCode) throws IOException {
        RestTemplate restTemplate = new RestTemplate();
        String uri = String.format("https://restcountries.eu/rest/v2/alpha/%s?fields=name;capital", countryCode);

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
