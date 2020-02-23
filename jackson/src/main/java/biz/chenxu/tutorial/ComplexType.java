package biz.chenxu.tutorial;

import lombok.Data;
import lombok.ToString;

import java.util.Date;
import java.util.List;

@Data
@ToString
public class ComplexType {

    private String stringField;

    private Date dateField;

    private Integer intField;

    private Double doubleField;

    private Float floatField;

    private List<ComplexType> complexTypeList;

    private ComplexType[] complexArray;

}
