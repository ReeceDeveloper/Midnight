package reecedeveloper.com.github.listeners;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.events.GenericEvent;
import net.dv8tion.jda.api.events.interaction.command.CommandAutoCompleteInteractionEvent;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import reecedeveloper.com.github.interfaces.DGenericEvent;
import reecedeveloper.com.github.managers.SlashCommandManager;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EventListener extends ListenerAdapter {
    public EventListener(JDA jdaObject) {
        initializeManagerMap(jdaObject);
    }

    // Hash map containing a list of event classes (key) related to an implementation of DGenericEvent (value).
    private final Map<List<Class<? extends GenericEvent>>, DGenericEvent> eventManagerMap = new HashMap<>();

    private void initializeManagerMap(JDA jdaObject) {
        // Slash commands.
        List<Class<? extends GenericEvent>> slashCommandEventList = Arrays.asList(
                SlashCommandInteractionEvent.class,
                CommandAutoCompleteInteractionEvent.class
        );

        eventManagerMap.put(slashCommandEventList, new SlashCommandManager(jdaObject));

        // Other events and their managers.
    }

    @Override
    public void onGenericEvent(GenericEvent genericEvent) {
        // TODO - if handling many events, this loop may be unoptimized and need performance-based revisions.

        // Check (read: for) every even manager contained within the event manager map.
        for (Map.Entry<List<Class<? extends GenericEvent>>, DGenericEvent> eventManager : eventManagerMap.entrySet()) {
            // Get a list of all event classes handled by the current event manager.
            List<Class<? extends GenericEvent>> eventClasses = eventManager.getKey();

            // If the received event is an instanceof any of the managed classes...
            if (eventClasses.stream().anyMatch(eventClass -> eventClass.isInstance(genericEvent))) {
                // Pass the event to its respective event manager for handling.
                eventManager.getValue().handleGenericEvent(genericEvent);
            }

            // Otherwise, continue the loop.
        }
    }
}
