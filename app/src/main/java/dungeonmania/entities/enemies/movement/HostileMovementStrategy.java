package dungeonmania.entities.enemies.movement;

import dungeonmania.entities.Player;
import dungeonmania.entities.enemies.Enemy;
import dungeonmania.map.GameMap;
import dungeonmania.util.Position;

public class HostileMovementStrategy extends MovementStrategy {

    @Override
    public Position getNextPosition(Enemy enemy, Player player, GameMap map) {
        return map.dijkstraPathFind(enemy.getPosition(), player.getPosition(), enemy);
    }
}
