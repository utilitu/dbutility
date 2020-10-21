package com.optum.wpi.integrity;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * Created by apriyad1 on 6/27/19.
 */
@Configuration
//@EnableScheduling
//@EnableConfigurationProperties({ IDTDBHelper.class, IDTSecondaryDBHelper.class, IDTQueryLoaderConfig.class, BiometricsQueryLoaderConfig.class, ConditionQueryLoaderConfig.class })
//@ComponentScan({"com.optum.wpi.batch","com.optum.wpi.batch.test"})
public class AppConfig {
    private final static Logger _LOGGER = LoggerFactory.getLogger(AppConfig.class);

    @PostConstruct
    public void displayConfig() {
        _LOGGER.debug("postconstruct");
    }
}