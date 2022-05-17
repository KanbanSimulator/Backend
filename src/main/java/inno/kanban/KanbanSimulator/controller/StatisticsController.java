package inno.kanban.KanbanSimulator.controller;

import inno.kanban.KanbanSimulator.dto.StatisticsDto;
import inno.kanban.KanbanSimulator.service.BoardService;
import inno.kanban.KanbanSimulator.utils.ResponseWrapper;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/statistics")
public class StatisticsController {

    private final BoardService boardService;

    @GetMapping("/{roomId}")
    public ResponseWrapper<StatisticsDto> getStatistics(@PathVariable("roomId") Long roomId) {
        return ResponseWrapper.from(boardService.getStatistics(roomId));
    }
}
