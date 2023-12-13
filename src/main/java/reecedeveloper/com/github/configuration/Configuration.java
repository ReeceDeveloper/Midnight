package reecedeveloper.com.github.configuration;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class Configuration extends JSONObject {
    private static final Logger LOGGER = LoggerFactory.getLogger(Configuration.class);

    private static Configuration configInstance = null;

    public Configuration(File jsonConfig) throws IOException {
        super(new String(Files.readAllBytes(jsonConfig.toPath())));

        configInstance = this;
    }

    // TODO: Change the name of this, better yet, use better code logic.
    public static Configuration getConfigInstance(String filePath) {
        if (configInstance == null) {
            try {
                configInstance = new Configuration(new File(filePath));
            } catch (IOException configLoadException) {
                LOGGER.error("Unable to load the configuration file.");
            }
        }

        return configInstance;
    }

    public static Configuration getConfigInstance() {
        return configInstance;
    }
}
