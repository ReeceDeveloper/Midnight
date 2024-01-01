package reecedeveloper.com.github.managers;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.events.GenericEvent;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;
import net.dv8tion.jda.api.events.interaction.component.EntitySelectInteractionEvent;
import net.dv8tion.jda.api.events.interaction.component.StringSelectInteractionEvent;
import reecedeveloper.com.github.commands.testing.ButtonTest;
import reecedeveloper.com.github.interfaces.DGenericEvent;
import reecedeveloper.com.github.utilities.ButtonData;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ComponentManager implements DGenericEvent {
    public ComponentManager(JDA jdaObject) {
        initializeManagerMaps(jdaObject);
    }

    private void initializeManagerMaps(JDA jdaObject) {
        initializeButtonManagerMap();
    }

    // TODO - I really dislike the structuring of this whole class/system. This must be done better.

    private final Map<String, ButtonData> buttonDataMap = new HashMap<>();

    private void registerButton(String name, ButtonData buttonData) {
        buttonDataMap.put(name, buttonData);
    }

    private void initializeButtonManagerMap() {
        registerButton("ButtonTest", new ButtonTest().getButtonData());
    }

    private void validateButtonInteractionEvent(ButtonInteractionEvent buttonInteractionEvent) {
        for (Map.Entry<String, ButtonData> buttonData : buttonDataMap.entrySet()) {
            List<String> buttonIdStrings = buttonData.getValue().getButtonIdStrings();

            for (String buttonIdString : buttonIdStrings) {
                if (buttonInteractionEvent.getComponentId().equals(buttonIdString)) {
                    buttonData.getValue().getButtonInteractionEvent().handleButtonInteractionEvent(buttonInteractionEvent);
                }
            }
        }
    }

    private void validateStringSelectInteractionEvent(StringSelectInteractionEvent stringSelectInteractionEvent) {
        // Code.
    }

    private void validateEntitySelectInteractionEvent(EntitySelectInteractionEvent entitySelectInteractionEvent) {
        // Code.
    }

    @Override
    public void handleGenericEvent(GenericEvent genericEvent) {
        if (genericEvent instanceof ButtonInteractionEvent) {
            validateButtonInteractionEvent((ButtonInteractionEvent) genericEvent); return;
        }

        if (genericEvent instanceof StringSelectInteractionEvent) {
            validateStringSelectInteractionEvent((StringSelectInteractionEvent) genericEvent); return;
        }

        if (genericEvent instanceof EntitySelectInteractionEvent) {
            validateEntitySelectInteractionEvent((EntitySelectInteractionEvent) genericEvent);
        }
    }
}
