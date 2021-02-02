package ua.edu.sumdu.j2se.Klymenko.tasks.additions;

import org.apache.log4j.Logger;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Properties;

/**
 * Class for provide load properties form file
 */
public class PropertyLoad {
    private static final Logger logger = Logger.getLogger(PropertyLoad.class);

    /**
     * Gets set of properties.
     *
     * @param path
     * @return loaded properties
     */
    public static Properties getProperties(String path) {
        Properties properties = new Properties();
        InputStream inputStream = null;
        try {
            URL url = PropertyLoad.class.getResource(path);
            inputStream = url.openStream();
            properties.load(inputStream);
            logger.info("Property configuration was successfully downloaded.");
        } catch (IOException e) {
            logger.error("Cannot find property file: " + inputStream, e);
        }
        return properties;
    }
}