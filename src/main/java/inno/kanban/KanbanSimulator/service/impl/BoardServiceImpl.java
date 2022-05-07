package inno.kanban.KanbanSimulator.service.impl;

import inno.kanban.KanbanSimulator.dao.CardDao;
import inno.kanban.KanbanSimulator.dao.PlayerRepository;
import inno.kanban.KanbanSimulator.dao.TeamRepository;
import inno.kanban.KanbanSimulator.dto.*;
import inno.kanban.KanbanSimulator.exception.CardNotFoundException;
import inno.kanban.KanbanSimulator.exception.KanbanException;
import inno.kanban.KanbanSimulator.exception.TeamNotFoundException;
import inno.kanban.KanbanSimulator.model.Card;
import inno.kanban.KanbanSimulator.model.Team;
import inno.kanban.KanbanSimulator.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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
        return BoardDto.builder()
                .teamId(team.getId())
                .day(team.getDayNum())
                .analyticsFreePersons(team.getFreeAnalyticsPersons())
                .developmentFreePersons(team.getFreeDevelopmentPersons())
                .testFreePersons(team.getFreeTestingPersons())
                .cards(mapCardsToDto(new ArrayList<>(cards)))
                .build();
    }

    @Transactional
    public BoardDto moveCard(MoveCardDto moveCardDto) {

        var movedCard = cardDao.findById(moveCardDto.getCardId())
                .orElseThrow(() -> new CardNotFoundException(moveCardDto.getCardId()));

        var team = movedCard.getTeam();
        var cards = team.getCardSet();
        var cardMap = cards.stream().collect(Collectors.groupingBy(Card::getColumnType));

        int priority = moveCardDto.getOrdering();
        var type = getColumnType(moveCardDto.getColumnNumber());
        var status = getColumnStatus(moveCardDto.getColumnNumber());
        var selectedCards = cardMap.getOrDefault(type, Collections.emptyList());
        for (var card : selectedCards) {
            if (card.getPriority() == priority) {
                priority += 1;
                card.setPriority(priority);
            }
        }
        cardDao.saveAll(selectedCards);
        movedCard.setColumnType(type);
        movedCard.setPriority(moveCardDto.getOrdering());
        movedCard.setColumnStatus(status);

        return BoardDto.builder()
                .teamId(team.getId())
                .day(team.getDayNum())
                .analyticsFreePersons(team.getFreeAnalyticsPersons())
                .developmentFreePersons(team.getFreeDevelopmentPersons())
                .testFreePersons(team.getFreeTestingPersons())
                .cards(mapCardsToDto(new ArrayList<>(cards)))
                .build();
    }

    @Override
    @Transactional
    public BoardDto movePerson(Long teamId,
                               MovePersonDto movePersonDto) {
        var team = teamRepository.findById(teamId)
                .orElseThrow(() -> new TeamNotFoundException(teamId));

        var targetCard = cardDao.findById(movePersonDto.getCurrentCard())
                .orElseThrow(() -> new CardNotFoundException(movePersonDto.getCurrentCard()));
        if (movePersonDto.getPrevCard() == null) {
            switch (targetCard.getColumnType()) {
                case TESTING: team.setFreeTestingPersons(team.getFreeAnalyticsPersons() - 1);
                case ANALYTICS: team.setFreeAnalyticsPersons(team.getFreeAnalyticsPersons() - 1);
                case DEVELOPMENT: team.setFreeDevelopmentPersons(team.getFreeDevelopmentPersons() - 1);
                default: throw new KanbanException("Wrong input");
            }
        } else {
            var prevCard = cardDao.findById(movePersonDto.getPrevCard())
                    .orElseThrow(() -> new CardNotFoundException(movePersonDto.getPrevCard()));
            prevCard.setPersonsCount(prevCard.getPersonsCount() - 1);
        }
        targetCard.setPersonsCount(targetCard.getPersonsCount() + 1);

        return BoardDto.builder()
                .teamId(team.getId())
                .day(team.getDayNum())
                .analyticsFreePersons(team.getFreeAnalyticsPersons())
                .developmentFreePersons(team.getFreeDevelopmentPersons())
                .testFreePersons(team.getFreeTestingPersons())
                .cards(mapCardsToDto(new ArrayList<>(team.getCardSet())))
                .build();
    }

    @Override
    @Transactional
    public BoardDto getOrCreateBoard(PopulateBacklogDto populateBacklogDto) {

        var team = teamRepository.findById(populateBacklogDto.getTeamId())
                .orElseThrow(() -> new TeamNotFoundException(populateBacklogDto.getTeamId()));

        if (team.getCardSet().size() > 0) {
            return BoardDto.builder()
                    .teamId(team.getId())
                    .cards(mapCardsToDto(new ArrayList<>(team.getCardSet())))
                    .build();
        }

        int storyNum = 0;
        var newCards = generateNewCards(team, RANDOM.nextInt(8) + 10, Card.ColumnType.QUEUE, storyNum);
        cardDao.saveAll(newCards);
        storyNum += newCards.size();
        var analCards = generateNewCards(team, RANDOM.nextInt(3) + 1, Card.ColumnType.ANALYTICS, storyNum);
        cardDao.saveAll(analCards);
        storyNum += analCards.size();
        var devCards = generateNewCards(team, RANDOM.nextInt(3) + 1, Card.ColumnType.DEVELOPMENT, storyNum);
        devCards.forEach(card -> {
            card.setAnalyticCompleted(card.getAnalyticCompleted());
        });
        cardDao.saveAll(devCards);
        storyNum += devCards.size();
        var testCards = generateNewCards(team, RANDOM.nextInt(3) + 1, Card.ColumnType.TESTING, storyNum);
        testCards.forEach(card -> {
            card.setAnalyticCompleted(card.getAnalyticCompleted());
            card.setDevelopCompleted(card.getDevelopRemaining());
        });
        cardDao.saveAll(testCards);

        return BoardDto.builder()
                .teamId(team.getId())
                .day(team.getDayNum())
                .analyticsFreePersons(team.getFreeAnalyticsPersons())
                .developmentFreePersons(team.getFreeDevelopmentPersons())
                .testFreePersons(team.getFreeTestingPersons())
                .cards(mapCardsToDto(Stream.of(newCards, analCards, devCards, testCards).flatMap(Collection::stream).collect(Collectors.toList())))
                .build();
    }

    @Override
    public BoardDto startDay(Long teamId) {
        var team = teamRepository.findById(teamId)
                .orElseThrow(() -> new TeamNotFoundException(teamId));

        team.setFreeDevelopmentPersons(5);
        team.setFreeAnalyticsPersons(5);
        team.setFreeTestingPersons(5);
        team.setDayNum(team.getDayNum() + 1);

        team.getCardSet().forEach(card -> card.setPersonsCount(0));

        int storyNum = 0;
        var newCards = generateNewCards(team, RANDOM.nextInt(8) + 1, Card.ColumnType.QUEUE, storyNum);
        cardDao.saveAll(newCards);

        return BoardDto.builder()
                .teamId(team.getId())
                .day(team.getDayNum())
                .analyticsFreePersons(team.getFreeAnalyticsPersons())
                .developmentFreePersons(team.getFreeDevelopmentPersons())
                .testFreePersons(team.getFreeTestingPersons())
                .cards(mapCardsToDto(Stream.of(newCards, team.getCardSet()).flatMap(Collection::stream).collect(Collectors.toList())))
                .build();
    }

    private List<Card> generateNewCards(Team team,
                                        Integer randomNum,
                                        Card.ColumnType columnType,
                                        Integer initialStoryNum) {
        Integer initialNum = initialStoryNum;
        var cards = new ArrayList<Card>();
        var priority = 0;
        for (int i = 0; i < randomNum; i++) {
            cards.add(Card.builder()
                    .analyticCompleted(0)
                    .analyticRemaining(RANDOM.nextInt(20) + 1)
                    .title("Story " + initialNum)
                    .businessValue(RANDOM.nextInt(60) + 1)
                    .columnType(columnType)
                    .developCompleted(0)
                    .developRemaining(RANDOM.nextInt(20) + 1)
                    .testingCompleted(0)
                    .testingRemaining(RANDOM.nextInt(20) + 1)
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
                .ordering(card.getPriority())
                .analyticCompleted(card.getAnalyticCompleted())
                .analyticRemaining(card.getAnalyticRemaining())
                .developCompleted(card.getDevelopCompleted())
                .developRemaining(card.getDevelopRemaining())
                .testingCompleted(card.getTestingCompleted())
                .testingRemaining(card.getTestingRemaining())
                .readyDay(card.getReadyDay())
                .personsAmount(card.getPersonsCount())
                .frontColumnType(card.getFrontValue())
                .build())
                .collect(Collectors.toList());
    }

    private Card.ColumnType getColumnType(Integer columnNumber) {
        if (columnNumber == 0) return Card.ColumnType.QUEUE;
        if (columnNumber < 3) return Card.ColumnType.ANALYTICS;
        if (columnNumber < 5) return Card.ColumnType.DEVELOPMENT;
        if (columnNumber < 7) return Card.ColumnType.TESTING;
        return Card.ColumnType.COMPLETED;
    }

    private Card.ColumnStatus getColumnStatus(Integer columnNumber) {
        if (columnNumber % 2 == 1) return Card.ColumnStatus.IN_PROGRESS;
        return Card.ColumnStatus.FINISHED;
    }
}
