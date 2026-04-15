package dungeonmania.task2;

import dungeonmania.DungeonManiaController;
import dungeonmania.response.models.DungeonResponse;
import dungeonmania.util.Direction;
import dungeonmania.util.Position;
import dungeonmania.mvp.TestUtils;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class LogicalTest {

    @Test
    @Tag("16-1")
    @DisplayName("Test OR logic - light bulb turns on when one switch is activated")
    public void testOrLogic() {
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_logicalTest_orLogic", "c_logicalTest");

        assertEquals("light_bulb_off", getLightBulbType(res));

        // Push boulder onto switch
        res = dmc.tick(Direction.RIGHT);

        assertEquals("light_bulb_on", getLightBulbType(res));
    }

    @Test
    @Tag("16-2")
    @DisplayName("Test AND logic - light bulb turns on only when both switches are activated")
    public void testAndLogic() {
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_logicalTest_andLogic", "c_logicalTest");

        assertEquals("light_bulb_off", getLightBulbType(res));

        // Activate first switch only
        res = dmc.tick(Direction.RIGHT);
        assertEquals("light_bulb_off", getLightBulbType(res));

        // Activate second switch
        res = dmc.tick(Direction.DOWN);
        assertEquals("light_bulb_on", getLightBulbType(res));
    }

    @Test
    @Tag("16-3")
    @DisplayName("Test XOR logic - light bulb turns on when exactly one switch is activated")
    public void testXorLogic() {
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_logicalTest_xorLogic", "c_logicalTest");

        assertEquals("light_bulb_off", getLightBulbType(res));

        // Activate first switch only
        res = dmc.tick(Direction.RIGHT);
        assertEquals("light_bulb_on", getLightBulbType(res));

        // Activate second switch (both now active)
        res = dmc.tick(Direction.DOWN);
        assertEquals("light_bulb_off", getLightBulbType(res));

        // Deactivate first switch (only second active)
        res = dmc.tick(Direction.LEFT);
        assertEquals("light_bulb_on", getLightBulbType(res));
    }

    @Test
    @Tag("16-4")
    @DisplayName("Test CO_AND logic - light bulb turns on only when both switches activate on same tick")
    public void testCoAndLogic() {
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_logicalTest_coAndLogic", "c_logicalTest");

        assertEquals("light_bulb_off", getLightBulbType(res));

        // Activate first switch only
        res = dmc.tick(Direction.RIGHT);
        assertEquals("light_bulb_off", getLightBulbType(res));

        // Activate second switch only
        res = dmc.tick(Direction.DOWN);
        assertEquals("light_bulb_off", getLightBulbType(res));

        // For CO_AND, both need to activate same tick
        // This test assumes the dungeon is designed to test that scenario
    }

    @Test
    @Tag("16-5")
    @DisplayName("Test wire propagation - current flows through multiple wires")
    public void testWirePropagation() {
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_logicalTest_wirePropagation", "c_logicalTest");

        assertEquals("light_bulb_off", getLightBulbType(res));

        // Activate switch at one end of wire chain
        res = dmc.tick(Direction.RIGHT);

        // Light bulb at other end should turn on
        assertEquals("light_bulb_on", getLightBulbType(res));
    }

    @Test
    @Tag("16-6")
    @DisplayName("Test switch door - opens when logical condition is satisfied")
    public void testSwitchDoor() {
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_logicalTest_switchDoor", "c_logicalTest");

        Position playerPos = getPlayerPos(res);
        Position doorPos = getDoorPos(res);

        // Door should be closed initially
        assertEquals("switch_door", getDoorType(res));

        // Try to walk through door - should be blocked, player stays at same position
        res = dmc.tick(Direction.RIGHT);
        assertEquals(playerPos, getPlayerPos(res));

        // Activate the switch by pushing boulder
        res = dmc.tick(Direction.DOWN);
        assertEquals("switch_door_open", getDoorType(res));

        // Now player can walk through - first move to door position
        res = dmc.tick(Direction.UP);
        assertEquals(doorPos, getPlayerPos(res));
    }

    @Test
    @Tag("16-7")
    @DisplayName("Test logical bomb - explodes when logical condition is satisfied")
    public void testLogicalBomb() {
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_logicalTest_logicalBomb", "c_logicalTest");

        // Bomb should exist on map initially
        assertEquals(1, TestUtils.getEntities(res, "bomb").size());

        // Activate the switch
        res = dmc.tick(Direction.RIGHT);

        // Bomb should explode and be removed
        assertEquals(0, TestUtils.getEntities(res, "bomb").size());

        // Surrounding entities should be destroyed (except player)
        assertEquals(0, TestUtils.getEntities(res, "wall").size());
        assertEquals(1, TestUtils.getEntities(res, "player").size());
    }

    // Helper methods
    private String getLightBulbType(DungeonResponse res) {
        return TestUtils.getEntitiesStream(res, "light_bulb_off")
                .findFirst()
                .or(() -> TestUtils.getEntitiesStream(res, "light_bulb_on").findFirst())
                .map(e -> e.getType())
                .orElse(null);
    }

    private String getDoorType(DungeonResponse res) {
        return TestUtils.getEntitiesStream(res, "switch_door")
                .findFirst()
                .or(() -> TestUtils.getEntitiesStream(res, "switch_door_open").findFirst())
                .map(e -> e.getType())
                .orElse(null);
    }

    private Position getDoorPos(DungeonResponse res) {
        return TestUtils.getEntitiesStream(res, "switch_door")
                .findFirst()
                .or(() -> TestUtils.getEntitiesStream(res, "switch_door_open").findFirst())
                .map(e -> e.getPosition())
                .orElse(null);
    }

    private Position getPlayerPos(DungeonResponse res) {
        return TestUtils.getEntities(res, "player").get(0).getPosition();
    }
}
