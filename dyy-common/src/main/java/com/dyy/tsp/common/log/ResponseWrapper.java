package com.dyy.tsp.common.log;

import org.springframework.http.HttpHeaders;
import org.springframework.web.util.ContentCachingResponseWrapper;

import javax.servlet.http.HttpServletResponse;

public class ResponseWrapper extends ContentCachingResponseWrapper {
    private HttpHeaders headers;

    public ResponseWrapper(HttpServletResponse response) {
        super(response);
    }

    public HttpHeaders getHeaders() {
        if (this.headers == null) {
            this.headers = new HttpHeaders();
            for (String headerName : getHeaderNames()) {
                for (String headerValue : getHeaders(headerName)) {
                    this.headers.add(headerName, headerValue);
                }
            }
        }
        return headers;
    }
}
