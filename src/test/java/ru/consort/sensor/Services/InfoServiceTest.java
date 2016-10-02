package ru.consort.sensor.Services;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import com.sun.jersey.api.client.filter.HTTPBasicAuthFilter;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriBuilder;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by turov on 05.09.2016.
 */
public class InfoServiceTest {
    private InfoService infoService;


    @Before
    public void initTest() {
        infoService = new InfoService();
    }

    @After
    public void afterTest() {
        infoService = null;
    }

    @Test
    public void createParameters() throws Exception {
        //infoService.createParameters();
    }

    @Test
    public void test_JSON() {
     //   ClientConfig config = new DefaultClientConfig();
       //Client client = Client.create(config);
      //  WbResource service = client.resource(UriBuilder.fromUri("http://192.168.0.151/").build());
        //ist<String> urls = new ArrayList<>();
//        client.addFilter(new HTTPBasicAuthFilter("admin", ""));
  //     String s = "H1/";
    //   String json = service.path("obixj").path("app/drivers/modbus/remote/tcp502/slave1/points/H1/").accept(MediaType.APPLICATION_JSON).get(String.class);
      // System.err.println(json);
    }

}

