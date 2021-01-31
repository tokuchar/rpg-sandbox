package com.oncors.rpg;

import io.opentracing.Scope;
import io.opentracing.SpanContext;
import io.opentracing.Tracer;
import io.opentracing.propagation.Format;
import io.opentracing.propagation.TextMapExtractAdapter;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;

import java.util.Iterator;
import java.util.Map;

@Slf4j
@Aspect
@Component
public class TraceAspect {
    private Tracer tracer;

    public TraceAspect(@Autowired Tracer tracer) {
        this.tracer = tracer;
    }

    @Around("@annotation(com.oncors.rpg.Trace)")
    public Object trace(ProceedingJoinPoint joinPoint) throws Throwable {
        log.info("trace log..");
        return handleTracing(joinPoint);
    }

    private Object handleTracing(ProceedingJoinPoint joinPoint) throws Throwable {
        final String operationName = joinPoint.getSignature().getName();
        SpanContext parentContext = null;
        HttpHeaders httpHeaders = null;
        Object proceed;

        for (Object object : joinPoint.getArgs()) {
            if (object instanceof HttpHeaders) {
                httpHeaders = (HttpHeaders) object;
                parentContext = tracer.extract(Format.Builtin.HTTP_HEADERS, new TextMapExtractAdapter(httpHeaders.toSingleValueMap()));
            }
        }

        try (Scope scope = tracer.buildSpan(operationName)
                .asChildOf(parentContext)
                .startActive(true)) {

            tracer.inject(
                    tracer.activeSpan().context(),
                    Format.Builtin.HTTP_HEADERS,
                    new RequestBuilderCarrier(httpHeaders));

            proceed = joinPoint.proceed();
        }
        return proceed;
    }

    public class RequestBuilderCarrier implements io.opentracing.propagation.TextMap {
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
