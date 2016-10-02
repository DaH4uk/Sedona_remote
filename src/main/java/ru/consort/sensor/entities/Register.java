package ru.consort.sensor.entities;

/**
 * Created by DaH4uk on 06.07.2016.
 * It is a Register Entity. Needs to implement by RegisterService.
 * https://konsort.planfix.ru/task/32615
 */
public class Register {
    private int meta;
    private int status;
    private int address;
    private int registerDataType;
    private int priority;
    private double out;
    private Short refId;
    private String description;

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public int getPriority() {

        return priority;
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

    public void setOut(double out) {
        this.out = out;
    }

    public void setRefId(Short refId) {
        this.refId = refId;
    }

    public void setDescription(String description) {
        this.description = description;
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

    public double getOut() {
        return out;
    }

    public Short getRefId() {
        return refId;
    }

    public String getDescription() {
        return description;
    }


    @Override
    public String toString() {
        return this.getMeta() + " " + this.getStatus() + " " + this.getAddress() + " " + this.getRegisterDataType() + " " + this.getPriority() + " " + this.getRefId() + " " + this.getOut() + " " + this.getDescription();
    }
}
