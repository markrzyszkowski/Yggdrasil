package com.github.asgardbot.core;

import com.github.asgardbot.commons.InvalidRequestException;
import com.github.asgardbot.commons.Request;
import com.github.asgardbot.commons.Response;
import com.github.asgardbot.commons.ServiceId;
import com.github.asgardbot.dataproviders.DataProvider;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest
@DirtiesContext
public class ServiceRegistrarTest {

    @Autowired
    private Map<ServiceId, DataProvider> dataProviderMap;

    @Test
    public void serviceRegistrar_registersTwoComponents() {
        Assert.assertTrue("Missing components",
                          dataProviderMap.containsKey(new ServiceId("provider1")));
        Assert.assertTrue("Missing components",
                          dataProviderMap.containsKey(new ServiceId("provider2")));
    }

    @TestConfiguration
    static class TestBeanProvider {

        @Bean
        DataProvider provider1() {
            return new DataProvider() {

                @Override
                public Response process(Request request) throws InvalidRequestException {
                    return null;
                }

                @Override
                public ServiceId getServiceId() {
                    return new ServiceId("provider1");
                }
            };
        }

        @Bean
        DataProvider provider2() {
            return new DataProvider() {

                @Override
                public Response process(Request request) throws InvalidRequestException {
                    return null;
                }

                @Override
                public ServiceId getServiceId() {
                    return new ServiceId("provider2");
                }
            };
        }
    }
}
