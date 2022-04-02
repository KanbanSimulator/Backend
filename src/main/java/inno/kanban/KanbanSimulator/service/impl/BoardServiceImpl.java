package inno.kanban.KanbanSimulator.service.impl;

import inno.kanban.KanbanSimulator.dao.CardDao;
import inno.kanban.KanbanSimulator.dao.PlayerRepository;
import inno.kanban.KanbanSimulator.dao.TeamRepository;
import inno.kanban.KanbanSimulator.dto.*;
import inno.kanban.KanbanSimulator.exception.CardNotFoundException;
import inno.kanban.KanbanSimulator.exception.PlayerNotFoundException;
import inno.kanban.KanbanSimulator.exception.TeamNotFoundException;
import inno.kanban.KanbanSimulator.model.Card;
import inno.kanban.KanbanSimulator.model.Team;
import inno.kanban.KanbanSimulator.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BoardServiceImpl implements BoardService {

    private final TeamRepository teamRepository;
    private final PlayerRepository playerRepository;
    private final CardDao cardDao;

    private final Random RANDOM = new Random();


//    @Override
//    public BoardDto editBoard(Long playerId, WipLimitDto wipLimitDto) throws PlayerNotFoundException {
//        var player = playerRepository.findById(playerId)
//                .orElseThrow(() -> new PlayerNotFoundException(playerId));
//        var team = player.getTeam();
//        team.setWip1(wipLimitDto.getWip1());
//        team.setWip2(wipLimitDto.getWip2());
//        team.setWip3(wipLimitDto.getWip3());
//        team.incVersion();
//        teamRepository.save(team);
//        return null;
//    }

    @Override
    @Transactional
    public BoardDto getBoard(Long teamId) {
        var team = teamRepository.findById(teamId)
                .orElseThrow(() -> new TeamNotFoundException(teamId));

        var cards = team.getCardSet();
        var cardMap = cards.stream().collect(Collectors.groupingBy(Card::getColumnType));
        return BoardDto.builder()
                .teamId(team.getId())
                .queue(mapCardsToDto(cardMap.getOrDefault(Card.ColumnType.NEW, Collections.emptyList())))
                .analyticsCards(mapCardsToDto(cardMap.getOrDefault(Card.ColumnType.ANALYTICS, Collections.emptyList())))
                .testingCards(mapCardsToDto(cardMap.getOrDefault(Card.ColumnType.TESTING, Collections.emptyList())))
                .finishedCards(mapCardsToDto(cardMap.getOrDefault(Card.ColumnType.FINISHED, Collections.emptyList())))
                .build();
    }

    @Transactional
    public BoardDto moveCard(MoveCardDto moveCardDto) {

        var movedCard = cardDao.findById(moveCardDto.getCardId())
                .orElseThrow(() -> new CardNotFoundException(moveCardDto.getCardId()));

        var team = movedCard.getTeam();
        var cards = team.getCardSet();
        var cardMap = cards.stream().collect(Collectors.groupingBy(Card::getColumnType));

        int priority = moveCardDto.getPriority();
        var selectedCards = cardMap.getOrDefault(moveCardDto.getColumnType(), Collections.emptyList());
        for (var card : selectedCards) {
            if (card.getPriority() == priority) {
                priority += 1;
                card.setPriority(priority);
            }
        }
        cardDao.saveAll(selectedCards);
        movedCard.setColumnType(moveCardDto.getColumnType());
        movedCard.setPriority(moveCardDto.getPriority());

        return BoardDto.builder()
                .teamId(team.getId())
                .queue(mapCardsToDto(cardMap.getOrDefault(Card.ColumnType.NEW, Collections.emptyList())))
                .analyticsCards(mapCardsToDto(cardMap.getOrDefault(Card.ColumnType.ANALYTICS, Collections.emptyList())))
                .testingCards(mapCardsToDto(cardMap.getOrDefault(Card.ColumnType.TESTING, Collections.emptyList())))
                .finishedCards(mapCardsToDto(cardMap.getOrDefault(Card.ColumnType.FINISHED, Collections.emptyList())))
                .build();
    }

    @Override
    @Transactional
    public BoardDto populateBacklog(PopulateBacklogDto populateBacklogDto) {

        var team = teamRepository.findById(populateBacklogDto.getTeamId())
                .orElseThrow(() -> new TeamNotFoundException(populateBacklogDto.getTeamId()));

        int storyNum = 0;
        var newCards = generateNewCards(team, RANDOM.nextInt(8), Card.ColumnType.NEW, storyNum);
        cardDao.saveAll(newCards);
        storyNum += newCards.size();
        var analCards = generateNewCards(team, RANDOM.nextInt(3), Card.ColumnType.ANALYTICS, storyNum);
        cardDao.saveAll(analCards);
        storyNum += analCards.size();
        var devCards = generateNewCards(team, RANDOM.nextInt(3), Card.ColumnType.DEVELOPMENT, storyNum);
        devCards.forEach(card -> {
            card.setAnalyticCompleted(card.getAnalyticCompleted());
        });
        cardDao.saveAll(devCards);
        storyNum += devCards.size();
        var testCards = generateNewCards(team, RANDOM.nextInt(3), Card.ColumnType.TESTING, storyNum);
        testCards.forEach(card -> {
            card.setAnalyticCompleted(card.getAnalyticCompleted());
            card.setDevelopCompleted(card.getDevelopRemaining());
        });
        cardDao.saveAll(testCards);

        return BoardDto.builder()
                .teamId(team.getId())
                .queue(mapCardsToDto(newCards))
                .analyticsCards(mapCardsToDto(analCards))
                .testingCards(mapCardsToDto(testCards))
                .build();
    }

    private List<Card> generateNewCards(Team team,
                                        Integer randomNum,
                                        Card.ColumnType columnType,
                                        Integer initialStoryNum) {
        Integer initialNum = initialStoryNum;
        var cards = new ArrayList<Card>();
        var priority = 0;
        for (int i = 0; i < RANDOM.nextInt(randomNum); i++) {
            cards.add(Card.builder()
                    .analyticCompleted(0)
                    .analyticRemaining(RANDOM.nextInt(20))
                    .title("Story " + initialNum)
                    .businessValue(RANDOM.nextInt(60))
                    .columnType(columnType)
                    .developCompleted(0)
                    .developRemaining(RANDOM.nextInt(20))
                    .testingCompleted(0)
                    .testingRemaining(RANDOM.nextInt(20))
                    .isExpedite(RANDOM.nextInt(100) > 90)
                    .team(team)
                    .priority(priority)
                    .build());
            initialNum++;
            priority ++;
        }
        return cards;
    }

    private List<CardDto> mapCardsToDto(List<Card> cards) {
        return cards.stream().map(card -> CardDto.builder()
                .id(card.getId())
                .columnType(card.getColumnType())
                .businessValue(card.getBusinessValue())
                .isExpedite(card.getIsExpedite())
                .title(card.getTitle())
                .analyticCompleted(card.getAnalyticCompleted())
                .analyticRemaining(card.getAnalyticRemaining())
                .developCompleted(card.getDevelopCompleted())
                .developRemaining(card.getDevelopRemaining())
                .testingCompleted(card.getTestingCompleted())
                .testingRemaining(card.getTestingRemaining())
                .readyDay(card.getReadyDay())
                .build())
                .collect(Collectors.toList());
    }
}
