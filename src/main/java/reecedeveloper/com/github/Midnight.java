package reecedeveloper.com.github;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.exceptions.InvalidTokenException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reecedeveloper.com.github.configuration.Configuration;

import java.time.Duration;
import java.util.Scanner;

public class Midnight {
    private static final Logger LOGGER = LoggerFactory.getLogger(Midnight.class);

    public static void main(String[] args) {
        try {
            JDA jdaObject = JDABuilder.createDefault(
                    Configuration.getConfigInstance("Configuration.json").optString("botToken")
            ).build();

            // Add the EventListener.

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
