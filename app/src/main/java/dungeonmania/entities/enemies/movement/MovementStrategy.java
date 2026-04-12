package dungeonmania.entities.enemies.movement;

import dungeonmania.entities.Player;
import dungeonmania.entities.enemies.Enemy;
import dungeonmania.map.GameMap;
import dungeonmania.util.Position;

public abstract class MovementStrategy {
    public abstract Position getNextPosition(Enemy enemy, Player player, GameMap map);
}
