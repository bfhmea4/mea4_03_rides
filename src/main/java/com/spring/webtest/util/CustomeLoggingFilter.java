package com.spring.webtest.util;

import org.springframework.web.filter.CommonsRequestLoggingFilter;

public class CustomeLoggingFilter extends CommonsRequestLoggingFilter {

    public CustomeLoggingFilter() {
        super.setIncludeQueryString(true);
        super.setIncludePayload(true);
        super.setMaxPayloadLength(10000);
    }
}
