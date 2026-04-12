package dungeonmania.entities.buildables;

import dungeonmania.Game;
import dungeonmania.battles.BattleStatistics;
import dungeonmania.entities.collectables.BattleItem;
import dungeonmania.entities.collectables.DurabilityHelper;
import dungeonmania.entities.collectables.DurableItem;
import dungeonmania.entities.collectables.Useable;

public class Bow extends Buildable implements Useable, BattleItem, DurableItem {
    private DurabilityHelper durability;

    public Bow(int durability) {
        super(null);
        this.durability = new DurabilityHelper(durability, this);
    }

    @Override
    public void use(Game game) {
        durability.decrement(game);
    }

    @Override
    public void useDurability(Game game) {
        use(game);

    }

    @Override
    public BattleStatistics applyBuff(BattleStatistics origin) {
        return BattleStatistics.applyBuff(origin, new BattleStatistics(0, 0, 0, 2, 1));
    }

    @Override
    public int getDurability() {
        return durability.getDurability();
    }
}
