package dungeonmania.entities.buildables;

import dungeonmania.entities.inventory.InventoryItem;
import dungeonmania.util.Position;

/**
 * Represents an item which can be built (crafted) by the player.
 */
public abstract class Buildable extends InventoryItem {
    public Buildable(Position position) {
        super(position);
    }

}
