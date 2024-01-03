package reecedeveloper.com.github.commands.utility;

import net.dv8tion.jda.api.entities.emoji.Emoji;
import net.dv8tion.jda.api.events.GenericEvent;
import net.dv8tion.jda.api.events.interaction.command.CommandAutoCompleteInteractionEvent;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;
import net.dv8tion.jda.api.events.interaction.component.EntitySelectInteractionEvent;
import net.dv8tion.jda.api.events.interaction.component.StringSelectInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import net.dv8tion.jda.api.interactions.commands.build.SlashCommandData;
import net.dv8tion.jda.api.interactions.components.buttons.Button;
import net.dv8tion.jda.api.interactions.components.selections.EntitySelectMenu;
import net.dv8tion.jda.api.interactions.components.selections.StringSelectMenu;
import reecedeveloper.com.github.embeds.Embeds;
import reecedeveloper.com.github.interfaces.ComponentInteraction;
import reecedeveloper.com.github.interfaces.SlashCommandInteraction;
import reecedeveloper.com.github.utilities.ComponentRecord;

import java.util.List;

public class ComponentTest implements SlashCommandInteraction, ComponentInteraction {
    @Override
    public void handleSlashCommandInteraction(SlashCommandInteractionEvent slashCommandInteractionEvent) {
        slashCommandInteractionEvent.replyEmbeds(Embeds.informationEmbed(
                "This message is longer to take up the whole embed box to align the string selection menu with the rest of the embed (ideally)."
        )).addActionRow(
                Button.primary(getComponentData().get(0).componentIds().getFirst(), "BUTTON")
                        .withEmoji(Emoji.fromUnicode("\uD83D\uDC80"))
        ).addActionRow(
                StringSelectMenu.create(getComponentData().get(1).componentIds().getFirst()).addOption(
                        "A menu option.", "menu-option"
                ).build()
        ).addActionRow(
                EntitySelectMenu.create(getComponentData().get(2).componentIds().getFirst(), EntitySelectMenu.SelectTarget.USER).build()
        ).queue();
    }

    @Override
    public SlashCommandData getSlashCommandData() {
        return Commands.slash("ctest", "Test Discord components.");
    }

    @Override
    public void handleCommandAutoCompleteInteraction(CommandAutoCompleteInteractionEvent commandAutoCompleteInteractionEvent) {
        // Logic: apparently Collections does this... I'm not a huge fan of it, but StackOverflow says it's okay(?).
        // https://stackoverflow.com/questions/10572643/optional-methods-in-java-interface
        throw new UnsupportedOperationException("This command does not use command auto-complete, stop sending events here.");
    }

    @Override
    public void handleComponentInteraction(GenericEvent genericEvent) {
        if (genericEvent instanceof ButtonInteractionEvent buttonInteractionEvent) {
            buttonInteractionEvent.replyEmbeds(Embeds.informationEmbed(
                    buttonInteractionEvent.getUser().getAsMention() + " pressed the button."
            )).queue();

            return;
        }

        if (genericEvent instanceof StringSelectInteractionEvent stringSelectInteractionEvent) {
            stringSelectInteractionEvent.replyEmbeds(Embeds.informationEmbed(
                    stringSelectInteractionEvent.getValues().getFirst() + " was selected."
            )).queue();

            return;
        }

        if (genericEvent instanceof EntitySelectInteractionEvent entitySelectInteractionEvent) {
            entitySelectInteractionEvent.replyEmbeds(Embeds.informationEmbed(
                    entitySelectInteractionEvent.getValues().getFirst().getAsMention() + " was selected."
            )).queue();
        }
    }

    @Override
    public List<ComponentRecord> getComponentData() {
        return List.of(
                ComponentRecord.record(this, List.of(
                "BUTTON_ONE"
                )),
                ComponentRecord.record(this, List.of(
                        "choose-item"
                )),
                ComponentRecord.record(this, List.of(
                        "choose-user"
                ))
        );
    }
}
