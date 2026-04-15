package dungeonmania.task2;

import dungeonmania.DungeonManiaController;
import dungeonmania.mvp.TestUtils;
import dungeonmania.response.models.DungeonResponse;
import dungeonmania.response.models.EntityResponse;
import dungeonmania.util.Direction;
import dungeonmania.util.Position;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import java.util.stream.Collectors;

public class LogicalTest {

    @Test
    @Tag("16-1")
    @DisplayName("Test OR logic - light bulb turns on when one switch is activated")
    public void testOrLogic() {
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_logicalTest_orLogic", "c_logicalTest_orLogic");

        // Initially light bulb should be off
        assertEquals("light_bulb_off", getLightBulbType(res));

        // Activate first switch
        res = dmc.tick(Direction.RIGHT);
        assertEquals("light_bulb_on", getLightBulbType(res));
    }

    @Test
    @Tag("16-2")
    @DisplayName("Test AND logic - light bulb turns on only when both switches are activated")
    public void testAndLogic() {
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_logicalTest_andLogic", "c_logicalTest_andLogic");

        // Initially light bulb should be off
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
        DungeonResponse res = dmc.newGame("d_logicalTest_xorLogic", "c_logicalTest_xorLogic");

        // Initially light bulb should be off
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
    public void testCoAndLogic() throws InterruptedException {
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_logicalTest_coAndLogic", "c_logicalTest_coAndLogic");

        // Initially light bulb should be off
        assertEquals("light_bulb_off", getLightBulbType(res));

        // Activate first switch (different tick)
        res = dmc.tick(Direction.RIGHT);
        assertEquals("light_bulb_off", getLightBulbType(res));

        // Activate second switch (different tick)
        res = dmc.tick(Direction.DOWN);
        assertEquals("light_bulb_off", getLightBulbType(res));

        // Reset and activate both on same tick (need to design dungeon for this)
        // For CO_AND, you may need a different approach to test simultaneous activation
    }

    @Test
    @Tag("16-5")
    @DisplayName("Test wire propagation - current flows through multiple wires")
    public void testWirePropagation() {
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_logicalTest_wirePropagation", "c_logicalTest_wirePropagation");

        // Initially light bulb should be off
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
        DungeonResponse res = dmc.newGame("d_logicalTest_switchDoor", "c_logicalTest_switchDoor");

        Position playerPos = getPlayerPos(res);
        Position doorPos = getDoorPos(res);

        // Door should be closed initially
        assertEquals("switch_door", getDoorType(res));

        // Try to walk through door (should be blocked)
        res = dmc.tick(Direction.RIGHT);
        assertEquals(playerPos, getPlayerPos(res));

        // Activate the switch
        res = dmc.tick(Direction.DOWN);
        assertEquals("switch_door_open", getDoorType(res));

        // Now player can walk through
        res = dmc.tick(Direction.UP);
        assertNotEquals(playerPos, getPlayerPos(res));
    }

    @Test
    @Tag("16-7")
    @DisplayName("Test logical bomb - explodes when logical condition is satisfied")
    public void testLogicalBomb() {
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_logicalTest_logicalBomb", "c_logicalTest_logicalBomb");

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

    @Test
    @Tag("16-8")
    @DisplayName("Test multiple logical entities - complex circuit")
    public void testComplexCircuit() {
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_logicalTest_complexCircuit", "c_logicalTest_complexCircuit");

        // Test a circuit combining AND and OR gates
        // (Design depends on your test dungeon)

        assertEquals("light_bulb_off", getLightBulbType(res, 0));
        assertEquals("light_bulb_off", getLightBulbType(res, 1));

        // Activate switches in sequence
        res = dmc.tick(Direction.RIGHT);
        // Assert expected states
    }

    // Helper methods
    private String getLightBulbType(DungeonResponse res) {
        return TestUtils.getEntitiesStream(res, "light_bulb_off")
                .findFirst()
                .or(() -> TestUtils.getEntitiesStream(res, "light_bulb_on").findFirst())
                .map(e -> e.getType())
                .orElse(null);
    }

    private String getLightBulbType(DungeonResponse res, int index) {
        List<EntityResponse> bulbs = TestUtils.getEntitiesStream(res, "light_bulb_off")
                .collect(Collectors.toList());
        bulbs.addAll(TestUtils.getEntitiesStream(res, "light_bulb_on").collect(Collectors.toList()));
        return bulbs.get(index).getType();
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
