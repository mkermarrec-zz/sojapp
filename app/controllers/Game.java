/**
 * File : Game.java
 */

package controllers;

import play.mvc.Controller;
import play.mvc.With;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.logging.Logger;

/**
 * Project: SOJ <br/>
 * File : Game.java<br/>
 * Package : controllers<br/>
 * Description : <br/>
 *
 * @author : xcks8484
 * @since : 24 oct. 2013
 */
@With(Secure.class)
public class Game extends Controller {

    /**
     * @param id
     */
    public static void show(String id) {
        try {
            if (id != null) {
                models.Game game = models.Game.findById(Long.parseLong(id));
                if (game != null) {
                    render(game);
                } else {
                    throw (new Exception());
                }
            } else {
                throw (new Exception());
            }
        } catch (Exception e) {
            flash("error", "Erreur sur l'affichage du jeu");
            play.Logger.error(e.getMessage());

            redirect("Games.list");
        }
    }

    /**
     * @param id
     */
    public static void showImage(String id) {
        models.Game game = models.Game.findById(Long.parseLong(id));
        InputStream inputStream = new ByteArrayInputStream(game.getPicture());
        renderBinary(inputStream, "image", game.getPicture().length, "image/png", true);
    }
}
