package dungeonmania.entities.logical;

import java.util.List;

import dungeonmania.entities.Entity;
import dungeonmania.util.Position;

public class Wire extends Entity implements Conductor, ConductingSubject, ConductingObserver {

    public Wire(Position position) {
        super(position);
    }

    @Override
    public int getActivatedTick() {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public List<Position> getAdjacentConductors() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<Position> getAdjacentLogicalEntities() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public boolean isConducting() {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public void setActivatedTick(int tick) {
        // TODO Auto-generated method stub

    }

    @Override
    public void setConducting(boolean conducting) {
        // TODO Auto-generated method stub

    }

    @Override
    public void addObserver(ConductingObserver observer) {
        // TODO Auto-generated method stub

    }

    @Override
    public void notifyObservers(boolean isConducting, int tick) {
        // TODO Auto-generated method stub

    }

    @Override
    public void removeObserver(ConductingObserver observer) {
        // TODO Auto-generated method stub

    }

    @Override
    public void onConductingStateChange(boolean isConducting, int tick) {
        // TODO Auto-generated method stub

    }

}
