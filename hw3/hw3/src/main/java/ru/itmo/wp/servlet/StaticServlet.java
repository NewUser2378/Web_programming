package ru.itmo.wp.servlet;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;

public class StaticServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String URIi = request.getRequestURI();
        String[] URIs = URIi.split("\\+"); // Split by +

        for (int i = 0; i < URIs.length; i++) {
            String URI = URIs[i];
            String basePath = getServletContext().getRealPath("/static"); // Get the real path of the /static directory

            File file = new File(basePath, URI);

            if (file.isFile()) {
                if (i == 0) {
                    response.setContentType(getServletContext().getMimeType(file.getName()));
                }

                try (OutputStream outputStream = response.getOutputStream();
                     FileInputStream inputStream = new FileInputStream(file)) {
                    byte[] buffer = new byte[8192]; // Buffer size for data transfer
                    int bytesRead;
                    while ((bytesRead = inputStream.read(buffer)) != -1) {
                        outputStream.write(buffer, 0, bytesRead);
                    }
                }
            } else {
                response.sendError(HttpServletResponse.SC_NOT_FOUND);
            }
        }
    }
}
