
package controllers;

import play.data.validation.Validation.ValidationResult;
import play.mvc.Controller;
import play.mvc.Scope.Params;
import play.mvc.With;

/**
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
            String email = params.get("object_email");
            String password = params.get("object_password");
            String newPassword = params.get("object_newPassword");
            String newPasswordBis = params.get("object_newPasswordBis");

            if (email != null && !"".equals(email) && !user.getEmail().equals(email)) {
                user.setEmail(email);
                if (!user.validateAndSave()) {
                    throw (new Exception("Email non valide"));
                }
            }

            if (!"".equals(password) && user.getPassword().equals(password)) {
                if (newPassword != null && !"".equals(newPassword) && newPassword.equals(newPasswordBis)) {
                    user.setPassword(newPassword);
                    user.save();
                    redirect("/user/show");
                } else {
                    throw (new Exception("Les mots de passe sont vides ou ne sont pas identiques"));
                }
            } else {
                throw (new Exception("Mot de passe incorrect"));
            }
        } catch (Exception e) {
            flash("error", e.getMessage());
            redirect("/user/show");
        }

    }

    /**
     * @param params
     * @throws Exception
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
