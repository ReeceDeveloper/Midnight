package reecedeveloper.com.github.commands.testing;

import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import net.dv8tion.jda.api.interactions.commands.build.SlashCommandData;
import net.dv8tion.jda.api.interactions.components.buttons.Button;
import reecedeveloper.com.github.embeds.Embeds;
import reecedeveloper.com.github.interfaces.DButtonInteractionEvent;
import reecedeveloper.com.github.interfaces.DSlashCommandInteractionEvent;
import reecedeveloper.com.github.utilities.ButtonData;

import java.util.List;

// TODO - Remove before publishing a release.
public class ButtonTest implements DSlashCommandInteractionEvent, DButtonInteractionEvent {
    @Override
    public void handleSlashCommandInteractionEvent(SlashCommandInteractionEvent slashCommandInteractionEvent) {
        slashCommandInteractionEvent.replyEmbeds(Embeds.informationEmbed(
                "You have summoned the button!"
        )).addActionRow(
                Button.primary(getButtonData().getButtonIdStrings().get(0), "BUTTON"),
                Button.danger(getButtonData().getButtonIdStrings().get(1), "DELETE")
        ).queue();
    }

    @Override
    public SlashCommandData getSlashCommandData() {
        return Commands.slash("button", "Test the button.");
    }

    @Override
    public void handleButtonInteractionEvent(ButtonInteractionEvent buttonInteractionEvent) {
        if (buttonInteractionEvent.getComponentId().equals(getButtonData().getButtonIdStrings().get(1))) {
            buttonInteractionEvent.editMessageEmbeds(Embeds.informationEmbed(
                    buttonInteractionEvent.getUser().getAsMention() + " deleted the button!"
            )).setComponents().queue();
        } else {
            buttonInteractionEvent.replyEmbeds(Embeds.informationEmbed(
                    buttonInteractionEvent.getUser().getAsMention() + " pressed the button!"
            )).queue();
        }
    }

    @Override
    public ButtonData getButtonData() {
        return ButtonData.data(this, List.of(
                "BUTTON_ONE", "BUTTON_TWO"
        ));
    }
}
