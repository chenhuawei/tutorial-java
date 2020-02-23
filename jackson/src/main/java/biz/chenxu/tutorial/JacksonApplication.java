package biz.chenxu.tutorial;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.List;

public class JacksonApplication {

    public static void main(String[] args) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();

        String json = "{\n" +
                "  \"stringField\": \"demoData\",\n" +
                "  \"dateField\": \"2020-02-23\",\n" +
                "  \"intField\": 1,\n" +
                "  \"doubleField\": 1.0,\n" +
                "  \"floatField\": 1.0,\n" +
                "  \"complexTypeList\": [\n" +
                "    {\n" +
                "      \"stringField\": \"innerDemoData\",\n" +
                "      \"dateField\": \"2020-02-23\",\n" +
                "      \"intField\": 1,\n" +
                "      \"doubleField\": 1.0,\n" +
                "      \"floatField\": 1.0\n" +
                "    }\n" +
                "  ]\n" +
                "}";

        JsonNode rootNode = objectMapper.readTree(json);
        JsonNode listNode = rootNode.get("complexTypeList");
        JsonParser token = objectMapper.treeAsTokens(listNode);
        List<ComplexType> list = objectMapper.readValue(token, new TypeReference<List<ComplexType>>(){});
        for (ComplexType complexType : list) {
            System.out.println(complexType);
        }
    }
}
