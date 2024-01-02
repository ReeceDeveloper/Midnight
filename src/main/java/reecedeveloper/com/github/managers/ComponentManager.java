package reecedeveloper.com.github.managers;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.events.GenericEvent;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;
import reecedeveloper.com.github.interfaces.GenericDiscordEvent;
import reecedeveloper.com.github.utilities.ComponentRecord;

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

    private final Map<String, ComponentRecord<Class<?>>> componentMap = new HashMap<>();

    private void registerComponent(ComponentRecord<Class<?>> componentData) {
        componentMap.put(componentData.componentName(), componentData);
    }

    private void initializeComponentMap(JDA jdaObject) {

    }

    @Override
    public void handleGenericEvent(GenericEvent genericEvent) {
        for (Map.Entry<String, ComponentRecord<Class<?>>> componentRecord : componentMap.entrySet()) {
            List<String> componentIds = componentRecord.getValue().componentIds();

            for (String componentId : componentIds) {
                if (genericEvent instanceof ButtonInteractionEvent buttonInteractionEvent) {
                    if (componentId.equals(buttonInteractionEvent.getComponentId())) {

                    }
                }
            }
        }
    }
}
