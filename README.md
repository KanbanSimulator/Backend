# Backend 
## project structure
All files related to backend are in inno.kanban.KanbanSimulator package
There 8 main packages:
1) config - all related to web and swagger configuration
2) controller - all controller logic
3) dao - data access object package
4) dto - data transfer object packages
5) exception - package with different exception
6) model - all database entities
7) service - classes with buissiness logic
8) utils - utility classes
### Controllers
1) RoomController 
   1) createRoom - method to create the room
   2) joinRoom - method to join the room
   3) startTheGame - method to start the game (available for room creator)
   4) checkRoomState - method to check the room state (used for long polling from front end)
2) BoardController
   1) getOrCreateBoard - method for getting the board for team if existing, otherwise creates the new board
   2) startDay - method to start a new day
   3) movePerson - method to move the person from card or to card
   4) moveCard - method to move card from column to column or in one column
   5) setWipLimit - method to set work in progress limit
   6) getWipLimit - method to get current work in progress limit
3) StatisticsController
   1) getStatistics - method to get statistics when the game ended
   
All methods and dtos are described more precisely in [swagger](https://peaceful-cove-23510.herokuapp.com/swagger-ui/index.html) 
