package com.example.demo.webdto.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class CountryCodesWebResponse {
    private List<String> country_codes = new ArrayList<>();
}
