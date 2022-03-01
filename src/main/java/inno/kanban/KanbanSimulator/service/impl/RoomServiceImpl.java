package inno.kanban.KanbanSimulator.service.impl;

import inno.kanban.KanbanSimulator.dao.PlayerRepository;
import inno.kanban.KanbanSimulator.dao.RoomRepository;
import inno.kanban.KanbanSimulator.dao.TeamRepository;
import inno.kanban.KanbanSimulator.dto.PlayerJoinDto;
import inno.kanban.KanbanSimulator.dto.RoomCreateDto;
import inno.kanban.KanbanSimulator.dto.RoomDto;
import inno.kanban.KanbanSimulator.exception.RoomNotFoundException;
import inno.kanban.KanbanSimulator.model.Player;
import inno.kanban.KanbanSimulator.model.Room;
import inno.kanban.KanbanSimulator.model.Team;
import inno.kanban.KanbanSimulator.service.RoomService;
import inno.kanban.KanbanSimulator.utils.Mapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class RoomServiceImpl implements RoomService {

    private final RoomRepository roomRepository;
    private final PlayerRepository playerRepository;
    private final TeamRepository teamRepository;

    @Override
    @Transactional
    public RoomDto joinRoom(Long roomId,
                            PlayerJoinDto playerJoinDto) throws RoomNotFoundException {
        var room = roomRepository.findById(roomId)
                .orElseThrow(() -> new RoomNotFoundException(roomId));

        var selectedTeam = room.getTeamSet().iterator().next();
        var minPlayerNumber = selectedTeam.getPlayerSet().stream()
                .filter(player -> !player.getSpectator())
                .count();
        for (var team : room.getTeamSet()) {
            if (team.getPlayerSet().stream()
                    .filter(player -> !player.getSpectator())
                    .count() < minPlayerNumber) {
                selectedTeam = team;
                minPlayerNumber = team.getPlayerSet().stream()
                        .filter(player -> !player.getSpectator())
                        .count();
            }
        }
        var player = Player.builder()
                .name(playerJoinDto.getName())
                .spectator(playerJoinDto.isSpectator())
                .team(selectedTeam)
                .build();
        playerRepository.save(player);


        room.getPlayers().add(player);
        room.incVersion();
        roomRepository.save(room);

        return RoomDto.builder()
                .id(room.getId())
                .players(Mapper.mapToPlayers(room.getPlayers()))
                .player(Mapper.mapToPlayer(player))
                .build();
    }

    @Override
    @Transactional
    public RoomDto createRoom(RoomCreateDto roomCreateDto) {
        var room = new Room();
        roomRepository.save(room);

        Set<Team> teams = new HashSet<>();
        for (var i = 0; i < roomCreateDto.getTeamsAmount(); i++) {
            teams.add(Team.builder()
                    .room(room)
                    .build());
        }

        teamRepository.saveAll(teams);

        var player = Player.builder()
                .name(roomCreateDto.getPlayer().getName())
                .spectator(roomCreateDto.getPlayer().isSpectator())
                .team(teams.iterator().next())
                .creator(true)
                .build();

        playerRepository.save(player);

        return RoomDto.builder()
                .id(room.getId())
                .players(Set.of())
                .player(Mapper.mapToPlayer(player))
                .build();
    }
}
