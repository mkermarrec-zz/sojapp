package controllers;

import controllers.security.CheckAdmin;
import helpers.DatabaseHelper;
import play.mvc.Controller;
import play.mvc.With;

/**
 * Project: SOJ_workspace <br/>
 * File : ${FILE_NAME} <br/>
 * Package : controllers <br/>
 * Description : <br/>
 *
 * @author : xcks8484
 * @since : 26/02/2015
 */
@CheckAdmin
@With(controllers.security.Secure.class)
public class ClearData extends Controller {
    private static DatabaseHelper databaseHelper = DatabaseHelper.getInstance();

    public static void clearData() {
        try {
            databaseHelper.clearAllData();
            renderText("OK");
        } catch (Exception e) {
            error(e.getMessage());
        }

    }
}
