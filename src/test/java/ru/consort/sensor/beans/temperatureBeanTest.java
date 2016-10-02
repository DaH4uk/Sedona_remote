package ru.consort.sensor.beans;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import com.sun.jersey.api.client.filter.HTTPBasicAuthFilter;
import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;
import org.junit.Before;
import org.junit.Test;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriBuilder;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;


/**
 * Created by turov on 22.09.2016.
 */
public class temperatureBeanTest {
    private static final ClientConfig config = new DefaultClientConfig();
    private static final Client client = Client.create(config);
    //Sedona web url
    private static final WebResource service = client.resource(UriBuilder.fromUri("http://85.26.195.142/").build());
    private static final JsonParser parser = new JsonParser();
    JsonElement a = null;


    @Before
    public void setUp() throws Exception {

    }

    @Test
    public void init() throws Exception {
    //  System.out.println((int) Double.parseDouble(a.toString().replace("\"", "")));
    }

    @Test
    public void clicked() throws IOException {
       // System.out.println(httpCon.getInputStream());
    }

}