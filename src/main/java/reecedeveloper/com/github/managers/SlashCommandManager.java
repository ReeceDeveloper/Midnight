package reecedeveloper.com.github.managers;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.events.GenericEvent;
import net.dv8tion.jda.api.events.interaction.command.CommandAutoCompleteInteractionEvent;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import reecedeveloper.com.github.commands.owner.Presence;
import reecedeveloper.com.github.commands.utility.ComponentTest;
import reecedeveloper.com.github.commands.utility.Ping;
import reecedeveloper.com.github.commands.utility.Uptime;
import reecedeveloper.com.github.interfaces.GenericDiscordEvent;
import reecedeveloper.com.github.interfaces.SlashCommandInteraction;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

public class SlashCommandManager implements GenericDiscordEvent {
    private final Map<String, SlashCommandInteraction> slashCommandMap = new HashMap<>();

    public SlashCommandManager(JDA jdaObject) {
        initSlashCommandMap();

        jdaObject.updateCommands().addCommands(slashCommandMap.values().stream()
                .map(SlashCommandInteraction::getSlashCommandData)
                .collect(Collectors.toList())
        ).queue();
    }

    private void registerSlashCommand(SlashCommandInteraction slashCommandEvent) {
        slashCommandMap.put(slashCommandEvent.getSlashCommandData().getName(), slashCommandEvent);
    }

    private void initSlashCommandMap() {
        registerSlashCommand(new Ping());
        registerSlashCommand(new Uptime());

        registerSlashCommand(new Presence());

        registerSlashCommand(new ComponentTest());
    }

    @Override
    public void handleGenericEvent(GenericEvent genericEvent) {
        // TODO - Split to handle both SlashCommandInteractionEvent and CommandAutoCompleteInteractionEvent.
        if (genericEvent instanceof CommandAutoCompleteInteractionEvent) { // Temporary.
            return;
        }

        SlashCommandInteractionEvent slashCommandInteractionEvent = (SlashCommandInteractionEvent) genericEvent;

        SlashCommandInteraction slashCommandInteraction = slashCommandMap.get(slashCommandInteractionEvent.getName());

        if (Objects.nonNull(slashCommandInteraction)) { // Sanity check.
            slashCommandInteraction.handleSlashCommandInteraction(slashCommandInteractionEvent);
        }
    }
}
