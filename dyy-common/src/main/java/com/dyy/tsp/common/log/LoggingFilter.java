package com.dyy.tsp.common.log;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.util.*;
import java.util.concurrent.atomic.AtomicLong;

public class LoggingFilter extends OncePerRequestFilter {

    static final Logger logger = LoggerFactory.getLogger(LoggingFilter.class);
    static final String REQUEST_PREFIX = "> ";
    static final String RESPONSE_PREFIX = "< ";
    static final String NOTIFICATION_PREFIX = "* ";
    private int DEFAULT_MAX_PAYLOAD_LENGTH = 8 * 1024;
    final AtomicLong _id = new AtomicLong(0);
    private static final Comparator<Map.Entry<String, List<String>>> COMPARATOR = (o1, o2) -> o1.getKey().compareToIgnoreCase(o2.getKey());
    private static final Set<MediaType> READABLE_APP_MEDIA_TYPES = new HashSet<MediaType>() {{
        add(new MediaType("text", "*"));
        add(new MediaType("application", "svg+xml"));
        add(MediaType.APPLICATION_ATOM_XML);
        add(MediaType.APPLICATION_FORM_URLENCODED);
        add(MediaType.APPLICATION_JSON);
        add(MediaType.APPLICATION_XML);
        add(MediaType.APPLICATION_XHTML_XML);
    }};

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, final FilterChain filterChain) throws ServletException, IOException {
        if (!logger.isDebugEnabled()) {
            filterChain.doFilter(request, response);
            return;
        }
        final long id = _id.incrementAndGet();
        RequestWrapper requestToUse = new RequestWrapper(request);
        ResponseWrapper responseToUse = new ResponseWrapper(response);

        try {
            filterChain.doFilter(requestToUse, responseToUse);
        } finally {
            logRequest(id, requestToUse);
            logResponse(id, responseToUse);
            responseToUse.copyBodyToResponse();
        }
    }


    private void logRequest(long id, RequestWrapper request) {
        final StringBuilder b = new StringBuilder();
        printRequestLine(b, "Sending client request", id, request.getMethod(), request.getURI());
        printPrefixedHeaders(b, id, REQUEST_PREFIX, new ServletServerHttpRequest(request).getHeaders());
        if (isReadable(request.getContentType())) {
            printEntity(b, request.getContentAsByteArray(), request.getCharacterEncoding());
        }
        log(b);
    }

    private void logResponse(long id, ResponseWrapper response) {
        final StringBuilder b = new StringBuilder();
        printResponseLine(b, "Server responded with a response", id, response.getStatus());
        printPrefixedHeaders(b, id, RESPONSE_PREFIX, response.getHeaders());
        if (isReadable(response.getContentType())) {
            printEntity(b, response.getContentAsByteArray(), response.getCharacterEncoding());
        }
        log(b);
    }


    void log(final StringBuilder b) {
        if (logger.isDebugEnabled()) {
            logger.info(b.toString());
        }
    }

    private void printEntity(StringBuilder b, byte[] buf, String charset) {
        if (buf.length > 0) {
            int length = Math.min(buf.length, DEFAULT_MAX_PAYLOAD_LENGTH);
            String payload;
            try {
                payload = new String(buf, 0, length, charset);
            } catch (UnsupportedEncodingException ex) {
                payload = "[unknown]";
            }
            b.append(payload);
            if (buf.length > DEFAULT_MAX_PAYLOAD_LENGTH) {
                b.append("...more...");
            }
            b.append('\n');
        }
    }

    private StringBuilder prefixId(final StringBuilder b, final long id) {
        b.append(Long.toString(id)).append(" ");
        return b;
    }

    void printRequestLine(final StringBuilder b, final String note, final long id, final String method, final URI uri) {
        prefixId(b, id).append(NOTIFICATION_PREFIX)
                .append(note)
                .append(" on thread ").append(Thread.currentThread().getName())
                .append("\n");
        prefixId(b, id).append(REQUEST_PREFIX).append(method).append(" ")
                .append(uri.toASCIIString()).append("\n");
    }

    void printResponseLine(final StringBuilder b, final String note, final long id, final int status) {
        prefixId(b, id).append(NOTIFICATION_PREFIX)
                .append(note)
                .append(" on thread ").append(Thread.currentThread().getName()).append("\n");
        prefixId(b, id).append(RESPONSE_PREFIX)
                .append(Integer.toString(status))
                .append("\n");
    }

    void printPrefixedHeaders(final StringBuilder b,
                              final long id,
                              final String prefix,
                              final HttpHeaders headers) {
        for (final Map.Entry<String, List<String>> headerEntry : getSortedHeaders(headers.entrySet())) {
            final List<?> val = headerEntry.getValue();
            final String header = headerEntry.getKey();

            if (val.size() == 1) {
                prefixId(b, id).append(prefix).append(header).append(": ").append(val.get(0)).append("\n");
            } else {
                final StringBuilder sb = new StringBuilder();
                boolean add = false;
                for (final Object s : val) {
                    if (add) {
                        sb.append(',');
                    }
                    add = true;
                    sb.append(s);
                }
                prefixId(b, id).append(prefix).append(header).append(": ").append(sb.toString()).append("\n");
            }
        }
    }

    Set<Map.Entry<String, List<String>>> getSortedHeaders(final Set<Map.Entry<String, List<String>>> headers) {
        final TreeSet<Map.Entry<String, List<String>>> sortedHeaders = new TreeSet<Map.Entry<String, List<String>>>(COMPARATOR);
        sortedHeaders.addAll(headers);
        return sortedHeaders;
    }

    boolean isReadable(String contentType) {
        if (StringUtils.isBlank(contentType)) {
            return false;
        }
        MediaType mediaType = MediaType.parseMediaType(contentType);
        if (mediaType == null) {
            return false;
        }
        for (MediaType readableMediaType : READABLE_APP_MEDIA_TYPES) {
            if (readableMediaType.isCompatibleWith(mediaType)) {
                return true;
            }
        }
        return false;
    }
}
