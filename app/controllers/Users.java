package controllers;

import play.mvc.With;

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
@Check("admin")
@With(Secure.class)
public class Users extends CRUD {
}
