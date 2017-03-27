package com.hjc.herol.util;

import org.codehaus.jackson.JsonFactory;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.annotate.JsonMethod;
import org.codehaus.jackson.map.ObjectMapper;

import java.io.IOException;
import java.io.StringWriter;
import java.util.Map;

public class JsonUtil {
    private static ObjectMapper objectMapper = null;

    static {
        objectMapper = new ObjectMapper();
    }

    public static String toJsonString(Object obj) throws IOException {

        StringWriter writer = new StringWriter();
        JsonGenerator gen = new JsonFactory().createJsonGenerator(writer);
        objectMapper.writeValue(gen, obj);
        gen.close();
        String json = writer.toString();
        writer.close();
        return json;

    }

    public static Map readJson2Map(String json) {
        Map<String, String> maps = null;
        try {
            maps = objectMapper.readValue(json, Map.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return maps;
    }
}
