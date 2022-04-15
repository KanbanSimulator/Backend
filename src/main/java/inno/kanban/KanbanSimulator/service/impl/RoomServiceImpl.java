package inno.kanban.KanbanSimulator.service.impl;

import inno.kanban.KanbanSimulator.dao.PlayerRepository;
import inno.kanban.KanbanSimulator.dao.RoomRepository;
import inno.kanban.KanbanSimulator.dao.TeamRepository;
import inno.kanban.KanbanSimulator.dto.*;
import inno.kanban.KanbanSimulator.exception.PlayerNotFoundException;
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
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

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
                .room(room)
                .team(selectedTeam)
                .build();
        playerRepository.save(player);


        room.getPlayers().add(player);
        room.incVersion();
        roomRepository.save(room);

        return RoomDto.builder()
                .id(room.getId())
                .started(room.getStarted())
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
                    .number((long) (i + 1))
                    .build());
        }

        teamRepository.saveAll(teams);

        var player = Player.builder()
                .name(roomCreateDto.getPlayer().getName())
                .spectator(roomCreateDto.getPlayer().isSpectator())
                .team(teams.iterator().next())
                .room(room)
                .creator(true)
                .build();

        playerRepository.save(player);

        return RoomDto.builder()
                .id(room.getId())
                .started(room.getStarted())
                .players(Set.of(Mapper.mapToPlayer(player)))
                .player(Mapper.mapToPlayer(player))
                .build();
    }

    @Override
    public RoomDto checkRoomState(Long roomId,
                                  Long playerId) throws RoomNotFoundException, PlayerNotFoundException {
        var room = roomRepository.findById(roomId)
                .orElseThrow(() -> new RoomNotFoundException(roomId));

        var player = playerRepository.findById(playerId)
                .orElseThrow(() -> new PlayerNotFoundException(playerId));

        return RoomDto.builder()
                .id(room.getId())
                .started(room.getStarted())
                .players(Mapper.mapToPlayers(room.getPlayers()))
                .player(Mapper.mapToPlayer(player))
                .build();
    }

    @Transactional
    @Override
    public RoomDto startRoom(Long roomId, StartRoomDto startRoomDto) throws RoomNotFoundException {
        var room = roomRepository.findById(roomId)
                .orElseThrow(() -> new RoomNotFoundException(roomId));

        room.setStarted(true);
        var playerSettingsMap = startRoomDto.getPlayers().stream().collect(Collectors.toMap(StartPlayerDto::getId, Function.identity()));

        var teamsMap = room.getTeamSet().stream().collect(Collectors.toMap(Team::getNumber, Function.identity()));
        for (var player : room.getPlayers()) {
            var setting = playerSettingsMap.getOrDefault(player.getId(), null);
            if (setting == null) {
                throw new RuntimeException("No settings for player with id " + player.getId());
            }
            player.setSpectator(setting.getSpectator());
            var team = teamsMap.getOrDefault(setting.getTeamNumber(), null);
            if (team == null) {
                throw new RuntimeException("Team not found exception");
            }
            player.setTeam(team);
            playerRepository.save(player);
        }
        roomRepository.save(room);

        return RoomDto.builder()
                .id(room.getId())
                .started(room.getStarted())
                .players(Mapper.mapToPlayers(room.getPlayers()))
                .player(Mapper.mapToPlayer(room.getPlayers().stream().filter(player -> player.getCreator()).findFirst().get()))
                .build();
    }
}
