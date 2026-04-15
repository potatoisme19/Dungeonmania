package dungeonmania.entities.logical;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import dungeonmania.entities.Entity;
import dungeonmania.entities.Switch;
import dungeonmania.entities.collectables.Bomb;
import dungeonmania.map.GameMap;
import dungeonmania.util.Position;

/**
 * Class representing circuit logic to map in the game
 * it is created so that GameMap will not involve initializing the observer network
 */
public class LogicalCircuitManager {
    private GameMap map;

    public LogicalCircuitManager(GameMap map) {
        this.map = map;
    }

    // private List<Entity> getEntities() {
    //     return map.getEntities();
    // }

    private <T extends Entity> List<T> getEntities(Class<T> type) {
        return map.getEntities(type);
    }

    private List<Entity> getEntities(Position pos) {
        return map.getEntities(pos);
    }

    public List<Entity> getAdjacentConductors(Entity entity) {
        List<Entity> result = new ArrayList<>();
        for (Position pos : entity.getPosition().getCardinallyAdjacentPositions()) {
            for (Entity e : getEntities(pos)) {
                if (e instanceof Conductor && e != entity) {
                    result.add(e);
                }
            }
        }
        return result;
    }

    public List<Entity> getAdjacentLogicalEntities(Entity entity) {
        List<Entity> result = new ArrayList<>();
        for (Position pos : entity.getPosition().getCardinallyAdjacentPositions()) {
            for (Entity e : getEntities(pos)) {
                if (e instanceof Logical && e != entity) {
                    result.add(e);
                }
            }
        }
        return result;
    }

    public void updateLogicalEntities(int currentTick) {
        getAllLogicalEntities().stream()
                .filter(logical -> logical instanceof Entity)
                .forEach(logical -> {
                    Entity entity = (Entity) logical;
                    List<Conductor> adjacentConductors = getAdjacentConductors(entity).stream()
                            .filter(adjacent -> adjacent instanceof Conductor)
                            .map(adjacent -> (Conductor) adjacent)
                            .collect(Collectors.toList());
                    logical.updateActivation(adjacentConductors, currentTick);
                });
    }

    private List<Logical> getAllLogicalEntities() {
        List<Logical> logicalEntities = new ArrayList<>();
        logicalEntities.addAll(getEntities(LightBulb.class));
        logicalEntities.addAll(getEntities(SwitchDoor.class));

        for (Bomb bomb : getEntities(Bomb.class)) {
            if (bomb.isLogical()) {
                logicalEntities.add(bomb);
            }
        }
        return logicalEntities;
    }

    /**
     * Get all conductors (wires and switches)
     * register wires to observe adjacent conductors
     *      update wire's list of adjacent conductors
     *      wire observes all adjacent conductors
     * Register logical entities to observe adjacent conductors
     */
    public void initObserverNetwork() {
        List<Conductor> allConductors = new ArrayList<>();
        allConductors.addAll(getEntities(Wire.class));
        allConductors.addAll(getEntities(Switch.class));

        for (Conductor conductor : allConductors) {
            if (conductor instanceof Wire wire) {
                wire.updateAdjacentConductors(map);

                for (Entity adjacent : getAdjacentConductors(wire)) {
                    if (adjacent instanceof ConductingSubject) {
                        ((ConductingSubject) adjacent).addObserver(wire);
                    }
                }
            }
        }
    }
}
