package dungeonmania.entities.buildables;

import dungeonmania.Game;
import dungeonmania.battles.BattleStatistics;
import dungeonmania.entities.collectables.Useable;
import dungeonmania.entities.collectables.BattleItem;
import dungeonmania.entities.collectables.DurabilityHelper;
import dungeonmania.entities.collectables.DurableItem;

public class Shield extends Buildable implements Useable, BattleItem, DurableItem {
    private DurabilityHelper durability;
    private double defence;

    public Shield(int durability, double defence) {
        super(null);
        this.durability = new DurabilityHelper(durability, this);
        this.defence = defence;
    }

    @Override
    public void use(Game game) {
        durability.decrement(game);
    }

    @Override
    public BattleStatistics applyBuff(BattleStatistics origin) {
        return BattleStatistics.applyBuff(origin, new BattleStatistics(0, 0, defence, 1, 1));
    }

    @Override
    public int getDurability() {
        return durability.getDurability();
    }

    @Override
    public void useDurability(Game game) {
        use(game);
    }

}
