/*
 * The MIT License (MIT)
 *
 * Copyright © 2023 - ReeceDeveloper
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated
 * documentation files (the “Software”), to deal in the Software without restriction, including without limitation
 * the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and
 * to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of
 * the Software.

 * THE SOFTWARE IS PROVIDED “AS IS”, WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO
 * THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF
 * CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER
 * DEALINGS IN THE SOFTWARE.
 */

package reecedeveloper.com.github.listeners;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.events.GenericEvent;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.events.session.ReadyEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reecedeveloper.com.github.interfaces.DiscordEvent;
import reecedeveloper.com.github.managers.SlashCommandManager;

import java.util.*;

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
        discordEvents.put(SlashCommandInteractionEvent.class, Collections.singletonList(new SlashCommandManager(jdaObject)));
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
