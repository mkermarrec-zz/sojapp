package helpers;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import play.mvc.Http;
import play.mvc.Scope;

import java.util.Iterator;
import java.util.Map;

/**
 * Project: DEVICE <br/>
 * File : ${FILE_NAME} <br/>
 * Package : helpers <br/>
 * Description : <br/>
 *
 * @author : xcks8484
 * @since : 24/11/2014
 */
public class LoggerHelper {

    private static final String SEPARATOR = ";";
    private static LoggerHelper instance;

    private LoggerHelper() {
    }

    public static LoggerHelper getInstance() {
        if (instance == null) {
            instance = new LoggerHelper();
        }
        return instance;
    }

    /**
     * Logs a user action containing user name, date, action type and serialized request
     *
     * @param userName
     * @param action
     * @param request
     */
    public static void logUserAction(String userName, String action, String objectType, Http.Request request) {
        StringBuilder builder = new StringBuilder();

        builder.append(userName);
        builder.append(SEPARATOR);
        builder.append(action);
        builder.append(SEPARATOR);
        builder.append(objectType);
        builder.append(SEPARATOR);

        // serializing request
        builder.append(serializeRequestParams(request.params));

        // logging action
        play.Logger.info(builder.toString());
    }

    /**
     * Serializes all parameters of a request
     *
     * @param params
     * @return
     */
    private static String serializeRequestParams(Scope.Params params) {
        StringBuilder builder = new StringBuilder("{");

        Iterator<Map.Entry<String, String[]>> iterator = params.all().entrySet().iterator();

        if (iterator.hasNext()) {
            builder.append(serializeParam(iterator.next()));
        }

        while (iterator.hasNext()) {
            builder.append(", ");
            builder.append(serializeParam(iterator.next()));
        }

        builder.append("}");
        return builder.toString();
    }

    /**
     * Serializes a request parameter
     *
     * @param entry
     */
    private static String serializeParam(Map.Entry<String, String[]> entry) {
        StringBuilder builder = new StringBuilder();
        builder.append(entry.getKey());
        builder.append("=[");

        for (int i = 0; i < entry.getValue().length; i++) {

            if (i != entry.getValue().length - 1) {
                builder.append(", ");
            }
        }

        builder.append("]");
        return builder.toString();
    }



}
