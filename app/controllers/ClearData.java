package controllers;

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
@With(Secure.class)
@Check("admin")
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
