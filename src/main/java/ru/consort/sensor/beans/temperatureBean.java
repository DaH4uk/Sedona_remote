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
 * Автор: Туров Данил
 * Дата создания: 04.08.2016.
 * Реализует управление на странице /temperature
 * Проект: "Модуль локальной автоматизации".
 * Консорт.
 */
@ManagedBean(name = "temperatureBean")
public class temperatureBean {
    private static final ClientConfig config = new DefaultClientConfig();
    private static final Client client = Client.create(config);
    private static final JsonParser parser = new JsonParser();
    private Integer dayTemp;
    private Integer nightTemp;
    private Integer hotWaterDay;
    private Integer hotWaterNight;

    /**
     * Ининциализирует значения:
     * Дневной команатной температуры,
     * Ночной комнатной температуры,
     * Температуры горячей воды днем,
     * Температуры горячей воды ночью.
     * @throws IOException
     */
    @PostConstruct
    public void init() {
        if (!(boolean) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("authorized")){
            try {
                FacesContext.getCurrentInstance().getExternalContext().redirect("/NO");
            } catch (IOException e){/*Nope*/}
        }
        dayTemp = getTempValue("H43/");
        nightTemp = getTempValue("H47/");
        hotWaterDay = getTempValue("H35/");
        hotWaterNight = getTempValue("H37/");
    }

    /**
     * Вызывается при нажатии на кнопку "Готово"
     * в форме "Комнатная температура днем".
     * Открывает connection по заданному url,
     * авторизуется с указанными данными,
     * методом PUT отправляет данные
     * и выводит измененное значение.
     * @throws IOException
     */
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
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Значение комнатной температуры днем изменено на: " + dayTemp + " градусов.", null));
    }

    /**
     * Вызывается при нажатии на кнопку "Готово"
     * в форме "Комнатная температура ночью".
     * Открывает connection по заданному url,
     * авторизуется с указанными данными,
     * методом PUT отправляет данные
     * и выводит измененное значение.
     * @throws IOException
     */
    public void setNightTempValue() throws IOException {
        URL url = new URL(cbmsUrl + "obix/app/drivers/modbus/remote/rtu1/slave1/points/AO/H47/in2/");
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
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Значение комнатной температуры ночью изменено на: " + nightTemp + " градусов.", null));

    }

    /**
     * Вызывается при нажатии на кнопку "Готово"
     * в форме "Температура горячей воды днем".
     * Открывает connection по заданному url,
     * авторизуется с указанными данными,
     * методом PUT отправляет данные
     * и выводит измененное значение.
     * @throws IOException
     */
    public void setHotWaterDayTemp() throws IOException {
        URL url = new URL(cbmsUrl + "obix/app/drivers/modbus/remote/rtu1/slave1/points/AO/H35/in2/");
        HttpURLConnection httpCon = (HttpURLConnection) url.openConnection();

        String encoded = Base64.encode(("admin" + ":" + "").getBytes());
        httpCon.setRequestProperty("Authorization", "Basic " + encoded);

        httpCon.setDoOutput(true);
        httpCon.setRequestMethod("PUT");
        OutputStreamWriter out = new OutputStreamWriter(
                httpCon.getOutputStream());
        out.write("<obj>\n" +
                "<real name=\"in2\" val=\"" + hotWaterDay + "\"></real>\n" +
                "</obj>");
        out.close();
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Значение температуры горячей воды днем изменено на: " + hotWaterDay + " градусов.", null));

    }

    /**
     * Вызывается при нажатии на кнопку "Готово"
     * в форме "Температура горячей воды ночью".
     * Открывает connection по заданному url,
     * авторизуется с указанными данными,
     * методом PUT отправляет данные
     * и выводит измененное значение.
     * @throws IOException
     */
    public void setHotWaterNightTemp() throws IOException {
        URL url = new URL(cbmsUrl + "obix/app/drivers/modbus/remote/rtu1/slave1/points/AO/H37/in2/");
        HttpURLConnection httpCon = (HttpURLConnection) url.openConnection();


        String encoded = Base64.encode(("admin" + ":" + "").getBytes());
        httpCon.setRequestProperty("Authorization", "Basic " + encoded);

        httpCon.setDoOutput(true);
        httpCon.setRequestMethod("PUT");
        OutputStreamWriter out = new OutputStreamWriter(
                httpCon.getOutputStream());
        out.write("<obj>\n" +
                "<real name=\"in2\" val=\"" + hotWaterNight + "\"></real>\n" +
                "</obj>");
        out.close();
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Значение температуры горячей воды ночью изменено на: " + hotWaterNight + " градусов.", null));

    }

    /**
     * Скрывает реализацию доступа к данным json.
     * Подключается с авторизацией,
     * получает json (10 попыток),
     * парсит его и возвращает значение поля in16.
     * @param path часть url регистра в виде "H43/".
     * @return значение регистра.
     */
    private int getTempValue(String path) {
        WebResource service = client.resource(UriBuilder.fromUri(cbmsUrl).build());
        client.addFilter(new HTTPBasicAuthFilter("admin", ""));
        String json = "";
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

    public void onChange() { /*NOTHING*/}

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

    public Integer getHotWaterDay() {
        return hotWaterDay;
    }

    public void setHotWaterDay(Integer hotWaterDay) {
        this.hotWaterDay = hotWaterDay;
    }

    public Integer getHotWaterNight() {
        return hotWaterNight;
    }

    public void setHotWaterNight(Integer hotWaterNight) {
        this.hotWaterNight = hotWaterNight;
    }
}
