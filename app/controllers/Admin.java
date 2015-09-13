
package controllers;

import play.mvc.Controller;
import play.mvc.With;

/**
 * 
 * Project: SOJ <br/>
 * File : Home.java<br/>
 * Package : controllers<br/>
 * Description : <br/>
 * 
 * @author : xcks8484
 * @since : 8 nov. 2013
 */
@With(controllers.security.Secure.class)
public class Admin extends Controller {

  public static void index() {
    render();
  }

}
