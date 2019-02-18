package com.example;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.POST;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.jboss.resteasy.plugins.providers.multipart.MultipartFormDataInput;

/**
 * REST Web Service
 *
 * see https://howtodoinjava.com/jersey/jersey-file-upload-example/
 */
@Path("generic")
public class GenericResource {

  @GET
  @Path("/ping")
  public Response ping() {
    return Response.ok("ping detected!").build();
  }

  // https://stackoverflow.com/questions/48186234/resteasy003200-could-not-find-message-body-reader-for-type-class-org-glassfish
  @POST
  @Path("/pdf")
  @Consumes({MediaType.MULTIPART_FORM_DATA})
  public Response upload(MultipartFormDataInput input) {
    String UPLOAD_PATH = "c:/tmp/";
    try {
      // List<InputPart> filepart = input.getFormDataMap().get("file");
      InputStream fileInputStream = input.getFormDataPart("file", InputStream.class, null);
      String fileName = "test.pdf";

      int read;
      byte[] bytes = new byte[1024];

      try (OutputStream out = new FileOutputStream(new File(UPLOAD_PATH + fileName))) {
        while ((read = fileInputStream.read(bytes)) != -1) {
          out.write(bytes, 0, read);
        }
      }
    } catch (IOException e) {
      throw new WebApplicationException("Error while uploading file. Please try again !!");
    }
    return Response.ok("Data uploaded successfully !!").build();
  }

}
