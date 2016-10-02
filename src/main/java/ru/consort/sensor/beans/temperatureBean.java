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

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriBuilder;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

import static ru.consort.sensor.Services.InfoService.cbmsUrl;

/**
 * Created by DaH4uk on 04.08.2016.
 */
@ManagedBean(name = "temperatureBean")
public class temperatureBean {
    private static final ClientConfig config = new DefaultClientConfig();
    private static final Client client = Client.create(config);
    private static final JsonParser parser = new JsonParser();
    private Integer dayTemp = 20;
    private Integer nightTemp = 20;

    @PostConstruct
    public void init() {

        dayTemp = getTempValue("H43/");
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Значение дневной температуры: " + dayTemp + ".", null));
        nightTemp = getTempValue("H47/");
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Значение ночной температуры: " + nightTemp + ".", null));

    }


    public void setDayTempValue() throws IOException {
        URL url = new URL(cbmsUrl + "obix/app/drivers/modbus/remote/rtu1/slave1/points/AO/H43/in2/");
        HttpURLConnection httpCon = (HttpURLConnection) url.openConnection();


        String encoded = Base64.encode(("admin" + ":" + "").getBytes());
        httpCon.setRequestProperty("Authorization", "Basic " + encoded);

        httpCon.setDoOutput(true);
        httpCon.setRequestMethod("PUT");
        OutputStreamWriter out = new OutputStreamWriter(
                httpCon.getOutputStream());
        out.write("<obj>\n" +
                "<real name=\"in2\" val=\"" + dayTemp + "\"></real>\n" +
                "</obj>");
        out.close();
        System.out.println(httpCon.getInputStream());
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Значение дневной температуры изменено на: " + dayTemp + " градусов.", null));

    }

    public void setNightTempValue() throws IOException {
        URL url = new URL(cbmsUrl + "obix/app/drivers/modbus/remote/rtu1/slave1/points/AO/H43/in2/");
        HttpURLConnection httpCon = (HttpURLConnection) url.openConnection();


        String encoded = Base64.encode(("admin" + ":" + "").getBytes());
        httpCon.setRequestProperty("Authorization", "Basic " + encoded);

        httpCon.setDoOutput(true);
        httpCon.setRequestMethod("PUT");
        OutputStreamWriter out = new OutputStreamWriter(
                httpCon.getOutputStream());
        out.write("<obj>\n" +
                "<real name=\"in2\" val=\"" + nightTemp + "\"></real>\n" +
                "</obj>");
        out.close();
        System.out.println(httpCon.getInputStream());
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Значение ночной температуры изменено на: " + nightTemp + " градусов.", null));

    }

    public void onChange() {

    }

    public Integer getDayTemp() {
        return dayTemp;
    }

    public void setDayTemp(Integer dayTemp) {
        this.dayTemp = dayTemp;
    }

    public Integer getNightTemp() {
        return nightTemp;
    }

    public void setNightTemp(Integer nightTemp) {
        this.nightTemp = nightTemp;
    }

    private int getTempValue(String path) {
        WebResource service = client.resource(UriBuilder.fromUri(cbmsUrl).build());
        client.addFilter(new HTTPBasicAuthFilter("admin", ""));
        String json = service.path("obixj").path("app/drivers/modbus/remote/rtu1/slave1/points/AO/" + path + "in16/").accept(MediaType.APPLICATION_JSON).get(String.class);
        int count = 0;
        while (json == "" && count < 10) {
            json = service.path("obixj").path("app/drivers/modbus/remote/rtu1/slave1/points/AO/" + path + "in16/").accept(MediaType.APPLICATION_JSON).get(String.class);
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            count++;
        }
        JsonElement jsonElement = parser.parse(json);
        JsonObject rootObject = jsonElement.getAsJsonObject(); // чтение главного объекта
        //Reading root element in json
        JsonObject object = rootObject.getAsJsonObject("real");
        JsonElement a = object.get("val");
        return (int) Double.parseDouble(a.toString().replace("\"", ""));
    }
}
