/**
 * File : Bootstrap.java
 */
package bootstrap;

import helpers.DatabaseHelper;
import models.User;
import play.jobs.Job;
import play.jobs.OnApplicationStart;
import play.test.Fixtures;


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
    private DatabaseHelper databaseHelper = DatabaseHelper.getInstance();

    @Override
    public void doJob() {
        if(User.findAll().isEmpty()) {
            Fixtures.loadModels("init/initial-users.yml");
            Fixtures.loadModels("init/members.yml");
            Fixtures.loadModels("init/games.yml");
        }
    }
}
