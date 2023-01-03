package lk.ijse.pos.util;

import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObjectBuilder;

/**
 * @author : Ashan Sandeep
 * @date : 1/3/2023
 * @since : 0.1.0
 **/

public class ResponseUtil {
    private static ResponseUtil responseUtil;
    private final JsonObjectBuilder responseObject;

    private ResponseUtil() {
        responseObject = Json.createObjectBuilder();
    }

    public static ResponseUtil getInstance() {
        return responseUtil == null ? responseUtil = new ResponseUtil() : responseUtil;
    }

    public JsonObjectBuilder getResponseObject(String state, String message, Object data) {
        if (!responseObject.build().isEmpty()) {
            responseObject.remove("state");
            responseObject.remove("message");
            responseObject.remove("data");
        }

        responseObject.add("state", state);
        responseObject.add("message", message);
        if (data instanceof JsonArrayBuilder) {
            responseObject.add("data", ((JsonArrayBuilder) data).build());
        } else {
            responseObject.add("data", data.toString());
        }

        return responseObject;
    }
}
