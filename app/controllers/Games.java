
package controllers;

import play.cache.Cache;
import play.data.Upload;
import play.mvc.With;

import java.io.ByteArrayInputStream;
import java.util.Base64;

/**
 * Project: SOJ <br/>
 * File : Games.java<br/>
 * Package : controllers<br/>
 * Description : <br/>
 *
 * @author : xcks8484
 * @since : 15 oct. 2013
 */
@With(Secure.class)
public class Games extends CRUD {
}
