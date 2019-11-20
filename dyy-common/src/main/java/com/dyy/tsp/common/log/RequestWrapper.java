package com.dyy.tsp.common.log;

import org.springframework.http.HttpHeaders;
import org.springframework.util.StringUtils;
import org.springframework.web.util.ContentCachingRequestWrapper;
import javax.servlet.http.HttpServletRequest;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Enumeration;

public class RequestWrapper extends ContentCachingRequestWrapper {
    private HttpHeaders headers;

    public RequestWrapper(HttpServletRequest request) {
        super(request);
    }

    public HttpHeaders getHeaders() {
        if (this.headers == null) {
            this.headers = new HttpHeaders();
            for (Enumeration<?> headerNames = getHeaderNames(); headerNames.hasMoreElements(); ) {
                String headerName = (String) headerNames.nextElement();
                for (Enumeration<?> headerValues = getHeaders(headerName);
                     headerValues.hasMoreElements(); ) {
                    String headerValue = (String) headerValues.nextElement();
                    this.headers.add(headerName, headerValue);
                }
            }
        }
        return headers;
    }

    public URI getURI() {
        try {
            StringBuffer url = getRequestURL();
            String query = getQueryString();
            if (StringUtils.hasText(query)) {
                url.append('?').append(query);
            }
            return new URI(url.toString());
        } catch (URISyntaxException ex) {
            throw new IllegalStateException("Could not get HttpServletRequest URI: " + ex.getMessage(), ex);
        }
    }
}
