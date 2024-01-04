package reecedeveloper.com.github.utilities;

import reecedeveloper.com.github.interfaces.ModalInteraction;

public record ModalRecord(ModalInteraction invoker, String modalId) {
    public static ModalRecord record(ModalInteraction invoker, String modalId) {
        return new ModalRecord(invoker, modalId);
    }
}
