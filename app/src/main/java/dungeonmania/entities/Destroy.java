package dungeonmania.entities;

import dungeonmania.map.GameMap;

/** Called when this entity is destroyed (ie removed from the game map). */
public interface Destroy {
    void onDestroy(GameMap gameMap);
}
