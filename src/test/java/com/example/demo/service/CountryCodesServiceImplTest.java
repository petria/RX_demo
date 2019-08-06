package com.example.demo.service;

import com.example.demo.service.json.Alpha2CodeJson;
import com.example.demo.servicedto.response.CountryCodesResponse;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InOrder;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import rx.observers.AssertableSubscriber;

import java.io.IOException;

import static org.mockito.Mockito.*;

public class CountryCodesServiceImplTest {

    @Mock
    private RestFetcher restFetcher;

    @InjectMocks
    private CountryCodesServiceImpl countryCodesService;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void GetCountryCodes_Success_ReturnSingleCountryCodesResponse() throws IOException {

        Alpha2CodeJson[] results = createMockResult();
        when(restFetcher.fetchCountryCodes())
                .thenReturn(results);

        AssertableSubscriber<CountryCodesResponse> response = countryCodesService.getCountryCodes()
                .test()
                .assertCompleted()
                .assertNoErrors()
                .awaitTerminalEvent();

        response.assertValueCount(1);

        InOrder inOrder = inOrder(restFetcher);
        inOrder.verify(restFetcher, times(1)).fetchCountryCodes();
    }

    private Alpha2CodeJson[] createMockResult() {
        Alpha2CodeJson[] array = new Alpha2CodeJson[2];
        array[0] = new Alpha2CodeJson();
        array[0].setAlpha2Code("CODE1");
        array[1] = new Alpha2CodeJson();
        array[1].setAlpha2Code("CODE2");
        return array;
    }


}
