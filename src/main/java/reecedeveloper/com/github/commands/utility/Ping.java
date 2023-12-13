package reecedeveloper.com.github.commands.utility;

import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import net.dv8tion.jda.api.interactions.commands.build.SlashCommandData;
import reecedeveloper.com.github.embeds.Embeds;
import reecedeveloper.com.github.interfaces.SlashCommandEvent;

public class Ping implements SlashCommandEvent {
    @Override
    public void execute(SlashCommandInteractionEvent slashCommandEvent) {
        long gatewayPing = slashCommandEvent.getJDA().getGatewayPing();

        slashCommandEvent.getJDA().getRestPing().queue(restResponsePing -> {
            slashCommandEvent.replyEmbeds(Embeds.informationEmbed(String.format(
                    "Gateway ping is **%d** milliseconds, and the REST response was **%d** milliseconds.", gatewayPing, restResponsePing
            ))).setEphemeral(true).queue();
        });
    }

    @Override
    public SlashCommandData getSlashCommandData() {
        return Commands.slash("ping", "Calculate the gateway and REST response ping.");
    }
}
