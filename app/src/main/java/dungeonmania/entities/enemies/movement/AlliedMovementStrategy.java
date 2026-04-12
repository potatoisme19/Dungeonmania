package dungeonmania.entities.enemies.movement;

import dungeonmania.entities.Player;
import dungeonmania.entities.enemies.Enemy;
import dungeonmania.map.GameMap;
import dungeonmania.util.Position;

public class AlliedMovementStrategy extends MovementStrategy {
    private boolean wasAdjacentToPlayer = false;

    @Override
    public Position getNextPosition(Enemy enemy, Player player, GameMap map) {
        Position nextPos;
        boolean isAdjacentToPlayer = Position.isAdjacent(player.getPosition(), enemy.getPosition());

        if (wasAdjacentToPlayer && !isAdjacentToPlayer) {
            nextPos = player.getPreviousDistinctPosition();
        } else {
            nextPos = isAdjacentToPlayer
                    ? enemy.getPosition()
                    : map.dijkstraPathFind(enemy.getPosition(), player.getPosition(), enemy);
            wasAdjacentToPlayer = Position.isAdjacent(player.getPosition(), nextPos);
        }

        return nextPos;
    }
}
