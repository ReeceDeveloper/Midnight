package reecedeveloper.com.github.interfaces;

import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.build.SlashCommandData;

public interface DSlashCommandInteractionEvent {
    void handleSlashCommandInteractionEvent(SlashCommandInteractionEvent slashCommandInteractionEvent);

    SlashCommandData getSlashCommandData();
}
