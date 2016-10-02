package ru.consort.sensor.beans;

import org.primefaces.context.RequestContext;
import org.primefaces.model.TreeNode;
import ru.consort.sensor.Services.RegisterService;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;


/**
 * Created by DaH4uk on 11.07.2016.
 * Realised registerInfo functionality.
 * Realised functionality of Select dialog window and display .
 * Receives the input of URL from the session map of the FacesContext.
 * https://konsort.planfix.ru/task/32615
 */


@ManagedBean(name = "registerInfoBean")
@ViewScoped
public class RegisterInfoBean {
    private int meta;
    private int status;
    private int address;
    private int registerDataType;
    private int priority;
    private double out;
    private Short refId;
    private String description;
    private TreeNode selectedNode;
    //default url
    private String url = "Numeric/Numeri1/";
    private boolean disabled;

    public boolean isDisabled() {
        return disabled;
    }

    public void setDisabled(boolean disabled) {
        this.disabled = disabled;
    }


    @PostConstruct
    public void init() {
        //get url from Session
        this.url = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("url");

        //sets fields
        this.meta = RegisterService.getRegistersMap().get(url).getMeta();
        this.status = RegisterService.getRegistersMap().get(url).getStatus();
        this.address = RegisterService.getRegistersMap().get(url).getAddress();
        this.registerDataType = RegisterService.getRegistersMap().get(url).getRegisterDataType();
        this.priority = RegisterService.getRegistersMap().get(url).getPriority();
        this.out = RegisterService.getRegistersMap().get(url).getOut();
        this.refId = RegisterService.getRegistersMap().get(url).getRefId();
        this.description = RegisterService.getRegistersMap().get(url).getDescription();

        //need for registers, where the priority field is not implemented
        this.disabled = priority == 0;

    }
    //TODO: I need to implement the functionality of the save button
    public void saveRegister(){
        FacesMessage message = new FacesMessage("Сохранить: ", "Данный функционал еще не реализован");
        FacesContext.getCurrentInstance().addMessage(null, message);

    }

    public void cancel(){
        RequestContext.getCurrentInstance().closeDialog(null);
    }

    public int getMeta() {
        return meta;
    }

    public int getStatus() {
        return status;
    }

    public int getAddress() {
        return address;
    }

    public int getRegisterDataType() {
        return registerDataType;
    }

    public int getPriority() {
        return priority;
    }

    public double getOut() {
        return out;
    }

    public Short getRefId() {
        return refId;
    }

    public String getDescription() {
        return description;
    }


    public TreeNode getSelectedNode() {
        return selectedNode;
    }

    public void setSelectedNode(TreeNode selectedNode) {
        this.selectedNode = selectedNode;
    }

    public void setMeta(int meta) {
        this.meta = meta;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public void setAddress(int address) {
        this.address = address;
    }

    public void setRegisterDataType(int registerDataType) {
        this.registerDataType = registerDataType;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public void setOut(double out) {
        this.out = out;
    }

    public void setRefId(Short refId) {
        this.refId = refId;
    }

    public void setDescription(String description) {
        this.description = description;
    }


}
