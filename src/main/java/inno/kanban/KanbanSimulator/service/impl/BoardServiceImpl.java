package inno.kanban.KanbanSimulator.service.impl;

import inno.kanban.KanbanSimulator.dao.PlayerRepository;
import inno.kanban.KanbanSimulator.dao.TeamRepository;
import inno.kanban.KanbanSimulator.dto.BoardDto;
import inno.kanban.KanbanSimulator.dto.WipLimitDto;
import inno.kanban.KanbanSimulator.exception.PlayerNotFoundException;
import inno.kanban.KanbanSimulator.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BoardServiceImpl implements BoardService {

    private final TeamRepository teamRepository;
    private final PlayerRepository playerRepository;


    @Override
    public BoardDto editBoard(Long playerId, WipLimitDto wipLimitDto) throws PlayerNotFoundException {
        var player = playerRepository.findById(playerId)
                .orElseThrow(() -> new PlayerNotFoundException(playerId));
        var team = player.getTeam();
        team.setWip1(wipLimitDto.getWip1());
        team.setWip2(wipLimitDto.getWip2());
        team.setWip3(wipLimitDto.getWip3());
        team.incVersion();
        teamRepository.save(team);
        return null;
    }

    @Override
    public BoardDto getBoard(Long playerId) throws PlayerNotFoundException {
        var player = playerRepository.findById(playerId)
                .orElseThrow(() -> new PlayerNotFoundException(playerId));

        var team = player.getTeam();
        var cards = team.getCardSet();

        return null;
    }
}
