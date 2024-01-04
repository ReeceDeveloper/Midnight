package reecedeveloper.com.github.managers;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.events.GenericEvent;
import net.dv8tion.jda.api.events.interaction.ModalInteractionEvent;
import reecedeveloper.com.github.commands.utility.ModalTest;
import reecedeveloper.com.github.interfaces.GenericDiscordEvent;
import reecedeveloper.com.github.interfaces.ModalInteraction;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ModalManager implements GenericDiscordEvent {
    private final Map<String, ModalInteraction> modalMap = new HashMap<>();

    public ModalManager(JDA jdaObject) {
        initializeModalMap(jdaObject);
    }

    void registerModal(ModalInteraction modalInteraction) {
        modalMap.put(modalInteraction.getModalData().getFirst().invoker().getClass().getName(), modalInteraction);
    }

    void initializeModalMap(JDA jdaObject) {
        registerModal(new ModalTest());
    }

    @Override
    public void handleGenericEvent(GenericEvent genericEvent) {
        ModalInteractionEvent modalInteractionEvent = (ModalInteractionEvent) genericEvent;

        for (Map.Entry<String, ModalInteraction> modalEntry : modalMap.entrySet()) {
            List<String> modalIds = new ArrayList<>();

            for (int idx = 0; idx < modalEntry.getValue().getModalData().size(); ++idx) {
                modalIds.add(modalEntry.getValue().getModalData().get(idx).modalId());
            }

            for (String modalId : modalIds) {
                if (modalInteractionEvent.getModalId().equals(modalId)) {
                    modalEntry.getValue().handleModalInteractionEvent(modalInteractionEvent);
                }
            }
        }
    }
}
