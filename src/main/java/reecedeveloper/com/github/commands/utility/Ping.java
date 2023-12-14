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
