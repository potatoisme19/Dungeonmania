package dungeonmania.entities.buildables;

import dungeonmania.entities.collectables.Arrow;
import dungeonmania.entities.collectables.Wood;
import dungeonmania.entities.factories.EntityFactory;
import dungeonmania.entities.inventory.Inventory;
import dungeonmania.entities.inventory.InventoryItem;

public class BowStrategy implements CraftingStrategy {
    @Override
    public boolean canCraft(Inventory inventory) {
        return inventory.count(Wood.class) >= 1 && inventory.count(Arrow.class) >= 3;
    }

    @Override
    public InventoryItem craft(Inventory inventory, EntityFactory factory) {
        inventory.removeFirst(Wood.class, 1);
        inventory.removeFirst(Arrow.class, 3);
        return factory.getBowFactory().build(null);
    }

    @Override
    public String getStrategyName() {
        return "bow";
    }
}
