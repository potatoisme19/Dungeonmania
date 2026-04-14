package dungeonmania.entities.buildables;

import dungeonmania.entities.factories.EntityFactory;
import dungeonmania.entities.inventory.Inventory;
import dungeonmania.entities.inventory.InventoryItem;

public interface CraftingStrategy {
    boolean canCraft(Inventory inventory);

    InventoryItem craft(Inventory inventory, EntityFactory factory);

    String getStrategyName();
}
