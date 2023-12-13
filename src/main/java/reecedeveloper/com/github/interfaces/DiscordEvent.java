package reecedeveloper.com.github.interfaces;

import net.dv8tion.jda.api.events.GenericEvent;

public interface DiscordEvent {
    void execute(GenericEvent genericDiscordEvent);
}
