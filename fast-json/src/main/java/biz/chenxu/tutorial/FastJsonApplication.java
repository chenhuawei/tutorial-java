package biz.chenxu.tutorial;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class FastJsonApplication {



    public static void main(String[] args) {
        String json = "{\"stringArray\": [\"abc\", \"def\"]}";
        JSONObject jsonObject = JSON.parseObject(json);
        String[] stringArray1 = jsonObject.getObject("stringArray", new String[]{}.getClass());
        System.out.println("method 1 " + Arrays.toString(stringArray1));

        String[] stringArray2 = jsonObject.getObject("stringArray", String[].class);
        System.out.println("method 2 " + Arrays.toString(stringArray2));

        String complexJson = createComplexTypeJson();
        Type type = new TypeReference<List<ComplexType>>(){}.getType();
        List<ComplexType> complexTypeList = JSONObject.parseObject(complexJson, type);

        String complexTypeString = complexTypeList.stream().map(Object::toString)
                .collect(Collectors.joining(", "));

        System.out.println(complexTypeString);
    }

    private static ComplexType createComplexType(int i) {
        ComplexType complexType = new ComplexType();
        complexType.setStringField(String.format("stringFieldValue%d", i));
        complexType.setIntField(i + 1);
        complexType.setDoubleField(Double.valueOf(i + 2.0));
        complexType.setFloatField(Float.valueOf(i));
        complexType.setDateField(new Date());
        complexType.setComplexArray(new ComplexType[1]);
        complexType.setComplexTypeList(new ArrayList<>());
        return complexType;
    }

    private static String createComplexTypeJson() {
        List<ComplexType> list = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            ComplexType complexType = createComplexType(i);
            list.add(complexType);
        }
        return JSON.toJSONString(list);
    }
}
