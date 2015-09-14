package controllers.security;

import controllers.Admin;
import models.User;
import play.libs.Crypto;

/**
 * Manage the secure
 *
 * @author XCKS8484
 */
public class Security extends Secure.Security {

    /**
     * Authenticate the user
     *
     * @param username Login of the user
     * @param password Password of the user
     * @return Result of the authentication
     */
    public static boolean authenticate(String username, String password) {
        User user = User.find("byLogin", username).first();
        boolean result = false;

        if (user != null) {
            String signedPassword = Crypto.sign(password);

            result = user.getPassword().equals(signedPassword);
        }

        return result;
    }

    /**
     * Check the level of authorization of the user
     *
     * @return Is the user allowed to get access to the content
     */
    public static boolean checkAdmin() {
        User user = User.find("byLogin", session.get("username")).first();
        boolean hasProfile = user.isAdmin();
        if (hasProfile) {
            return true;
        }

        return false;
    }


    /**
     * Retrieve user name and last name
     *
     * @return Is the user name
     */
    public static String getUserName() {
        User user = User.find("byLogin", session.get("username")).first();
        return user.toString();
    }


    /**
     * @return
     */
    public static boolean checkNotAdmin() {
        User user = User.find("byLogin", session.get("username")).first();
        boolean hasProfile = user.isAdmin();
        if (!hasProfile) {
            return true;
        }

        return false;
    }

    /**
     * Called on disconnection, redirect the user to the identification form
     */

    public static void onDisconnected() {
    }

    /**
     * Called before disconnection
     */

    public static void onDisconnect() {
    }

    /**
     * Called after the authentifcation, redirect the user to the admin index page
     */

    public static void onAuthenticated() {
        if(checkAdmin()) {
            Admin.index();
        }else {
            redirect("/");
        }
    }

}