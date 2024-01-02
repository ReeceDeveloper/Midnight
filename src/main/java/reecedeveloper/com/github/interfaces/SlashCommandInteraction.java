package reecedeveloper.com.github.interfaces;

import net.dv8tion.jda.api.events.interaction.command.CommandAutoCompleteInteractionEvent;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.build.SlashCommandData;

public interface SlashCommandInteraction {
    void handleSlashCommandInteraction(SlashCommandInteractionEvent slashCommandInteractionEvent);

    SlashCommandData getSlashCommandData();

    void handleCommandAutoCompleteInteraction(CommandAutoCompleteInteractionEvent commandAutoCompleteInteractionEvent);
}
