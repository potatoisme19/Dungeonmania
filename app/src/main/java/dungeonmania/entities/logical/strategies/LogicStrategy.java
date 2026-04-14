package dungeonmania.entities.logical.strategies;

import java.util.List;

import dungeonmania.entities.logical.Conductor;

public interface LogicStrategy {
    boolean evaluate(List<Conductor> adjacentConductors, int currentTick);

    String getLogicType();
}
