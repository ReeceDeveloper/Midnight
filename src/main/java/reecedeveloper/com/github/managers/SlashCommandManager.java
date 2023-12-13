package reecedeveloper.com.github.managers;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.events.GenericEvent;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import reecedeveloper.com.github.commands.Ping;
import reecedeveloper.com.github.interfaces.DiscordEvent;
import reecedeveloper.com.github.interfaces.SlashCommandEvent;

import java.util.HashMap;
import java.util.Objects;
import java.util.stream.Collectors;

public class SlashCommandManager implements DiscordEvent {
    private final HashMap<String, SlashCommandEvent> slashCommandMap = new HashMap<>();

    public SlashCommandManager(JDA jdaObject) {
        initCommandMap();

        jdaObject.updateCommands().addCommands(
                slashCommandMap.values().stream()
                        .map(SlashCommandEvent::getSlashCommandData)
                        .collect(Collectors.toList())
        ).queue();
    }

    private void registerCommand(SlashCommandEvent slashCommandEvent) {
        slashCommandMap.put(slashCommandEvent.getSlashCommandData().getName(), slashCommandEvent);
    }

    private void initCommandMap() {
        registerCommand(new Ping());
    }

    @Override
    public void execute(GenericEvent genericDiscordEvent) {
        SlashCommandInteractionEvent slashCommandInteractionEvent = (SlashCommandInteractionEvent) genericDiscordEvent;

        SlashCommandEvent slashCommandEvent = slashCommandMap.get(slashCommandInteractionEvent.getName());

        if (Objects.nonNull(slashCommandEvent)) { // Sanity check.
            slashCommandEvent.execute(slashCommandInteractionEvent);
        }
    }
}
