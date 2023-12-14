/*
 * The MIT License (MIT)
 *
 * Copyright © 2023 - ReeceDeveloper
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated
 * documentation files (the "Software"), to deal in the Software without restriction, including without limitation
 * the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and
 * to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of
 * the Software.

 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO
 * THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF
 * CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER
 * DEALINGS IN THE SOFTWARE.
 */

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
