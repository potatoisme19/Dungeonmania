package dungeonmania.entities.logical;

import dungeonmania.entities.Entity;
import dungeonmania.entities.logical.strategies.LogicStrategy;
import dungeonmania.entities.logical.strategies.LogicStrategyFactory;
import dungeonmania.map.GameMap;
import dungeonmania.util.Position;
import java.util.List;

public class SwitchDoor extends Entity implements Logical {
    private boolean activated;
    private boolean open;
    private LogicStrategy logicStrategy;

    public SwitchDoor(Position position, String logicType) {
        super(position);
        this.activated = false;
        this.open = false;
        this.logicStrategy = LogicStrategyFactory.createStrategy(logicType);
    }

    @Override
    public boolean isActivated() {
        return activated;
    }

    @Override
    public void setActivated(boolean activated) {
        this.activated = activated;
        this.open = activated;
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
        return open;
    }

    public boolean isOpen() {
        return open;
    }
}
