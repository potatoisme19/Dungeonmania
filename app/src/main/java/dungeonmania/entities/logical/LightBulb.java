package dungeonmania.entities.logical;

import dungeonmania.entities.Entity;
import dungeonmania.entities.logical.strategies.LogicStrategy;
import dungeonmania.entities.logical.strategies.LogicStrategyFactory;
import dungeonmania.map.GameMap;
import dungeonmania.util.Position;
import java.util.List;

public class LightBulb extends Entity implements Logical {
    private boolean activated;
    private LogicStrategy logicStrategy;

    public LightBulb(Position position, String logicType) {
        super(position);
        this.activated = false;
        this.logicStrategy = LogicStrategyFactory.createStrategy(logicType);
    }

    @Override
    public boolean isActivated() {
        return activated;
    }

    @Override
    public void setActivated(boolean activated) {
        this.activated = activated;
    }

    @Override
    public LogicStrategy getLogicStrategy() {
        return logicStrategy;
    }

    @Override
    public void updateActivation(List<Conductor> adjacentConductors, int currentTick) {
        boolean shouldBeActivated = logicStrategy.evaluate(adjacentConductors, currentTick);
        setActivated(shouldBeActivated);
    }

    @Override
    public boolean canMoveOnto(GameMap map, Entity entity) {
        return true;
    }
}
