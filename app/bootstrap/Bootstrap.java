/**
 * File : Bootstrap.java
 */
package bootstrap;

import models.Borrowing;
import models.Game;
import models.User;
import play.jobs.Job;
import play.jobs.OnApplicationStart;
import play.test.Fixtures;

import java.util.List;


/**
 * Project: SOJ <br/>
 * File : Bootstrap.java<br/>
 * Package : bootstrap<br/>
 * Description : <br/>
 *
 * @author : xcks8484
 * @since : 15 oct. 2013
 */
@OnApplicationStart
public class Bootstrap extends Job {

    @Override
    public void doJob() {
        // Check if the database is empty
        if (User.count() == 0) {
            Fixtures.loadModels("initial-data.yml");
        }

        List<Borrowing> borrowings = Borrowing.findAll();
        List<Game> games = Game.findAll();

        games.get(0).setBorrowing(borrowings.get(0));
        games.get(0).save();
        games.get(2).setBorrowing(borrowings.get(1));
        games.get(2).save();
    }
}
