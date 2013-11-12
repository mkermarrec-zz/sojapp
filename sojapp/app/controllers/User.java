
package controllers;

import play.data.validation.Validation.ValidationResult;
import play.mvc.Controller;
import play.mvc.Scope.Params;
import play.mvc.With;

/**
 * 
 * Project: SOJ <br/>
 * File : User.java<br/>
 * Package : controllers<br/>
 * Description : <br/>
 * 
 * @author : xcks8484
 * @since : 29 oct. 2013
 */
@With(Secure.class)
public class User extends Controller {

  /**
   * @throws Throwable
   * 
   */
  public static void show() throws Throwable {
    String userName = Security.connected();

    if (userName == null) {
      Secure.logout();
    } else {
      models.User user = models.User.find("byLogin", userName).first();
      render(user);
    }
  }

  /**
   * 
   */
  public static void save() {
    String userName = Security.connected();
    models.User user = models.User.find("byLogin", userName).first();

    try {
      validateForm(params);
      String email = params.get("email");
      String password = params.get("password");

      if (!user.getEmail().equals(email) || !user.getPassword().equals(password)) {
        flash.put("success", "Vos données on bien été enregistrées");
        user.setEmail(email);
        user.setPassword(password);
        user.save();
      }

      redirect("/user/show");
    } catch (Exception e) {
      flash.put("error", e.getMessage());
      redirect("/user/show");
    }
  }

  /**
   * @param params
   * @throws Exception
   * 
   */
  private static void validateForm(Params params) throws Exception {
    String email = params.get("email");
    String password = params.get("password");
    String passwordbis = params.get("passwordbis");

    ValidationResult result = validation.email(email);

    if (!result.ok) {
      throw (new Exception("Email invalide"));
    }

    if (password != null && password != null && !password.equals(passwordbis)) {
      throw (new Exception("Les mots de passes doivent être identiques"));
    }
  }

}
