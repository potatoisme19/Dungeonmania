package dungeonmania.entities.logical;

import dungeonmania.util.Position;
import java.util.List;

public interface Conductor {
    boolean isConducting();

    void setConducting(boolean conducting);

    int getActivatedTick();

    void setActivatedTick(int tick);

    List<Position> getAdjacentConductors();

    List<Position> getAdjacentLogicalEntities();
}
