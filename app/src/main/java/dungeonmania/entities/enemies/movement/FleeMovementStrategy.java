package dungeonmania.entities.enemies.movement;

import dungeonmania.entities.Player;
import dungeonmania.entities.enemies.Enemy;
import dungeonmania.map.GameMap;
import dungeonmania.util.Direction;
import dungeonmania.util.Position;

public class FleeMovementStrategy extends MovementStrategy {

    @Override
    public Position getNextPosition(Enemy enemy, Player player, GameMap map) {
        Position playerPos = player.getPosition();
        Position enemyPos = enemy.getPosition();
        Position plrDiff = Position.calculatePositionBetween(playerPos, enemyPos);

        Position moveX = (plrDiff.getX() >= 0)
                ? Position.translateBy(enemyPos, Direction.RIGHT)
                : Position.translateBy(enemyPos, Direction.LEFT);

        Position moveY = (plrDiff.getY() >= 0)
                ? Position.translateBy(enemyPos, Direction.DOWN)
                : Position.translateBy(enemyPos, Direction.UP);

        Position offset = enemyPos;

        // If on the same Y axis and can flee left or right, do so.
        if (plrDiff.getY() == 0 && canMoveTo(enemy, moveX, map))
            offset = moveX;
        // Or if on the same X axis and can flee up or down, do so.
        else if (plrDiff.getX() == 0 && canMoveTo(enemy, moveY, map))
            offset = moveY;
        // Prioritise Y movement if further away on the X axis
        else if (Math.abs(plrDiff.getX()) >= Math.abs(plrDiff.getY())) {
            if (canMoveTo(enemy, moveY, map))
                offset = moveY;
            else if (canMoveTo(enemy, moveX, map))
                offset = moveX;
            else
                offset = enemyPos;
            // Prioritise X movement if further away on the Y axis
        } else {
            if (canMoveTo(enemy, moveX, map))
                offset = moveX;
            else if (canMoveTo(enemy, moveY, map))
                offset = moveY;
            else
                offset = enemyPos;
        }

        return offset;
    }

    private boolean canMoveTo(Enemy enemy, Position position, GameMap map) {
        return map.canMoveTo(enemy, position);
    }

}
