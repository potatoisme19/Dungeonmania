package dungeonmania.entities.buildables;

import dungeonmania.entities.collectables.Key;
import dungeonmania.entities.collectables.Treasure;
import dungeonmania.entities.collectables.Wood;
import dungeonmania.entities.factories.EntityFactory;
import dungeonmania.entities.inventory.Inventory;
import dungeonmania.entities.inventory.InventoryItem;

public class ShieldStrategy implements CraftingStrategy {

    @Override
    public boolean canCraft(Inventory inventory) {
        return inventory.count(Wood.class) >= 2
                && (inventory.count(Treasure.class) >= 1 || inventory.count(Key.class) >= 1);
    }

    @Override
    public InventoryItem craft(Inventory inventory, EntityFactory factory) {
        inventory.removeFirst(Wood.class, 2);

        if (inventory.count(Treasure.class) >= 1) {
            inventory.removeFirst(Treasure.class, 1);
        } else {
            inventory.removeFirst(Key.class, 1);
        }

        return factory.getShieldFactory().build(null);
    }

    @Override
    public String getStrategyName() {
        return "shield";
    }

}
