package ru.consort.sensor.beans;

import ru.consort.sensor.Services.InfoService;
import ru.consort.sensor.entities.Parameter;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import java.io.IOException;
import java.io.Serializable;
import java.util.List;


/**
 * Автор: Туров Данил
 * Дата создания: 04.08.2016.
 * Реализует управление на странице /info
 * Проект: "Модуль локальной автоматизации".
 * Консорт.
 */
@ManagedBean(name = "infoBean")
@ViewScoped
public class InfoBean implements Serializable {
    private List<Parameter> parameters;

    @ManagedProperty("#{infoService}")
    private InfoService service;

    /**
     * При инициализации бина получает list параметров
     * и помещает его в поле parameters
     */
    @PostConstruct
    public void init() {
        if (!(boolean) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("authorized")){
            try {
                FacesContext.getCurrentInstance().getExternalContext().redirect("/NO");
            } catch (IOException e){/*Nope*/}
        }
        parameters = service.createParameters();
    }

    public List<Parameter> getParameters() {
        return parameters;
    }

    public void setService(InfoService service) {
        this.service = service;
    }
}
