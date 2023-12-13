package reecedeveloper.com.github.interfaces;

import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.build.SlashCommandData;

public interface SlashCommandEvent {
    void execute(SlashCommandInteractionEvent slashCommandEvent);

    SlashCommandData getSlashCommandData();
}
