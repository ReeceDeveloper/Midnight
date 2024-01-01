package reecedeveloper.com.github.utilities;

import reecedeveloper.com.github.interfaces.DButtonInteractionEvent;

import java.util.List;

public class ButtonData {
    private DButtonInteractionEvent dButtonInteractionEvent;
    private List<String> buttonIdStrings;

    public ButtonData(DButtonInteractionEvent dButtonInteractionEvent, List<String> buttonIdStrings) {
        this.setButtonInteractionEvent(dButtonInteractionEvent);
        this.setButtonIdStrings(buttonIdStrings);
    }

    public static ButtonData data(DButtonInteractionEvent dButtonInteractionEvent, List<String> buttonIdStrings) {
        return new ButtonData(dButtonInteractionEvent, buttonIdStrings);
    }

    public DButtonInteractionEvent getButtonInteractionEvent() {
        return dButtonInteractionEvent;
    }

    public List<String> getButtonIdStrings() {
        return buttonIdStrings;
    }

    private void setButtonInteractionEvent(DButtonInteractionEvent dButtonInteractionEvent) {
        this.dButtonInteractionEvent = dButtonInteractionEvent;
    }

    private void setButtonIdStrings(List<String> buttonIdStrings) {
        this.buttonIdStrings = buttonIdStrings;
    }
}
