/**
 * File : Bootstrap.java
 */
package bootstrap;

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

  @Override
  public void doJob() {
    // Check if the database is empty
    if (User.count() == 0) {
      Fixtures.loadModels("initial-data.yml");
    }
  }
}
