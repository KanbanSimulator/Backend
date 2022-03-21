package inno.kanban.KanbanSimulator;

import inno.kanban.KanbanSimulator.dao.RoomRepository;
import inno.kanban.KanbanSimulator.dto.PlayerJoinDto;
import inno.kanban.KanbanSimulator.dto.RoomCreateDto;
import inno.kanban.KanbanSimulator.model.Player;
import inno.kanban.KanbanSimulator.model.Room;
import inno.kanban.KanbanSimulator.model.Team;
import inno.kanban.KanbanSimulator.service.impl.RoomServiceImpl;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.transaction.TestTransaction;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.Random;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

@Transactional
public class RoomServiceTest extends KanbanSimulatorApplicationTests {

    @Autowired
    private RoomServiceImpl roomService;

    @Autowired
    private EntityManager entityManager;

    @Test
    void testCreateRoom() {
        var playerDto = new PlayerJoinDto("Mike", false);
        var joinDto = new RoomCreateDto(random.nextInt(4), playerDto);
        var result = roomService.createRoom(joinDto);

        assertThat(result.getId(), notNullValue());
        assertThat(result.getPlayer(), allOf(
                hasProperty("id", notNullValue()),
                hasProperty("name", is("Mike")),
                hasProperty("creator", is(true)),
                hasProperty("teamNumber", notNullValue())
        ));
        assertThat(result.getPlayers().iterator().next(), allOf(
                hasProperty("id", notNullValue()),
                hasProperty("name", is("Mike")),
                hasProperty("creator", is(true)),
                hasProperty("teamNumber", notNullValue())
        ));
        assertThat(result.getStarted(), is(false));
    }

    @SneakyThrows
    @Test
    void testJoinRoom() {

        if (!TestTransaction.isActive()) {
            TestTransaction.start();
        }

        var room = new Room();

        entityManager.persist(room);

        var team = Team.builder()
                .number(1L)
                .room(room)
                .build();

        entityManager.persist(team);


        var player = Player.builder()
                .name("Mike")
                .team(team)
                .creator(true)
                .spectator(false)
                .build();

        entityManager.persist(player);

        if (TestTransaction.isActive()) {
            TestTransaction.flagForCommit();
            TestTransaction.end();
        }


        var playerDto = new PlayerJoinDto("Timur", false);
        var result = roomService.joinRoom(room.getId(), playerDto);

        assertThat(result.getId(), is(room.getId()));
        assertThat(result.getPlayer(), allOf(
                hasProperty("id", notNullValue()),
                hasProperty("name", is("Timur")),
                hasProperty("creator", is(true)),
                hasProperty("teamNumber", notNullValue())
        ));
        assertThat(result.getStarted(), is(false));
    }

}
