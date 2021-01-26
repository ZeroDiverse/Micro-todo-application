package fil.adventural.microprojectmanagement.utils;

import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonUtils {
    /**
     * Change object to json string
     * @param obj the object
     * @return the json of object
     */
    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
