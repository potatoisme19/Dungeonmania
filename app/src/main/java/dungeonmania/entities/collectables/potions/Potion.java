package dungeonmania.entities.collectables.potions;

import dungeonmania.Game;
import dungeonmania.battles.BattleStatistics;
import dungeonmania.entities.Entity;
import dungeonmania.entities.Overlappable;
import dungeonmania.entities.Player;
import dungeonmania.entities.collectables.BattleItem;
import dungeonmania.entities.collectables.Useable;
import dungeonmania.entities.inventory.InventoryItem;
import dungeonmania.map.GameMap;
import dungeonmania.util.Position;

public abstract class Potion extends InventoryItem implements Useable, BattleItem, Overlappable {
    private int duration;

    public Potion(Position position, int duration) {
        super(position);
        this.duration = duration;
    }

    @Override
    public void onOverlap(GameMap map, Entity entity) {
        if (entity instanceof Player player) {
            if (!player.pickUp(this))
                return;
            map.destroyEntity(this);
        }
    }

    public int getDuration() {
        return duration;
    }

    @Override
    public BattleStatistics applyBuff(BattleStatistics origin) {
        return origin;
    }

    @Override
    public void use(Game game) {
        return;
    }
}
