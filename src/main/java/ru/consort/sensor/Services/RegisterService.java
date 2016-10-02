package ru.consort.sensor.Services;

import com.google.gson.*;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import com.sun.jersey.api.client.filter.HTTPBasicAuthFilter;
import ru.consort.sensor.entities.Register;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriBuilder;
import java.util.*;


/**
 * Created by DaH4uk on 06.07.2016.
 * Contains the implementation of methods for obtaining information about the registers.
 * https://konsort.planfix.ru/task/32615
 */
public class RegisterService {

    private static HashMap<String, String> pathMap = new HashMap<>();
    private static HashMap<String, Register> registersMap = new HashMap<>();
    //инициализация конфига
    private static final ClientConfig config = new DefaultClientConfig();
    private static final Client client = Client.create(config);
    //Sedona web url
    private static final WebResource service = client.resource(UriBuilder.fromUri("http://46.146.194.148:80/").build());
    private static final JsonParser parser = new JsonParser();


    public static Map<String, Register> getRegistersMap() {
        return registersMap;
    }

    static {
        //base type of registers
//        pathMap.put("Discret/","Discrete Output Coil");
        pathMap.put("AI/", "Analog Input");
        pathMap.put("AO/", "Analog Output");
        pathMap.put("AI1/", "Analog Input1");
//        pathMap.put("Numeri1/", "Numeric Output Holding Register");
        //login pass for sedona
        client.addFilter(new HTTPBasicAuthFilter("admin", ""));

    }



    //Implements update all registers information.
    static void getAllRegisters() {
        for (Object folder : pathMap.keySet()) {
            //sets path of base type registers
            String jsonFolder = service.path("json").path("app/drivers/modbus/remote/rtu1/slave1/points/" + folder).accept(MediaType.APPLICATION_JSON).get(String.class);

            JsonElement jsonElementFold = parser.parse(jsonFolder);
            JsonObject rootObjectFold = jsonElementFold.getAsJsonObject(); // чтение главного объекта
            //Reading root element in json
            JsonObject jsonObject = rootObjectFold.getAsJsonObject("obj");
            //Receiving a parameter array
            JsonArray jsonArrayFold = jsonObject.getAsJsonArray("ref");
            //Getting references to registers
            for (JsonElement p : jsonArrayFold) {
                String registerUrl = folder + p.getAsJsonObject().get("href").getAsString();
                registersMap.put(registerUrl,RegisterService.getRegister(registerUrl));
            }
        }
    }


    //Getting information about 1 register
    private static Register getRegister(String url) {

        // sets path of register
        String json = service.path("json").path("app/drivers/modbus/remote/rtu1/slave1/points/" + url).accept(MediaType.APPLICATION_JSON).get(String.class);

        JsonParser parser = new JsonParser();
        JsonElement jsonElement = parser.parse(json);
        JsonObject rootObject = jsonElement.getAsJsonObject(); // чтение главного объекта
        //Reading root element in json
        JsonObject object = rootObject.getAsJsonObject("real");


        JsonArray jsonArray = new JsonArray();
        //Reading fields
        JsonArray jsonArrayInt = object.getAsJsonArray("int");
        JsonArray jsonArrayShort = object.getAsJsonArray("short");
        JsonArray jsonArrayReal = object.getAsJsonArray("real");
        JsonArray jsonArrayString = object.getAsJsonArray("str");

        //Creates a new Register object, parsing and sets fields
        Register register = new Register();
        if (jsonArrayInt != null)
            for (JsonElement o : jsonArrayInt) {


                if (o.getAsJsonObject().get("name").getAsString().equals("meta")) {
                    register.setMeta(o.getAsJsonObject().get("val").getAsInt());
                } else if (o.getAsJsonObject().get("name").getAsString().equals("status")) {
                    register.setStatus(o.getAsJsonObject().get("val").getAsInt());
                } else if (o.getAsJsonObject().get("name").getAsString().equals("address")) {
                    register.setAddress(o.getAsJsonObject().get("val").getAsInt());
                } else if (o.getAsJsonObject().get("name").getAsString().equals("registerDataType")) {
                    register.setRegisterDataType(o.getAsJsonObject().get("val").getAsInt());
                } else if (o.getAsJsonObject().get("name").getAsString().equals("priority")) {
                    register.setPriority(o.getAsJsonObject().get("val").getAsInt());
                }

                jsonArray.add(o);
            }
        if (jsonArrayShort != null)
            for (JsonElement o : jsonArrayShort) {
                if (o.getAsJsonObject().get("name").getAsString().equals("refId")) {
                    register.setRefId(o.getAsJsonObject().get("val").getAsShort());
                }
                jsonArray.add(o);
            }
        if (jsonArrayReal != null)
            for (JsonElement o : jsonArrayReal) {
                if (o.getAsJsonObject().get("name").getAsString().equals("out")) {
                    register.setOut(o.getAsJsonObject().get("val").getAsDouble());
                }
                jsonArray.add(o);
            }
        if (jsonArrayString != null)
            for (JsonElement o : jsonArrayString) {
                if (o.getAsJsonObject().get("name").getAsString().equals("description")) {
                    register.setDescription(o.getAsJsonObject().get("val").getAsString());
                }
                jsonArray.add(o);
            }


        return register;
    }

}
