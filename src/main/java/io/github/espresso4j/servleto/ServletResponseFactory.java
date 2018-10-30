package io.github.espresso4j.servleto;

import io.github.espresso4j.espresso.Response;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ServletResponseFactory {

    public static void intoServletResponse(Response response, HttpServletResponse servletResponse)
            throws IOException {

        // status
        servletResponse.setStatus(response.status());

        // header
        response.headers().forEach(servletResponse::addHeader);

        if (response.body() != null) {
            response.body().into(servletResponse.getOutputStream());
        }

    }

}
