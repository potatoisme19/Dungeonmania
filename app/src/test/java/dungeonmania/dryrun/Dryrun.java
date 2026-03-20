/**
 * Do not change this file
 */
package dungeonmania.dryrun;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import dungeonmania.DungeonManiaController;
import dungeonmania.mvp.TestUtils;
import dungeonmania.response.models.DungeonResponse;
import dungeonmania.response.models.EntityResponse;
import dungeonmania.response.models.ItemResponse;
import dungeonmania.util.Direction;
import dungeonmania.util.Position;

public class Dryrun {
    @Test
    @Tag("Dryrun-1")
    @DisplayName("Dryrun for sunstone and more buildables")
    @Disabled // Remove this line to enable the test
    public void dryrunSunstoneAndBuildables() {
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_dryrun_sunstoneAndBuildables", "c_dryrun_sunstoneAndBuildables");
        assertTrue(countType(res, "sun_stone") == 2);
        res = dmc.tick(Direction.RIGHT);
        res = dmc.tick(Direction.RIGHT);
        res = dmc.tick(Direction.RIGHT);
        res = dmc.tick(Direction.RIGHT);
        res = dmc.tick(Direction.RIGHT);

        res = assertDoesNotThrow(() -> dmc.build("sceptre"));
        assertEquals(1, getInventory(res, "sceptre").size());
        res = assertDoesNotThrow(() -> dmc.build("midnight_armour"));
        assertEquals(1, getInventory(res, "midnight_armour").size());
    }

    @Test
    @Tag("Dryrun-2")
    @DisplayName("Dryrun for logic switches")
    @Disabled // Remove this line to enable the test
    public void dryrunLogic() {
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_dryrun_logic", "c_dryrun_logic");
        assertTrue(countType(res, "switch_door") == 1);
        assertTrue(countType(res, "bomb") == 1);
        assertTrue(countType(res, "wire") == 1);
        assertTrue(countType(res, "light_bulb_off") >= 1);
        res = dmc.tick(Direction.RIGHT);
        assertTrue(countType(res, "light_bulb_on") == 1);
    }

    private long countType(DungeonResponse res, String type) {
        return getEntitiesStream(res, type).count();
    }

    public static String getGoals(DungeonResponse dr) {
        String goals = dr.getGoals();
        return goals != null ? goals : "";
    }

    public static Stream<EntityResponse> getEntitiesStream(DungeonResponse res, String type) {
        if (type.equals("zombie_toast")) {
            return res.getEntities().stream().filter(it -> it.getType().startsWith(type))
                    .filter(it -> !it.getType().startsWith("zombie_toast_spawner"));
        }
        return res.getEntities().stream().filter(it -> it.getType().startsWith(type));
    }

    public static List<ItemResponse> getInventory(DungeonResponse res, String type) {
        return res.getInventory().stream().filter(it -> it.getType().startsWith(type)).collect(Collectors.toList());
    }

    public static Position getPosition(DungeonResponse res, String type) {
        return TestUtils.getEntities(res, type).get(0).getPosition();
    }
}
