package controllers;

import play.mvc.With;

/**
 * 
 * Project: SOJ <br/>
 * File : Borrowings.java<br/>
 * Package : controllers<br/>
 * Description : <br/>
 * 
 * @author : xcks8484
 * @since : 15 oct. 2013
 */
@Check("admin")
@With(Secure.class)
public class Borrowings extends CRUD {

}
