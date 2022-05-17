package inno.kanban.KanbanSimulator.service.impl;

import inno.kanban.KanbanSimulator.dao.CardDao;
import inno.kanban.KanbanSimulator.dao.PlayerRepository;
import inno.kanban.KanbanSimulator.dao.RoomRepository;
import inno.kanban.KanbanSimulator.dao.TeamRepository;
import inno.kanban.KanbanSimulator.dto.*;
import inno.kanban.KanbanSimulator.exception.CardNotFoundException;
import inno.kanban.KanbanSimulator.exception.KanbanException;
import inno.kanban.KanbanSimulator.exception.RoomNotFoundException;
import inno.kanban.KanbanSimulator.exception.TeamNotFoundException;
import inno.kanban.KanbanSimulator.model.Card;
import inno.kanban.KanbanSimulator.model.Player;
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
    private final RoomRepository roomRepository;

    private final Random RANDOM = new Random();

    private static int PERSONS_AMOUNT = 5;


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

    @Override
    @Transactional
    public WipLimitDto setWipLimit(Long teamId, WipLimitDto wipLimitDto) {
        var team = teamRepository.findById(teamId)
                .orElseThrow(() -> new TeamNotFoundException(teamId));

        team.setWip1(wipLimitDto.getWip1());
        team.setWip2(wipLimitDto.getWip2());
        team.setWip3(wipLimitDto.getWip3());
        return WipLimitDto.builder()
                .wip1(team.getWip1())
                .wip2(team.getWip2())
                .wip3(team.getWip3())
                .build();
    }

    @Override
    public WipLimitDto getWipLimit(Long teamId) {
        var team = teamRepository.findById(teamId)
                .orElseThrow(() -> new TeamNotFoundException(teamId));

        return WipLimitDto.builder()
                .wip1(team.getWip1())
                .wip2(team.getWip2())
                .wip3(team.getWip3())
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

        Card currentCard = null;
        Card prevCard = null;
        if (movePersonDto.getCurrentCard() != null) {
            currentCard = cardDao.findById(movePersonDto.getCurrentCard())
                    .orElseThrow(() -> new CardNotFoundException(movePersonDto.getCurrentCard()));
        }

        if (movePersonDto.getPrevCard() != null) {
            prevCard = cardDao.findById(movePersonDto.getPrevCard())
                    .orElseThrow(() -> new CardNotFoundException(movePersonDto.getPrevCard()));
        }

        if (prevCard == null && currentCard != null) {
            switch (currentCard.getColumnType()) {
                case TESTING: {
                    if (team.getFreeTestingPersons() > 0) {
                        team.setFreeTestingPersons(team.getFreeTestingPersons() - 1);
                        currentCard.setPersonsCount(currentCard.getPersonsCount() + 1);
                    }
                    break;
                }
                case ANALYTICS: {
                    if (team.getFreeAnalyticsPersons() > 0) {
                        team.setFreeAnalyticsPersons(team.getFreeAnalyticsPersons() - 1);
                        currentCard.setPersonsCount(currentCard.getPersonsCount() + 1);
                    }
                    break;
                }
                case DEVELOPMENT: {
                    if (team.getFreeDevelopmentPersons() > 0) {
                        team.setFreeDevelopmentPersons(team.getFreeDevelopmentPersons() - 1);
                        currentCard.setPersonsCount(currentCard.getPersonsCount() + 1);
                    }
                    break;
                }
                default: throw new KanbanException("Wrong input");
            }
        } else if (currentCard == null && prevCard != null) {
            switch (prevCard.getColumnType()) {
                case TESTING: {
                    if (team.getFreeTestingPersons() < PERSONS_AMOUNT && prevCard.getPersonsCount() > 0) {
                        team.setFreeTestingPersons(team.getFreeAnalyticsPersons() + 1);
                        prevCard.setPersonsCount(prevCard.getPersonsCount() - 1);
                    }
                    break;
                }
                case ANALYTICS: {
                    if (team.getFreeAnalyticsPersons() < PERSONS_AMOUNT && prevCard.getPersonsCount() > 0) {
                        team.setFreeAnalyticsPersons(team.getFreeAnalyticsPersons() + 1);
                        prevCard.setPersonsCount(prevCard.getPersonsCount() - 1);
                    }
                    break;
                }
                case DEVELOPMENT: {
                    if (team.getFreeDevelopmentPersons() < PERSONS_AMOUNT && prevCard.getPersonsCount() > 0) {
                        team.setFreeDevelopmentPersons(team.getFreeDevelopmentPersons() + 1);
                        prevCard.setPersonsCount(prevCard.getPersonsCount() - 1);
                    }
                    break;
                }
                default: throw new KanbanException("Wrong input");
            }
        } else {
            if (prevCard.getPersonsCount() > 0) {
                prevCard.setPersonsCount(prevCard.getPersonsCount() - 1);
                currentCard.setPersonsCount(currentCard.getPersonsCount() + 1);
            }
        }

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
                    .day(team.getDayNum())
                    .analyticsFreePersons(team.getFreeAnalyticsPersons())
                    .developmentFreePersons(team.getFreeDevelopmentPersons())
                    .testFreePersons(team.getFreeTestingPersons())
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
            card.setAnalyticCompleted(card.getAnalyticRemaining());
            card.setAnalyticRemaining(0);
        });
        cardDao.saveAll(devCards);
        storyNum += devCards.size();
        var testCards = generateNewCards(team, RANDOM.nextInt(3) + 1, Card.ColumnType.TESTING, storyNum);
        testCards.forEach(card -> {
            card.setAnalyticCompleted(card.getAnalyticRemaining());
            card.setDevelopCompleted(card.getDevelopRemaining());
            card.setAnalyticRemaining(0);
            card.setDevelopRemaining(0);
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
    @Transactional
    public BoardDto startDay(Long teamId) {
        var team = teamRepository.findById(teamId)
                .orElseThrow(() -> new TeamNotFoundException(teamId));

        team.setFreeDevelopmentPersons(5);
        team.setFreeAnalyticsPersons(5);
        team.setFreeTestingPersons(5);
        team.setDayNum(team.getDayNum() + 1);

        var cards = team.getCardSet();
        cards.forEach(this::progressCard);
        cards.forEach(card -> card.setPersonsCount(0));
        cardDao.saveAll(cards);

        int storyNum = cards.size();
        List<Card> newCards = Collections.emptyList();
        if (team.getDayNum() % 10 == 0) {
            newCards = generateNewCards(team, RANDOM.nextInt(8) + 1, Card.ColumnType.QUEUE, storyNum);
            cardDao.saveAll(newCards);
        }

        return BoardDto.builder()
                .teamId(team.getId())
                .day(team.getDayNum())
                .analyticsFreePersons(team.getFreeAnalyticsPersons())
                .developmentFreePersons(team.getFreeDevelopmentPersons())
                .testFreePersons(team.getFreeTestingPersons())
                .cards(mapCardsToDto(Stream.of(newCards, cards).flatMap(Collection::stream).collect(Collectors.toList())))
                .build();
    }

    @Override
    @Transactional(readOnly = true)
    public StatisticsDto getStatistics(Long roomId) {

        var room = roomRepository.findById(roomId)
                .orElseThrow(() -> new RoomNotFoundException(roomId));

        var teams = room.getTeamSet();

        return StatisticsDto.builder()
                .data(teams.stream()
                        .map(team -> StatisticDto.builder()
                                .teamNumber(team.getNumber())
                                .members(team.getPlayerSet().stream()
                                        .map(Player::getName)
                                        .collect(Collectors.toList()))
                                .value(team.getCardSet().stream()
                                        .filter(card -> card.getColumnType() == Card.ColumnType.TESTING && card.getColumnStatus() == Card.ColumnStatus.FINISHED)
                                        .mapToInt(Card::getBusinessValue)
                                        .sum())
                                .finished(team.getDayNum() >= 25)
                                .build())
                        .collect(Collectors.toList()))
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
                    .blockerRemaining(RANDOM.nextInt(100) > 90 ? RANDOM.nextInt(20) + 1 : 0)
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
                .blockerCompleted(card.getBlockerCompleted())
                .blockerRemaining(card.getBlockerRemaining())
                .readyDay(card.getReadyDay())
                .personsAmount(card.getPersonsCount())
                .frontColumnType(card.getFrontValue())
                .build())
                .collect(Collectors.toList());
    }

    private static Integer WORK_CONST = 5;
    private static Integer BLOCKER_CONST = 8;

    private void progressCard(Card card) {
        if (card.getColumnStatus() == Card.ColumnStatus.IN_PROGRESS) {
            if (card.getPersonsCount() > 0) {
                if (card.getBlockerRemaining() > 0) {
                    var blockerTotal = card.getBlockerCompleted() + card.getBlockerRemaining();
                    card.setBlockerCompleted(card.getBlockerCompleted() + BLOCKER_CONST);
                    if (card.getBlockerCompleted() >= blockerTotal) {
                        card.setBlockerCompleted(blockerTotal);
                    }
                    card.setBlockerRemaining(blockerTotal - card.getBlockerCompleted());
                }
                switch (card.getColumnType()) {
                    case ANALYTICS: {
                        var total = card.getAnalyticCompleted() + card.getAnalyticRemaining();
                        card.setAnalyticCompleted((card.getAnalyticCompleted() + card.getPersonsCount() * WORK_CONST));
                        if (card.getAnalyticCompleted() >= total) {
                            card.setAnalyticCompleted(total);
                            card.setColumnStatus(Card.ColumnStatus.FINISHED);
                        }
                        card.setAnalyticRemaining(total - card.getAnalyticCompleted());
                        break;
                    }
                    case DEVELOPMENT: {
                        var total = card.getDevelopCompleted() + card.getDevelopRemaining();
                        card.setDevelopCompleted((card.getDevelopCompleted() + card.getPersonsCount() * WORK_CONST));
                        if (card.getDevelopCompleted() >= total) {
                            card.setDevelopCompleted(total);
                            card.setColumnStatus(Card.ColumnStatus.FINISHED);
                        }
                        card.setDevelopRemaining(total - card.getDevelopCompleted());
                        break;
                    }
                    case TESTING: {
                        var total = card.getTestingCompleted() + card.getTestingRemaining();
                        card.setTestingCompleted((card.getTestingCompleted() + card.getPersonsCount() * WORK_CONST));
                        if (card.getTestingCompleted() >= total) {
                            card.setTestingCompleted(total);
                            card.setColumnStatus(Card.ColumnStatus.FINISHED);
                        }
                        card.setTestingRemaining(total - card.getTestingCompleted());
                        break;
                    }
                }
            }
        }
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
