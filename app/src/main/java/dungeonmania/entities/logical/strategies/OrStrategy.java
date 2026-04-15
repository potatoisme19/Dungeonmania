package dungeonmania.entities.logical.strategies;

import java.util.List;

import dungeonmania.entities.logical.Conductor;

public class OrStrategy implements LogicStrategy {

    @Override
    public boolean evaluate(List<Conductor> adjacentConductors, int currentTick) {
        boolean result = adjacentConductors.stream().anyMatch(Conductor::isConducting);
        return result;
    }

    @Override
    public String getLogicType() {
        return "or";
    }

}
