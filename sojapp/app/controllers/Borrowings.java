package controllers;

import play.db.Model;
import play.exceptions.TemplateNotFoundException;
import play.mvc.With;

import java.lang.reflect.Constructor;
import java.util.List;

/**
 * Project: SOJ <br/>
 * File : Borrowings.java<br/>
 * Package : controllers<br/>
 * Description : <br/>
 *
 * @author : xcks8484
 * @since : 15 oct. 2013
 */
@Check("admin")
@With(Secure.class)
public class Borrowings extends CRUD {
    /**
     *
     */
    public static void blank() throws Exception {
        ObjectType type = ObjectType.get(getControllerClass());
        notFoundIfNull(type);
        Constructor<?> constructor = type.entityClass.getDeclaredConstructor();
        constructor.setAccessible(true);
        Model object = (Model) constructor.newInstance();
        List<models.Game> games = models.Game.notBorrowed();

        try {
            render(type, object, games);
        } catch (TemplateNotFoundException e) {
            render("CRUD/blank.html", type, object, games);
        }
    }

    /**
     *
     * @param id
     * @throws Exception
     */
    public static void show(String id) throws Exception {
        ObjectType type = ObjectType.get(getControllerClass());
        notFoundIfNull(type);
        Model object = type.findById(id);
        notFoundIfNull(object);
        List<models.Game> games = models.Game.notBorrowed();

        try {
            render(type, object, games);
        } catch (TemplateNotFoundException e) {
            render("CRUD/show.html", type, object, games);
        }
    }
}
