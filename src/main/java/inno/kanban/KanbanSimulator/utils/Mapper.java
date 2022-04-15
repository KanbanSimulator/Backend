package inno.kanban.KanbanSimulator.utils;

import inno.kanban.KanbanSimulator.dto.PlayerDto;
import inno.kanban.KanbanSimulator.model.Player;

import java.util.Set;
import java.util.stream.Collectors;

public class Mapper {

    public static PlayerDto mapToPlayer(Player player) {
        return PlayerDto.builder()
                .id(player.getId())
                .name(player.getName())
                .creator(player.getCreator())
                .teamNumber(player.getTeam().getNumber())
                .teamId(player.getTeam().getId())
                .build();
    }

    public static Set<PlayerDto> mapToPlayers(Set<Player> players) {
        return players.stream()
                .map(Mapper::mapToPlayer)
                .collect(Collectors.toSet());
    }
}
