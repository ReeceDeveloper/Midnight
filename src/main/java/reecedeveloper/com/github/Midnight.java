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

package reecedeveloper.com.github;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.exceptions.InvalidTokenException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reecedeveloper.com.github.configuration.Configuration;
import reecedeveloper.com.github.listeners.EventListener;

import java.time.Duration;
import java.util.Scanner;

public class Midnight {
    private static final Logger LOGGER = LoggerFactory.getLogger(Midnight.class);

    public static void main(String[] args) {
        try {
            JDA jdaObject = JDABuilder.createDefault(
                    Configuration.getConfigInstance("Configuration.json").optString("botToken")
            ).build();

            jdaObject.addEventListener(new EventListener(jdaObject));

            Scanner terminalScanner = new Scanner(System.in);

            while (terminalScanner.hasNextLine()) {
                String terminalCommand = terminalScanner.nextLine();

                if (terminalCommand.equalsIgnoreCase("shutdown")) {
                    LOGGER.info("Midnight will now initiate shutdown procedures.");

                    jdaObject.shutdown();

                    if (!jdaObject.awaitShutdown(Duration.ofMinutes(5))) {
                        LOGGER.warn("JDA took more than 5 minutes to shut down gracefully, forcing shutdown.");

                        jdaObject.shutdownNow();

                        if (!jdaObject.awaitShutdown(Duration.ofMinutes(5))) {
                            LOGGER.error("JDA took more than 5 minutes to forcibly shut down. Killing process.");

                            System.exit(-1); // TODO: Perhaps setup a list of hex codes that indicate shutdown status?
                        }
                    }

                    System.exit(0); // TODO: See previous TODO.
                }
            }
        } catch (InvalidTokenException invalidTokenException) {
            LOGGER.error("JDA initialization failed, invalid token.");

            System.exit(-1); // TODO: See previous TODO.
        } catch (InterruptedException threadInterruptedException) {
            LOGGER.error("JDA thread interrupted during shutdown!");

            System.exit(-1); // TODO: See previous TODO (not technically a failure, but non-graceful).
        }
    }
}
