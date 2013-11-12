
package controllers;

import models.User;

/**
 * 
 * Project: SOJ <br/>
 * File : Security.java<br/>
 * Package : controllers<br/>
 * Description : <br/>
 * 
 * @author : xcks8484
 * @since : 15 oct. 2013
 */
public class Security extends Secure.Security {

  /**
   * 
   */
  static void onDisconnected() {
    session.clear();
  }

  static void onAuthenticated() {
    if (Security.isConnected()) {
      User user = User.find("byLogin", Security.connected()).first();
      session.put("usr", user.getFirstName());
      session.put("isAdmin", user.isAdmin());

      if (user.isAdmin()) {
        redirect("Admin.index");
      } else {
      redirect("Games.list");
      }
    } else {
      session.clear();
      redirect("/login");
    }
  }

  /**
   * 
   * @param username
   * @param password
   * @return
   */
  static boolean authentify(String username, String password) {
    return User.connect(username, password) != null;
  }

  /**
   * 
   * @param profile
   * @return
   */
  static boolean check(String profile) {
    if ("admin".equals(profile)) {
      return User.find("byLogin", connected()).<User> first().isAdmin();
    }
    return false;
  }
}
