package dungeonmania.entities;

import dungeonmania.map.GameMap;

/** Called whenever another entity moves away from this entity's position */
public interface MoveAway {
    void onMovedAway(GameMap map, Entity entity);
}
