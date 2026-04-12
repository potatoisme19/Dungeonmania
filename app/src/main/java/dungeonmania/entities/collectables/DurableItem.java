package dungeonmania.entities.collectables;

import dungeonmania.Game;

public interface DurableItem {
    int getDurability();

    void useDurability(Game game);
}
