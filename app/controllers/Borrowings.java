package controllers;

import controllers.security.CheckAdmin;
import models.Borrowing;
import org.apache.commons.collections.iterators.EntrySetMapIterator;
import play.db.Model;
import play.exceptions.TemplateNotFoundException;
import play.mvc.With;

import java.lang.reflect.Constructor;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Project: SOJ <br/>
 * File : Borrowings.java<br/>
 * Package : controllers<br/>
 * Description : <br/>
 *
 * @author : xcks8484
 * @since : 15 oct. 2013
 */
@CheckAdmin
@With(controllers.security.Secure.class)
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

    public static void returnGame(String id, Date returnDate) throws Exception {
        ObjectType type = ObjectType.get(getControllerClass());
        notFoundIfNull(type);
        Model object = type.findById(id);
        notFoundIfNull(object);

        Borrowing borrowing = (Borrowing) object;

        borrowing.setArchived(true);
        borrowing.setReturnDate(returnDate);
        borrowing.save();
        borrowing.getGame().setBorrowing(null);
        borrowing.getGame().save();

        redirect("Borrowings.list");
    }

    public static void list(int page, String search, String searchFields, String orderBy, String order) {

        request.args.put("where", "archived = false");
        ObjectType type = ObjectType.get(getControllerClass());
        notFoundIfNull(type);
        if (page < 1) {
            page = 1;
        }
        List<Model> objects = type.findPage(page, search, searchFields, orderBy, order, (String) request.args.get("where"));
        Collections.sort(objects,
                (b1, b2) -> ((models.Borrowing) b1).getExpectedReturnDate().compareTo(((models.Borrowing) b2).getExpectedReturnDate()));
        Long count = type.count(search, searchFields, (String) request.args.get("where"));
        Long totalCount = type.count(null, null, (String) request.args.get("where"));
        try {
            render(type, objects, count, totalCount, page, orderBy, order);
        } catch (TemplateNotFoundException e) {
            render("CRUD/list.html", type, objects, count, totalCount, page, orderBy, order);
        }
    }

    public static void listArchived(int page, String search, String searchFields, String orderBy, String order) {

        request.args.put("where", "archived = true");
        ObjectType type = ObjectType.get(getControllerClass());
        notFoundIfNull(type);
        if (page < 1) {
            page = 1;
        }
        List<Model> objects = type.findPage(page, search, searchFields, orderBy, order, (String) request.args.get("where"));
        Long count = type.count(search, searchFields, (String) request.args.get("where"));
        Long totalCount = type.count(null, null, (String) request.args.get("where"));
        try {
            render(type, objects, count, totalCount, page, orderBy, order);
        } catch (TemplateNotFoundException e) {
            render("CRUD/list.html", type, objects, count, totalCount, page, orderBy, order);
        }
    }
}
