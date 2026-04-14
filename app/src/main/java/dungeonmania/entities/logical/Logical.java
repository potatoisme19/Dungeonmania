package dungeonmania.entities.logical;

import java.util.List;

import dungeonmania.entities.logical.strategies.LogicStrategy;

public interface Logical {
    boolean isActivated();

    void setActivated(boolean activated);

    LogicStrategy getLogicStrategy();

    void updateActivation(List<Conductor> adjacentConductors, int currentTick);
}
