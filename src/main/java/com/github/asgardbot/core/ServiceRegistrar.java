package com.github.asgardbot.core;

import com.github.asgardbot.commons.ServiceId;
import com.github.asgardbot.dataproviders.DataProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Configuration
class ServiceRegistrar {

    private List<DataProvider> dataProviders;
    private Logger LOGGER = LoggerFactory.getLogger(ServiceRegistrar.class);

    public ServiceRegistrar(List<DataProvider> dataProviders) {
        this.dataProviders = dataProviders;
    }

    @Bean
    public Map<ServiceId, DataProvider> dataProviderMap() {
        Map<ServiceId, DataProvider> dataProviderMap = new HashMap<>();
        dataProviders.forEach(e -> dataProviderMap.put(e.getServiceId(), e));
        LOGGER.debug("{}", dataProviderMap);
        return dataProviderMap;
    }
}
