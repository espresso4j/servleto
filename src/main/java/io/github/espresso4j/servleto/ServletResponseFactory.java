package io.github.espresso4j.servleto;

import io.github.espresso4j.espresso.Response;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ServletResponseFactory {

    public static void intoServletResponse(Response response, HttpServletResponse servletResponse)
            throws IOException {

        // status
        servletResponse.setStatus(response.getStatus());

        // header
        response.getHeaders().forEach(servletResponse::addHeader);

        if (response.getBody() != null) {
            response.getBody().into(servletResponse.getOutputStream());
        }

    }

}
