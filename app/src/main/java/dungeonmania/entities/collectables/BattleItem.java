package dungeonmania.entities.collectables;

import dungeonmania.battles.BattleStatistics;

/**
 * Items that provide battle buffs when in inventory
 */
public interface BattleItem {
    BattleStatistics applyBuff(BattleStatistics origin);
}
