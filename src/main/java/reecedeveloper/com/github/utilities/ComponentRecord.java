package reecedeveloper.com.github.utilities;

import reecedeveloper.com.github.interfaces.ComponentInteraction;

import java.util.List;

public record ComponentRecord(ComponentInteraction invoker, List<String> componentIds) {
    public static ComponentRecord record(ComponentInteraction invoker, List<String> componentIds) {
        return new ComponentRecord(invoker, componentIds);
    }
}
