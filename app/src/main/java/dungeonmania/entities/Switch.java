package dungeonmania.entities;

import java.util.ArrayList;
import java.util.List;

import dungeonmania.entities.collectables.Bomb;
import dungeonmania.entities.logical.ConductingObserver;
import dungeonmania.entities.logical.ConductingSubject;
import dungeonmania.entities.logical.Conductor;
import dungeonmania.map.GameMap;
import dungeonmania.util.Position;

public class Switch extends Entity implements Overlappable, MoveAway, Conductor, ConductingSubject {
    /** Whether this switch is activated (ie a boulder has been pushed on top of it) */
    private boolean activated;
    private List<Bomb> bombs = new ArrayList<>();
    private boolean conducting;
    private int activatedTick;
    private List<ConductingObserver> observers = new ArrayList<>();

    public Switch(Position position) {
        super(position.asLayer(Entity.ITEM_LAYER));
    }

    /** Subscribe to the state of this switch */
    public void subscribe(Bomb bomb, GameMap map) {
        bombs.add(bomb);
        if (activated) {
            activateBombs(map);
        }
    }

    public void unsubscribe(Bomb b) {
        bombs.remove(b);
    }

    @Override
    public boolean canMoveOnto(GameMap map, Entity entity) {
        return true;
    }

    @Override
    public void onOverlap(GameMap map, Entity entity) {
        if (entity instanceof Boulder) {
            activated = true;
            notifyObservers(true, map.getTick());
            activateBombs(map);
            setConducting(true);
            setActivatedTick(map.getTick());
            map.updateLogicalEntities(map.getTick());
        }
    }

    public void activateBombs(GameMap map) {
        for (Bomb b : bombs) {
            int x = b.getPosition().getX();
            int y = b.getPosition().getY();
            for (int i = x - b.getRadius(); i <= x + b.getRadius(); i++) {
                for (int j = y - b.getRadius(); j <= y + b.getRadius(); j++) {
                    map.destroyEntitiesOnPosition(i, j);
                }
            }
        }
    }

    @Override
    public void onMovedAway(GameMap map, Entity entity) {
        if (entity instanceof Boulder) {
            activated = false;
            setConducting(false);
            notifyObservers(false, map.getTick());
            map.updateLogicalEntities(map.getTick());
        }
    }

    public boolean isActivated() {
        return activated;
    }

    @Override
    public boolean isConducting() {
        return activated;
    }

    @Override
    public void setConducting(boolean conducting) {
        this.conducting = conducting;
    }

    @Override
    public int getActivatedTick() {
        return activatedTick;
    }

    @Override
    public void setActivatedTick(int tick) {
        this.activatedTick = tick;
    }

    @Override
    public void addObserver(ConductingObserver observer) {
        if (!observers.contains(observer)) {
            observers.add(observer);
        }
    }

    @Override
    public void removeObserver(ConductingObserver observer) {
        observers.remove(observer);
    }

    @Override
    public void notifyObservers(boolean isConducting, int tick) {
        for (ConductingObserver observer : observers) {
            observer.onConductingStateChange(isConducting, tick);
        }
    }

}
