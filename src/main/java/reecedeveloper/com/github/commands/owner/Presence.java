/*
 * The MIT License (MIT)
 *
 * Copyright © 2023 - ReeceDeveloper
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated
 * documentation files (the "Software"), to deal in the Software without restriction, including without limitation
 * the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and
 * to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of
 * the Software.

 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO
 * THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF
 * CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER
 * DEALINGS IN THE SOFTWARE.
 */

package reecedeveloper.com.github.commands.owner;

import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;
import net.dv8tion.jda.api.interactions.commands.build.SlashCommandData;
import reecedeveloper.com.github.configuration.Configuration;
import reecedeveloper.com.github.embeds.Embeds;
import reecedeveloper.com.github.interfaces.DSlashCommandInteractionEvent;

import java.util.Objects;

// TODO: This can be done much better, but for Revision 1, it's okay(-ish).

public class Presence implements DSlashCommandInteractionEvent {
    @Override
    public void handleSlashCommandInteractionEvent(SlashCommandInteractionEvent slashCommandInteractionEvent) {
        long ownerId = Configuration.getConfigInstance().optLong("ownerId");
        long invokerUserId = slashCommandInteractionEvent.getUser().getIdLong();

        if (invokerUserId != ownerId) {
            slashCommandInteractionEvent.replyEmbeds(Embeds.errorEmbed(
                    "Insufficient permissions."
            )).queue();

            return;
        }

        String presenceOption = Objects.requireNonNull(slashCommandInteractionEvent.getOption("presence")).getAsString();
        String activityOption = Objects.requireNonNull(slashCommandInteractionEvent.getOption("activity")).getAsString();
        String statusOption = "";

        try {
            statusOption = Objects.requireNonNull(slashCommandInteractionEvent.getOption("status")).getAsString();
        } catch (NullPointerException ignored) {}

        OnlineStatus onlineStatus = getOnlineStatus(presenceOption);

        if (statusOption.isEmpty()) {
            if (!activityOption.equals("customStatus")) {
                slashCommandInteractionEvent.replyEmbeds(Embeds.warningEmbed(
                        "Midnight's presence has been updated, though no status for the activity was provided."
                )).queue();
            }

            slashCommandInteractionEvent.getJDA().getPresence().setStatus(onlineStatus);

            return;
        }

        Activity activity = getActivity(activityOption, statusOption);

        if (onlineStatus != null && activity != null) {
            slashCommandInteractionEvent.getJDA().getPresence().setPresence(onlineStatus, activity);

            slashCommandInteractionEvent.replyEmbeds(Embeds.informationEmbed(
                    "Midnight's status has been updated."
            )).queue();
        } else {
            slashCommandInteractionEvent.replyEmbeds(Embeds.errorEmbed(
                    "Invalid status or activity."
            )).queue();
        }
    }

    @Override
    public SlashCommandData getSlashCommandData() {
        return Commands.slash("presence", "Update Midnight's current presence (activity).")
                .addOptions(
                        new OptionData(OptionType.STRING, "presence", "The presence of Midnight.")
                                .addChoice("online", "ONLINE")
                                .addChoice("idle", "IDLE")
                                .addChoice("do-not-disturb", "DO_NOT_DISTURB")
                                .addChoice("invisible", "INVISIBLE")
                                .setRequired(true)
                ).addOptions(
                        new OptionData(OptionType.STRING, "activity", "The activity Midnight is performing.")
                                .addChoice("playing", "playing")
                                .addChoice("watching", "watching")
                                .addChoice("listening", "listening")
                                .addChoice("streaming", "streaming")
                                .addChoice("competing", "competing")
                                .addChoice("none", "customStatus")
                                .setRequired(true)
                ).addOption(OptionType.STRING, "status", "The status message of Midnight.", false);
    }

    private OnlineStatus getOnlineStatus(String presenceOption) {
        try { // Sanity check.
           return OnlineStatus.valueOf(presenceOption);
        } catch (IllegalArgumentException | NullPointerException invalidOnlineStatus) {
            return null;
        }
    }

    private Activity getActivity(String activityOption, String statusOption) {
        try { // Sanity check.
            return switch (activityOption) {
                case "playing" -> Activity.playing(statusOption);
                case "watching" -> Activity.watching(statusOption);
                case "listening" -> Activity.listening(statusOption);
                case "streaming" -> Activity.streaming(statusOption, "");
                case "competing" -> Activity.competing(statusOption);
                case "customStatus" -> Activity.customStatus(statusOption);
                default -> null;
            };
        } catch (IllegalArgumentException | NullPointerException invalidActivity) {
            return null;
        }
    }
}
