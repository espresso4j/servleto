package io.github.espresso4j.servleto;

import io.github.espresso4j.espresso.Request;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.security.cert.X509Certificate;
import java.util.*;

public class ServletRequestFactory {

    public static Request fromServletRequest(HttpServletRequest servletRequest) throws IOException {
        Request request = new Request();

        // scheme
        request.setScheme(Request.Scheme.from(servletRequest.getScheme()));

        // protocol
        request.setProtcol(servletRequest.getProtocol());

        // method
        request.setRequestMethod(Request.Method.from(servletRequest.getMethod()));

        // request uri
        request.setUri(servletRequest.getRequestURI());

        // queryString
        request.setQueryString(servletRequest.getQueryString());

        // server port and name
        request.setServerName(servletRequest.getLocalName());
        request.setServerPort(servletRequest.getLocalPort());

        // remote address
        request.setRemoteAddr(servletRequest.getRemoteAddr());

        // client cert
        request.setSslClientCert(clientCert(servletRequest));

        // header
        request.setHeaders(headers(servletRequest));

        // body
        request.setBody(servletRequest.getInputStream());

        return request;
    }

    private static Map<String, String> headers(HttpServletRequest servletRequest) {
        Map<String, String> headers = new HashMap<>();

        Enumeration<String> headerNames = servletRequest.getHeaderNames();

        while(headerNames.hasMoreElements()) {
            String headerKey = headerNames.nextElement();
            Enumeration<String> values = servletRequest.getHeaders(headerKey);
            if (values != null) {
                headers.put(headerKey, join(values));
            }
        }

        return headers;
    }

    private static String join(Enumeration<String> segs) {
        if (segs == null) {
            return null;
        }

        StringJoiner sj = new StringJoiner(",");
        while (segs.hasMoreElements()) {
            sj.add(segs.nextElement());
        }

        return sj.toString();
    }

    private static X509Certificate clientCert(HttpServletRequest servletRequest) {
        X509Certificate[] certificates = (X509Certificate[]) servletRequest.getAttribute(
                "javax.servlet.request.X509Certificate");

        if (certificates == null || certificates.length == 0) {
            return null;
        }

        return certificates[0];
    }

}
