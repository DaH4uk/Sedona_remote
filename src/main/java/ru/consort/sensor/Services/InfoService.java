package ru.consort.sensor.Services;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import com.sun.jersey.api.client.filter.HTTPBasicAuthFilter;
import ru.consort.sensor.entities.Parameter;

import javax.annotation.Resource;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriBuilder;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by DaH4uk on 04.08.2016.
 */
@ManagedBean(name = "infoService")
@ApplicationScoped
public class InfoService {

    private final static String[] parameterNames;
    private static final ClientConfig config = new DefaultClientConfig();
    private static final Client client = Client.create(config);

    public static String cbmsUrl = "http://85.26.195.142:8080/";


    private static final List<String> urls = new ArrayList<>();

    static {
        parameterNames = new String[13];
        parameterNames[0] = "Sensor 1 - Tемпература наружного воздуха";
        parameterNames[1] = "Sensor 2 - Комнатная температура";
        parameterNames[2] = "Sensor 3 - подача отопление";
        parameterNames[3] = "Sensor 4 - обратка отопление";
        parameterNames[4] = "Sensor 5 - подача ГВС";
        parameterNames[5] = "Sensor 6 - обратка ГВС";
        parameterNames[6] = "Расчетная температура подачи - контур 1";
        parameterNames[7] = "Расчетная температура подачи - контур 2";
        parameterNames[8] = "Расчетная температура обратки - контур 1";
        parameterNames[9] = "Расчетная температура обратки - контур 2";
        parameterNames[10] = "Комнатная температура - контур 1";
        parameterNames[11] = "Комнатная температура - контур 2";
        parameterNames[12] = "Tемпература наружного воздуха";


        urls.add(0,"H1/");
        urls.add(1,"H3/");
        urls.add(2,"H5/");
        urls.add(3,"H7/");
        urls.add(4,"H9/");
        urls.add(5,"H11/");
        urls.add(6,"H13/");
        urls.add(7,"H15/");
        urls.add(8,"H17/");
        urls.add(9,"H19/");
        urls.add(10,"H21/");
        urls.add(11,"H23/");
        urls.add(12,"H25/");
    }

    public List<Parameter> createParameters() {
        WebResource service = client.resource(UriBuilder.fromUri(cbmsUrl).build());
        List<Parameter> list = new ArrayList<Parameter>();
        JsonParser parser = new JsonParser();
        client.addFilter(new HTTPBasicAuthFilter("admin", ""));
        int i = 0;
        for (String url : urls) {
            String json = service.path("obixj").path("app/drivers/modbus/remote/rtu1/slave1/points/AO/" +url+ "in16/").accept(MediaType.APPLICATION_JSON).get(String.class);

            int count = 0;
            while (json == "" && count < 10){
                json = service.path("obixj").path("app/drivers/modbus/remote/rtu1/slave1/points/AO/" +url+ "in16/").accept(MediaType.APPLICATION_JSON).get(String.class);
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
            Double b = Double.parseDouble(a.toString().replace("\"",""));

            list.add(new Parameter(parameterNames[i], b));
            i++;
        }

        return list;
    }

}
