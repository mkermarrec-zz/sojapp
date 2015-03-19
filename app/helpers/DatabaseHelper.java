package helpers;

import org.apache.commons.io.FileUtils;
import play.Logger;
import play.Play;
import play.test.Fixtures;

import java.io.File;

/**
 * Project: SOJ_workspace <br/>
 * File : ${FILE_NAME} <br/>
 * Package : helpers <br/>
 * Description : <br/>
 *
 * @author : xcks8484
 * @since : 26/02/2015
 */
public class DatabaseHelper {
    private static DatabaseHelper ourInstance = new DatabaseHelper();

    private DatabaseHelper() {
    }

    public static DatabaseHelper getInstance() {
        return ourInstance;
    }

    /**
     * Deletes all data from database and inject minimum data such as users countries, zones ...
     */
    public static void clearAllData() throws Exception {
        Logger.info("Starting data deletion ...");

        try {
            Fixtures.deleteAllModels();

//            Fixtures.loadModels("../../../conf/init/initial-users.yml");
//            Fixtures.loadModels("../../../conf/init/members.yml");
//            Fixtures.loadModels("../../../conf/init/games.yml");
            Fixtures.loadModels("init/initial-users.yml");
            Fixtures.loadModels("init/members.yml");
            Fixtures.loadModels("init/games.yml");

            FileUtils.cleanDirectory(new File(Play.configuration.getProperty("attachments.path")));

            Logger.info("Data have been well deleted");
        } catch (Exception e) {
            if (e.getCause() != null) {
                Logger.error(e.getCause().getMessage());
            } else {
                Logger.error(e.getMessage());
            }
            Logger.error("Error during deletion process");
            throw new Exception(e);
        }
    }
}
