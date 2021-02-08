package com.oncors.rpg.config;

import io.jaegertracing.internal.samplers.ConstSampler;
import io.opentracing.propagation.Format;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.web.client.RestTemplate;

import java.util.Iterator;
import java.util.Map;

@Slf4j
@EnableAutoConfiguration
@Configuration
public class TraceConfig {
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
    public RestTemplate restTemplate(RestTemplateBuilder restTemplateBuilder, io.opentracing.Tracer tracer) {
        RestTemplate restTemplate = restTemplateBuilder
                .build();
        restTemplate.getInterceptors().add((request, body, execution) -> {
            tracer.inject(
                    tracer.activeSpan().context(),
                    Format.Builtin.HTTP_HEADERS,
                    new RequestBuilderCarrier(request.getHeaders()));

            log.info("HTTP {} request to {}", request.getMethod(), request.getURI());
            return execution.execute(request, body);
        });

        return restTemplate;
    }

    public static class RequestBuilderCarrier implements io.opentracing.propagation.TextMap {
        private final HttpHeaders httpHeaders;

        RequestBuilderCarrier(HttpHeaders httpHeaders) {
            this.httpHeaders = httpHeaders;
        }

        @Override
        public Iterator<Map.Entry<String, String>> iterator() {
            throw new UnsupportedOperationException("carrier is write-only");
        }

        @Override
        public void put(String key, String value) {
            httpHeaders.add(key, value);
        }
    }
}
