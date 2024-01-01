package reecedeveloper.com.github.interfaces;

import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;
import reecedeveloper.com.github.utilities.ButtonData;

public interface DButtonInteractionEvent {
    void handleButtonInteractionEvent(ButtonInteractionEvent buttonInteractionEvent);

    ButtonData getButtonData();
}

