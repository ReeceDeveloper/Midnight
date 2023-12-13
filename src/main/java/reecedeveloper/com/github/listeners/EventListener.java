package reecedeveloper.com.github.listeners;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.events.GenericEvent;
import net.dv8tion.jda.api.events.session.ReadyEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reecedeveloper.com.github.interfaces.DiscordEvent;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class EventListener extends ListenerAdapter {
    private static final Logger LOGGER = LoggerFactory.getLogger(EventListener.class);

    @Override
    public void onReady(ReadyEvent event) {
        LOGGER.info("Midnight has logged in successfully and is now online.");

        // TODO: Temporary - handle presence elsewhere.
        event.getJDA().getPresence().setPresence(OnlineStatus.ONLINE, false);
    }

    private final Map<Class<? extends GenericEvent>, List<DiscordEvent>> discordEvents = new HashMap<>();

    public EventListener(JDA jdaObject) {
        initEventMap(jdaObject);
    }

    private void initEventMap(JDA jdaObject) {
        // TODO: add desired events (i.e., SlashCommandInteractionEvent).
    }

    @Override
    public void onGenericEvent(GenericEvent event) {
        List<DiscordEvent> eventList = discordEvents.get(event.getClass());

        if (Objects.nonNull(eventList)) {
            eventList.forEach(currentEvent -> {
                if (Objects.nonNull(currentEvent)) {
                    currentEvent.execute(event);
                }
            });
        }
    }
}
