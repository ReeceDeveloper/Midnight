package reecedeveloper.com.github.interfaces;

import net.dv8tion.jda.api.events.GenericEvent;
import reecedeveloper.com.github.utilities.ComponentRecord;

import java.util.List;

public interface ComponentInteraction {
    void handleComponentInteraction(GenericEvent genericEvent);

    List<ComponentRecord> getComponentData();
}
