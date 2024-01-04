package reecedeveloper.com.github.commands.utility;

import net.dv8tion.jda.api.events.interaction.ModalInteractionEvent;
import net.dv8tion.jda.api.events.interaction.command.CommandAutoCompleteInteractionEvent;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import net.dv8tion.jda.api.interactions.commands.build.SlashCommandData;
import net.dv8tion.jda.api.interactions.components.ActionRow;
import net.dv8tion.jda.api.interactions.components.text.TextInput;
import net.dv8tion.jda.api.interactions.components.text.TextInputStyle;
import net.dv8tion.jda.api.interactions.modals.Modal;
import reecedeveloper.com.github.embeds.Embeds;
import reecedeveloper.com.github.interfaces.ModalInteraction;
import reecedeveloper.com.github.interfaces.SlashCommandInteraction;
import reecedeveloper.com.github.utilities.ModalRecord;

import java.util.List;

public class ModalTest implements SlashCommandInteraction, ModalInteraction {
    @Override
    public void handleSlashCommandInteraction(SlashCommandInteractionEvent slashCommandInteractionEvent) {
        TextInput subject = TextInput.create("subject", "Subject", TextInputStyle.SHORT)
                .setPlaceholder("Subject of the message.")
                .setMinLength(3)
                .setMaxLength(255)
                .build();

        TextInput body = TextInput.create("body", "Body", TextInputStyle.PARAGRAPH)
                .setPlaceholder("The body of your message.")
                .setMinLength(1)
                .setMaxLength(1000)
                .build();

        Modal modal = Modal.create(getModalData().getFirst().modalId(), "Message")
                .addComponents(ActionRow.of(subject), ActionRow.of(body))
                .build();

        slashCommandInteractionEvent.replyModal(modal).queue();
    }

    @Override
    public SlashCommandData getSlashCommandData() {
        return Commands.slash("mtest", "Test Discord Modals.");
    }

    @Override
    public void handleCommandAutoCompleteInteraction(CommandAutoCompleteInteractionEvent commandAutoCompleteInteractionEvent) {
        // Logic: apparently Collections does this... I'm not a huge fan of it, but StackOverflow says it's okay(?).
        // https://stackoverflow.com/questions/10572643/optional-methods-in-java-interface
        throw new UnsupportedOperationException("This command does not use command auto-complete, stop sending events here.");
    }

    @Override
    public void handleModalInteractionEvent(ModalInteractionEvent modalInteractionEvent) {
        modalInteractionEvent.replyEmbeds(Embeds.informationEmbed(
                "Modal interaction successful."
        )).queue();
    }

    @Override
    public List<ModalRecord> getModalData() {
        return List.of(ModalRecord.record(this, "MODAL_ONE"));
    }
}
