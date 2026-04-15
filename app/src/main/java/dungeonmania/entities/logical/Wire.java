package dungeonmania.entities.logical;

import java.util.ArrayList;
import java.util.List;

import dungeonmania.entities.Entity;
import dungeonmania.map.GameMap;
import dungeonmania.util.Position;

public class Wire extends Entity implements Conductor, ConductingSubject, ConductingObserver {

    private boolean conducting;
    private int activatedTick;
    private List<ConductingObserver> observers;
    private List<Entity> adjacentConductors;
    private List<Entity> adjacentLogicalEntities = new ArrayList<>();

    public Wire(Position position) {
        super(position);
        this.conducting = false;
        this.activatedTick = -1;
        this.observers = new ArrayList<>();
        this.adjacentConductors = new ArrayList<>();
    }

    @Override
    public int getActivatedTick() {
        return activatedTick;
    }

    @Override
    public boolean isConducting() {
        return conducting;
    }

    @Override
    public void setActivatedTick(int tick) {
        this.activatedTick = tick;

    }

    @Override
    public void setConducting(boolean conducting) {
        this.conducting = conducting;

    }

    @Override
    public void addObserver(ConductingObserver observer) {
        if (!observers.contains(observer)) {
            observers.add(observer);
        }

    }

    @Override
    public void notifyObservers(boolean isConducting, int tick) {
        for (ConductingObserver observer : observers) {
            observer.onConductingStateChange(isConducting, tick);
        }

    }

    @Override
    public void removeObserver(ConductingObserver observer) {
        observers.remove(observer);

    }

    @Override
    public boolean canMoveOnto(GameMap map, Entity entity) {
        return true;
    }

    @Override
    public void onConductingStateChange(boolean isConducting, int tick) {
        if (isConducting != this.conducting) {
            this.conducting = isConducting;
            this.activatedTick = tick;
            notifyObservers(isConducting, tick);
        }

    }

    public void updateAdjacentConductors(GameMap map) {
        adjacentConductors.clear();
        List<Position> adjacentPositions = getPosition().getCardinallyAdjacentPositions();

        for (Position pos : adjacentPositions) {
            List<Entity> entities = map.getEntities(pos);
            for (Entity entity : entities) {
                if (entity instanceof Conductor && entity != this) {
                    adjacentConductors.add(entity);
                }
            }
        }
    }

}
