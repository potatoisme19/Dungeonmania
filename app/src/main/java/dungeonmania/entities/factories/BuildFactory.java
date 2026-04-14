package dungeonmania.entities.factories;

import dungeonmania.entities.Entity;
import dungeonmania.util.Position;

public interface BuildFactory {
    public Entity build(Position pos);
}
