package dungeonmania.entities.logical.strategies;

import java.util.List;

import dungeonmania.entities.logical.Conductor;

public class AndStrategy implements LogicStrategy {

    @Override
    public boolean evaluate(List<Conductor> adjacentConductors, int currentTick) {
        if (adjacentConductors.size() < 2) {
            return false;
        }
        return adjacentConductors
                .stream()
                .allMatch(Conductor::isConducting);
    }

    @Override
    public String getLogicType() {
        return "and";
    }

}
