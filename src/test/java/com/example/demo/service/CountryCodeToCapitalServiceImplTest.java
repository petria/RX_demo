package com.example.demo.service;

import com.example.demo.servicedto.response.CountryCodeToCapitalResponse;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InOrder;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import rx.observers.AssertableSubscriber;

import java.io.IOException;

import static org.mockito.Mockito.*;

public class CountryCodeToCapitalServiceImplTest {

    @Mock
    private RestFetcher restFetcher;

    @InjectMocks
    private CountryCodeToCapitalServiceImpl service;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
    }


    @Test
    public void GetCountryCapital_Success_ReturnSingleResponse() throws IOException {

        CountryCodeToCapitalResponse results = createMockData();
        when(restFetcher.fetchNameAndCapitalByCountryCode("FI"))
                .thenReturn(results);

        AssertableSubscriber<CountryCodeToCapitalResponse> response = service.getCapitalFromCountryCode("FI")
                .test()
                .assertCompleted()
                .assertNoErrors()
                .awaitTerminalEvent();

        response.assertValueCount(1);

        InOrder inOrder = inOrder(restFetcher);
        inOrder.verify(restFetcher, times(1)).fetchNameAndCapitalByCountryCode("FI");

    }

    private CountryCodeToCapitalResponse createMockData() {
        CountryCodeToCapitalResponse response = new CountryCodeToCapitalResponse();
        response.setName("Finland");
        response.setCapital("Helsinki");
        return response;
    }


}
