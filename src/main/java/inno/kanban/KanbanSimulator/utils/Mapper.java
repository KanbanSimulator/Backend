package inno.kanban.KanbanSimulator.utils;

import inno.kanban.KanbanSimulator.dto.PlayerDto;
import inno.kanban.KanbanSimulator.dto.TeamDto;
import inno.kanban.KanbanSimulator.model.Player;
import inno.kanban.KanbanSimulator.model.Team;

import java.util.Set;
import java.util.stream.Collectors;

public class Mapper {

    public static PlayerDto mapToPlayer(Player player) {
        return PlayerDto.builder()
                .id(player.getId())
                .name(player.getName())
                .creator(player.getCreator())
                .teamNumber(player.getTeam().getNumber())
                .spectator(player.getSpectator())
                .teamId(player.getTeam().getId())
                .build();
    }

    public static Set<PlayerDto> mapToPlayers(Set<Player> players) {
        return players.stream()
                .map(Mapper::mapToPlayer)
                .collect(Collectors.toSet());
    }

    public static Set<TeamDto> mapToTeams(Set<Team> teams) {
        return teams.stream()
                .map(team -> TeamDto.builder()
                        .teamId(team.getId())
                        .teamNumber(team.getNumber())
                        .build())
                .collect(Collectors.toSet());
    }
}
