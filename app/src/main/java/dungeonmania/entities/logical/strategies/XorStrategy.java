package dungeonmania.entities.logical.strategies;

import java.util.List;

import dungeonmania.entities.logical.Conductor;

public class XorStrategy implements LogicStrategy {

    @Override
    public boolean evaluate(List<Conductor> adjacentConductors, int currentTick) {
        long conductingCount = adjacentConductors
                .stream()
                .filter(Conductor::isConducting)
                .count();
        return conductingCount == 1;
    }

    @Override
    public String getLogicType() {
        return "xor";
    }

}
