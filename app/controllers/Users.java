package controllers;

import controllers.security.CheckAdmin;
import play.db.Model;
import play.exceptions.TemplateNotFoundException;
import play.mvc.With;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * 
 * Project: SOJ <br/>
 * File : Users.java<br/>
 * Package : controllers<br/>
 * Description : <br/>
 * 
 * @author : xcks8484
 * @since : 15 oct. 2013
 */
@CheckAdmin
@With(controllers.security.Secure.class)
public class Users extends CRUD {

    public static void list(int page, String search, String searchFields, String orderBy, String order) {
        ObjectType type = ObjectType.get(getControllerClass());
        notFoundIfNull(type);
        if (page < 1) {
            page = 1;
        }
        List<Model> objects = type.findPage(page, search, searchFields, orderBy, order, (String) request.args.get("where"));
        Collections.sort(objects,
                (user1, user2) -> ((models.User) user1).getFullName().compareTo(((models.User)user2).getFullName()));
        Long count = type.count(search, searchFields, (String) request.args.get("where"));
        Long totalCount = type.count(null, null, (String) request.args.get("where"));
        try {
            render(type, objects, count, totalCount, page, orderBy, order);
        } catch (TemplateNotFoundException e) {
            render("CRUD/list.html", type, objects, count, totalCount, page, orderBy, order);
        }
    }
}
