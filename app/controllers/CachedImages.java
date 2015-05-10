package controllers;

import play.cache.Cache;
import play.data.Upload;
import play.mvc.Controller;

import java.io.ByteArrayInputStream;
import java.util.Base64;

/**
 * Created by kermama on 19/04/15.
 */
public class CachedImages extends Controller {
    /**
     * @param image
     */
    public static void cacheImage(String token, Upload image) {
        Base64.Encoder encoder = Base64.getEncoder();
        String encodedContent = encoder.encodeToString(image.asBytes());

        if (Cache.get(token + "_icon") != null) {
            Cache.replace(token + "_icon", encodedContent, "5mn");
        } else {
            Cache.add(token + "_icon", encodedContent, "5mn");
        }
    }

    /**
     *
     */
    public static void showImage(String token) {
        Base64.Decoder decoder = Base64.getDecoder();

        String encodedContent = (String) Cache.get(token + "_icon");
        byte[] decodedContent = decoder.decode(encodedContent);

        response.setContentTypeIfNotSet("image/png");
        renderBinary(new ByteArrayInputStream(decodedContent));
    }
}
