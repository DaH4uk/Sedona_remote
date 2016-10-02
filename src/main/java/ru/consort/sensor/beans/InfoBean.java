package ru.consort.sensor.beans;

import ru.consort.sensor.Services.InfoService;
import ru.consort.sensor.entities.Parameter;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import java.io.Serializable;
import java.util.List;

/**
 * Created by DaH4uk on 04.08.2016.
 */

@ManagedBean(name = "infoBean")
@ViewScoped
public class InfoBean implements Serializable {

    private List<Parameter> parameters;

    @ManagedProperty("#{infoService}")
    private InfoService service;

    @PostConstruct
    public void init() {
        parameters = service.createParameters();
    }

    public List<Parameter> getParameters() {
        return parameters;
    }

    public void setService(InfoService service) {
        this.service = service;
    }


}
