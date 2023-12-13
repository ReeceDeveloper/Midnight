package reecedeveloper.com.github.embeds;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;

import java.awt.*;

public class Embeds {
    public static final Color INFORMATION_COLOR = Color.decode("#004C4C");
    public static final Color WARNING_COLOR = Color.decode("#999900");
    public static final Color ERROR_COLOR = Color.decode("#990000");

    private static MessageEmbed createEmbed(String message, Color color) {
        return new EmbedBuilder()
                .setDescription(message)
                .setColor(color)
                .build();
    }

    public static MessageEmbed informationEmbed(String informationMessage) {
        return createEmbed(informationMessage, INFORMATION_COLOR);
    }

    public static MessageEmbed warningEmbed(String warningMessage) {
        return createEmbed(warningMessage, WARNING_COLOR);
    }

    public static MessageEmbed errorEmbed(String errorMessage) {
        return createEmbed(errorMessage, ERROR_COLOR);
    }
}
