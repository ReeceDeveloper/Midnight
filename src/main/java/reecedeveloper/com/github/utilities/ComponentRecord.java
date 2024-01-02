package reecedeveloper.com.github.utilities;

import java.util.List;

public record ComponentRecord<T>(String componentName, T componentInteractionEvent, List<String> componentIds) {
    public static <E> ComponentRecord<E> data(String componentName, E componentInteractionEvent, List<String> componentIds) {
        return new ComponentRecord<>(componentName, componentInteractionEvent, componentIds);
    }
}
