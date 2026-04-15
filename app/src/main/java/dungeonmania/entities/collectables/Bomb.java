package dungeonmania.entities.collectables;

import dungeonmania.util.Position;

import java.util.List;

import dungeonmania.entities.Entity;
import dungeonmania.entities.Overlappable;
import dungeonmania.entities.Player;
import dungeonmania.entities.Switch;
import dungeonmania.entities.inventory.InventoryItem;
import dungeonmania.entities.logical.Conductor;
import dungeonmania.entities.logical.Logical;
import dungeonmania.entities.logical.strategies.LogicStrategy;
import dungeonmania.entities.logical.strategies.LogicStrategyFactory;
import dungeonmania.map.GameMap;

public class Bomb extends InventoryItem implements Overlappable, Logical {

    private boolean isLogical;
    private String logicType;
    private LogicStrategy logicStrategy;
    private boolean activated;
    private boolean hasExploded;
    public static final int DEFAULT_RADIUS = 1;
    private State state;
    private int radius;
    private transient GameMap gameMap;

    public enum State {
        SPAWNED, PLACED
    }

    public Bomb(Position position, int radius) {
        super(position);
        state = State.SPAWNED;
        this.radius = radius;
        this.isLogical = false;
        this.hasExploded = false;
    }

    public Bomb(Position position, String logicType, int radius) {
        super(position);
        this.logicType = logicType;
        this.radius = radius;
        this.isLogical = true;
        this.logicStrategy = LogicStrategyFactory.createStrategy(logicType);
        this.activated = false;
        this.hasExploded = false;
    }

    public boolean isLogical() {
        return isLogical;
    }

    public LogicStrategy getLogicStrategy() {
        return logicStrategy;
    }

    public boolean isActivated() {
        return activated;
    }

    public void setActivated(boolean activated) {
        this.activated = activated;
    }

    public void setGameMap(GameMap gameMap) {
        this.gameMap = gameMap;
    }

    @Override
    public void onOverlap(GameMap map, Entity entity) {
        if (state != State.SPAWNED)
            return;
        if (entity instanceof Player player) {
            if (!player.pickUp(this))
                return;
            map.destroyEntity(this);
        }
    }

    public int getRadius() {
        return radius;
    }

    public void onPutDown(GameMap map, Position p) {
        translate(Position.calculatePositionBetween(getPosition(), p));
        map.addEntity(this);
        this.state = State.PLACED;
        List<Position> adjPosList = getPosition().getCardinallyAdjacentPositions();
        adjPosList.stream().forEach(node -> {
            List<Entity> entities = map.getEntities(node).stream().filter(Switch.class::isInstance).toList();
            entities.stream().map(Switch.class::cast).forEach(s -> s.subscribe(this, map));
        });
    }

    @Override
    public void updateActivation(List<Conductor> adjacentConductors, int currentTick) {
        if (!isLogical || hasExploded)
            return;
        boolean shouldBeActivated = logicStrategy.evaluate(adjacentConductors, currentTick);
        if (shouldBeActivated && !activated) {
            activated = true;
            explode();
        }
    }

    private void explode() {
        if (hasExploded)
            return;
        hasExploded = true;

        if (gameMap == null)
            return;

        Position pos = getPosition();
        for (int x = pos.getX() - radius; x <= pos.getX() + radius; x++) {
            for (int y = pos.getY() - radius; y <= pos.getY() + radius; y++) {
                gameMap.destroyEntitiesOnPosition(x, y);
            }
        }
        gameMap.destroyEntity(this);
    }

    public State getState() {
        return state;
    }
}
