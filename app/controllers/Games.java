
package controllers;

import org.apache.commons.io.FileUtils;
import play.cache.Cache;
import play.data.binding.Binder;
import play.db.Model;
import play.exceptions.TemplateNotFoundException;
import play.mvc.With;

import java.io.File;
import java.util.Base64;

/**
 * Project: SOJ <br/>
 * File : Games.java<br/>
 * Package : controllers<br/>
 * Description : <br/>
 *
 * @author : xcks8484
 * @since : 15 oct. 2013
 */
@With(Secure.class)
public class Games extends CRUD {

    public static void save(String id) throws Exception {
        ObjectType type = ObjectType.get(getControllerClass());
        notFoundIfNull(type);
        Model object = type.findById(id);
        notFoundIfNull(object);
        Binder.bindBean(params.getRootParamNode(), "object", object);
        String token = params.get("authenticityToken");
        String encodedContent = (String) Cache.get(token + "_icon");
        Base64.Decoder decoder = Base64.getDecoder();
        byte[] decodedContent = decoder.decode(encodedContent);
        ((models.Game) object).setPicture(decodedContent);
        validation.valid(object);
        if (validation.hasErrors()) {
            params.flash();
            renderArgs.put("error", play.i18n.Messages.get("crud.hasErrors"));
            try {
                render(request.controller.replace(".", "/") + "/show.html", type, object);
            } catch (TemplateNotFoundException e) {
                render("CRUD/show.html", type, object);
            }
        }
        object._save();
        flash.success(play.i18n.Messages.get("crud.saved", type.modelName));
        if (params.get("_save") != null) {
            redirect(request.controller + ".list");
        }
        redirect(request.controller + ".show", object._key());
    }
}
