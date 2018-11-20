package de.pfann.budgetmanager.server.restservices.resources.filter;

import de.pfann.budgetmanager.server.restservices.resources.core.Logged;
import org.glassfish.jersey.message.internal.ReaderWriter;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.ext.Provider;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;


@ValidateContactRequest
@Provider
public class ContactValidatRequestFilter implements ContainerRequestFilter {

    @Override
    public void filter(ContainerRequestContext aContainerRequestContext) throws IOException {
        System.out.println(getEntityBody(aContainerRequestContext));
    }

    private String getEntityBody(ContainerRequestContext requestContext)
    {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        InputStream in = requestContext.getEntityStream();

        final StringBuilder b = new StringBuilder();
        try
        {
            ReaderWriter.writeTo(in, out);

            byte[] requestEntity = out.toByteArray();
            if (requestEntity.length == 0)
            {
                b.append("").append("\n");
            }
            else
            {
                b.append(new String(requestEntity)).append("\n");
            }
            requestContext.setEntityStream( new ByteArrayInputStream(requestEntity) );

        } catch (IOException ex) {
            //Handle logging error
        }
        return b.toString();
    }



}
