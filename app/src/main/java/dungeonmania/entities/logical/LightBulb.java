package dungeonmania.entities.logical;

import java.util.List;

import dungeonmania.entities.Entity;
import dungeonmania.entities.logical.strategies.LogicStrategy;
import dungeonmania.util.Position;

public class LightBulb extends Entity implements Logical {

    public LightBulb(Position position) {
        super(position);
    }

    @Override
    public LogicStrategy getLogicStrategy() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public boolean isActivated() {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public void setActivated(boolean activated) {
        // TODO Auto-generated method stub

    }

    @Override
    public void updateActivation(List<Conductor> adjacentConductors, int currentTick) {
        // TODO Auto-generated method stub

    }

}
