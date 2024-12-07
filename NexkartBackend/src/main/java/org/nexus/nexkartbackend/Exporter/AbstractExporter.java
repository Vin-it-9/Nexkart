package org.nexus.nexkartbackend.Exporter;


import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


import jakarta.servlet.http.HttpServletResponse;

public class AbstractExporter {

    public void setResponseHeader(HttpServletResponse response , String contentType , String extension ) throws IOException {
        DateFormat dataFormatter = new SimpleDateFormat("yyyy-MM-dd");
        String timestamp = dataFormatter.format(new Date()) ;
        String fileName = "Users_" + timestamp + extension ;

        response.setContentType(contentType);

        String headerkey = "Content-Disposition" ;
        String headerValue = "attachment; fileName=" + fileName;
        response.setHeader(headerkey, headerValue);


    }

}