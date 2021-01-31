package com.oncors.rpg.config;

import io.jaegertracing.internal.samplers.ConstSampler;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@EnableAutoConfiguration
@Configuration
public class    TraceConfig {
    @Bean
    public io.opentracing.Tracer tracer(@Value("${spring.application.name}") String microserviceName) {
        io.jaegertracing.Configuration.SamplerConfiguration samplerConfig = new io.jaegertracing.Configuration.SamplerConfiguration()
                .withType(ConstSampler.TYPE).withParam(1);
        io.jaegertracing.Configuration.ReporterConfiguration reporterConfig = io.jaegertracing.Configuration.ReporterConfiguration
                .fromEnv().withLogSpans(true);

        io.jaegertracing.Configuration config = new io.jaegertracing.Configuration(microserviceName)
                .withSampler(samplerConfig)
                .withReporter(reporterConfig);
        return config.getTracer();
    }

    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder restTemplateBuilder) {
        return restTemplateBuilder
                .build();
    }
}
