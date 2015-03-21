
package controllers;

import play.data.validation.Required;
import play.i18n.Messages;
import play.mvc.Controller;
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
    public static void show() {
        String userName = Security.connected();

        models.User user = models.User.find("byLogin", userName).first();
        render(user);
    }

    /**
     *
     */
    public static void save(@Required String email, String password, String newPassword, String newPasswordBis) {

        String userName = Security.connected();
        models.User user = models.User.find("byLogin", userName).first();

        validateNewPassword(user, password, newPassword, newPasswordBis);

        if (validation.hasErrors()) {
            params.flash(); // add http parameters to the flash scope
            validation.keep(); // keep the errors for the next request
        } else {
            if (!"".equals(newPassword)) {
                user.setPassword(newPassword);
                flash.success(Messages.get("userOk"));
            } else {
                flash.success(Messages.get("userEmailOk"));
            }
            user.setEmail(email);
            user.save();
        }

        show();
    }

    /**
     * @param password
     * @param newPassword
     * @param newPasswordBis
     * @throws Exception
     */
    private static void validateNewPassword(models.User user, String password, String newPassword, String newPasswordBis) {
        if (!"".equals(password) && !"".equals(newPassword) && !"".equals(newPasswordBis)) {

            if (user.getPassword().equals(password)) {
                if (!newPassword.equals(newPasswordBis)) {
                    validation.addError("newPasswordBis", Messages.get("newPasswordBisError"));
                    flash.error(Messages.get("newPasswordBisError"));
                }
            } else {
                validation.addError("password", Messages.get("passwordError"));
                flash.error(Messages.get("passwordError"));
            }

        } else {
            if ("".equals(password) && "".equals(newPassword) && "".equals(newPasswordBis)) {
                // doing nothing
            } else {
                if (!"".equals(password)) {
                    validation.addError("newPassword", Messages.get("newPasswordError"));
                    flash.error(Messages.get("newPasswordError"));
                } else {
                    validation.addError("password", Messages.get("passwordRequiredError"));
                    flash.error(Messages.get("passwordRequiredError"));
                }
            }
        }
    }

}
