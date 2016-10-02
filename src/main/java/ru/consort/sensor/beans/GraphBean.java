package ru.consort.sensor.beans;

import org.primefaces.event.CellEditEvent;
import org.primefaces.event.RowEditEvent;
import org.primefaces.model.chart.*;
import ru.consort.sensor.Services.GraphService;
import ru.consort.sensor.entities.Parameter;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.context.FacesContext;
import java.util.List;


/**
 * Created by DaH4uk on 04.08.2016.
 */
@ManagedBean(name = "graphBean")
public class GraphBean {
    private LineChartModel lineModel;
    private List<Parameter> parameters;

    @PostConstruct
    public void init() {
        createLineModel();
        parameters = service.createParameters(6);
    }

    @ManagedProperty("#{graphService}")
    private GraphService service;

    public LineChartModel getLineModel() {
        return lineModel;
    }

    private void createLineModel() {
        lineModel = initLinearModel();
        lineModel.setTitle("График температуры");
        lineModel.setLegendPosition("e");
        Axis yAxis = lineModel.getAxis(AxisType.Y);
        lineModel.getAxes().put(AxisType.X, new CategoryAxis("Температура наружного воздуха"));
        yAxis.setMin(20);
        yAxis.setMax(100);

        yAxis.setLabel("Температура подачи");

    }

    private LineChartModel initLinearModel() {
        LineChartModel model = new LineChartModel();

        LineChartSeries series1 = new LineChartSeries();

        series1.set(15, 30);
        series1.set(5, 35);
        series1.set(-5, 50);
        series1.set(-15, 60);
        series1.set(-20, 80);
        series1.set(-30, 90);


        model.addSeries(series1);


        return model;
    }

    public List<Parameter> getParameters() {
        return parameters;
    }


    public void setService(GraphService service) {
        this.service = service;
    }

    public void onRowEdit(RowEditEvent event) {
        FacesMessage msg = new FacesMessage("Температура изменена", ((Parameter) event.getObject()).getParameterName());
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public void onRowCancel(RowEditEvent event) {
        FacesMessage msg = new FacesMessage("Edit Cancelled", ((Parameter) event.getObject()).getParameterName());
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public void onCellEdit(CellEditEvent event) {
        Object oldValue = event.getOldValue();
        Object newValue = event.getNewValue();

        if(newValue != null && !newValue.equals(oldValue)) {
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Cell Changed", "Old: " + oldValue + ", New:" + newValue);
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }
    }

}
