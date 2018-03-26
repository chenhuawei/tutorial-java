package biz.chenxu.tutorial;

import java.util.Date;
import java.util.List;

public class ComplexType {

    private String stringField;

    private Date dateField;

    private Integer intField;

    private Double doubleField;

    private Float floatField;

    private List<ComplexType> complexTypeList;

    private ComplexType[] complexArray;

    public String getStringField() {
        return stringField;
    }

    public void setStringField(String stringField) {
        this.stringField = stringField;
    }

    public Date getDateField() {
        return dateField;
    }

    public void setDateField(Date dateField) {
        this.dateField = dateField;
    }

    public Integer getIntField() {
        return intField;
    }

    public void setIntField(Integer intField) {
        this.intField = intField;
    }

    public Double getDoubleField() {
        return doubleField;
    }

    public void setDoubleField(Double doubleField) {
        this.doubleField = doubleField;
    }

    public Float getFloatField() {
        return floatField;
    }

    public void setFloatField(Float floatField) {
        this.floatField = floatField;
    }

    public List<ComplexType> getComplexTypeList() {
        return complexTypeList;
    }

    public void setComplexTypeList(List<ComplexType> complexTypeList) {
        this.complexTypeList = complexTypeList;
    }

    public ComplexType[] getComplexArray() {
        return complexArray;
    }

    public void setComplexArray(ComplexType[] complexArray) {
        this.complexArray = complexArray;
    }
}
