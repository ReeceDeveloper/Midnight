package reecedeveloper.com.github.interfaces;

import net.dv8tion.jda.api.events.interaction.ModalInteractionEvent;
import reecedeveloper.com.github.utilities.ModalRecord;

import java.util.List;

public interface ModalInteraction {
    void handleModalInteractionEvent(ModalInteractionEvent modalInteractionEvent);

    List<ModalRecord> getModalData();
}
