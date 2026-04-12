// dungeonmania/entities/inventory/DurabilityHelper.java
package dungeonmania.entities.collectables;

import dungeonmania.Game;
import dungeonmania.entities.Player;
import dungeonmania.entities.inventory.InventoryItem;

public class DurabilityHelper {
    private int durability;
    private InventoryItem item;

    public DurabilityHelper(int durability, InventoryItem item) {
        this.durability = durability;
        this.item = item;
    }

    public boolean decrement(Game game) {
        durability--;
        if (durability <= 0) {
            Player player = game.getPlayer();
            if (player != null) {
                player.remove(item);
            }
            return true;
        }
        return false;
    }

    public int getDurability() {
        return durability;
    }
}
