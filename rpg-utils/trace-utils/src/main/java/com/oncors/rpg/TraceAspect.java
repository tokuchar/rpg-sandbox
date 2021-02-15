package com.oncors.rpg;

import io.opentracing.Scope;
import io.opentracing.SpanContext;
import io.opentracing.Tracer;
import io.opentracing.propagation.Format;
import io.opentracing.propagation.TextMapAdapter;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;

@Slf4j
@Aspect
@Component
public class TraceAspect {
    public static final String UBER_TRACE_ID = "uber-trace-id";
    private Tracer tracer;
    private HttpServletRequest request;

    public TraceAspect(@Autowired Tracer tracer, @Autowired HttpServletRequest request) {
        this.tracer = tracer;
        this.request = request;
    }

    @Around("@annotation(com.oncors.rpg.Trace)")
    public Object trace(ProceedingJoinPoint joinPoint) throws Throwable {
        log.info("trace log..");
        return handleTracing(joinPoint);
    }

    private Object handleTracing(ProceedingJoinPoint joinPoint) throws Throwable {
        final String operationName = joinPoint.getSignature().getName();
        SpanContext parentContext = null;
        Object proceed;

        String uberTraceId = request.getHeader(UBER_TRACE_ID);

        if (uberTraceId != null) {
            parentContext = tracer.extract(
                    Format.Builtin.HTTP_HEADERS,
                    new TextMapAdapter(new HashMap<>() {{
                        put(UBER_TRACE_ID, uberTraceId);
                    }})
            );
        }

        try (Scope scope = tracer.buildSpan(operationName)
                .asChildOf(parentContext)
                .startActive(true)) {
            proceed = joinPoint.proceed();

            tracer.activeSpan().setTag("http.status", ((ResponseEntity) proceed).getStatusCode().value());
        }

        return proceed;
    }

    @AfterThrowing(pointcut = "execution(* com.oncors.rpg..* (..))", throwing = "exception")
    public void errorInterceptor(Exception exception) {
        tracer.activeSpan().log(exception.getMessage());
    }
}
