package reecedeveloper.com.github.managers;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.events.GenericEvent;
import net.dv8tion.jda.api.events.interaction.component.GenericComponentInteractionCreateEvent;
import reecedeveloper.com.github.commands.utility.ComponentTest;
import reecedeveloper.com.github.interfaces.ComponentInteraction;
import reecedeveloper.com.github.interfaces.GenericDiscordEvent;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ComponentManager implements GenericDiscordEvent {
    public ComponentManager(JDA jdaObject) {
        initializeManagerMaps(jdaObject);
    }

    private void initializeManagerMaps(JDA jdaObject) {
        initializeComponentMap(jdaObject);
    }

    private final Map<String, ComponentInteraction> componentMap = new HashMap<>();

    private void registerComponent(ComponentInteraction invoker) {
        componentMap.put(invoker.getComponentData().getFirst().invoker().getClass().getName(), invoker);
    }

    private void initializeComponentMap(JDA jdaObject) {
        registerComponent(new ComponentTest());
    }

    @Override
    public void handleGenericEvent(GenericEvent genericEvent) {
        if (genericEvent instanceof GenericComponentInteractionCreateEvent genericComponentInteractionCreateEvent) {
            for (Map.Entry<String, ComponentInteraction> componentMapEntry : componentMap.entrySet()) {
                List<String> componentIds = new ArrayList<>();

                for (int idx = 0; idx < componentMapEntry.getValue().getComponentData().size(); ++idx) {
                    componentIds.addAll(componentMapEntry.getValue().getComponentData().get(idx).componentIds());
                }

                for (String componentId : componentIds) {
                    if (genericComponentInteractionCreateEvent.getComponentId().equals(componentId)) {
                        componentMapEntry.getValue().handleComponentInteraction(genericEvent);
                    }
                }
            }
        }
    }
}
