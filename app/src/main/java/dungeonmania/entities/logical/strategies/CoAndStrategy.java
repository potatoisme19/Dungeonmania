// dungeonmania/entities/logical/strategies/CoAndStrategy.java
package dungeonmania.entities.logical.strategies;

import dungeonmania.entities.logical.Conductor;
import java.util.List;

public class CoAndStrategy implements LogicStrategy {

    @Override
    public boolean evaluate(List<Conductor> adjacentConductors, int currentTick) {
        if (adjacentConductors.size() < 2) {
            return false;
        }

        boolean allConducting = adjacentConductors.stream()
                .allMatch(Conductor::isConducting);

        if (!allConducting) {
            return false;
        }

        return adjacentConductors.stream()
                .allMatch(conductor -> conductor.getActivatedTick() == currentTick);
    }

    @Override
    public String getLogicType() {
        return "co_and";
    }
}
