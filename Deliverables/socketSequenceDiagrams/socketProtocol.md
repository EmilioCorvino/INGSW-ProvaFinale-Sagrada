#Socket Protocol

##Initialization
####Login:
* Client: loginRequest(playerName: String, ip: String, port: int, gameMode: String) ---> Server

  * Server: showRoom(showRoom(players: String[1..4]) ---> Client
  
####Board initialization:
* Server: windowPatternCardSelection(windowPattern1: WindowPatternCard [2], windowPattern2: WindowPatternCard [2]) ---> Client
  * Client: windowPatternCardRequest(id: int, side: String) ---> Server
    * Server: showInitializedBoard(board: CommonBoard) ---> Client

##Gameplay
####Default move:
* Client: defaultMoveRequest() ---> Server
  * Server: showAvailableDice(idSources: int[1..n]) ---> Client
    * Client: placementMoveRequest(idSourceContainer: int, idSource: int, sourceIndex: int, idDestinationContainer: int, idDestination: int, destinationIndex: int) ---> Server
      * Server: showPlacementMove(idSourceContainer: int, idSource: int, sourceIndex: int, idDestinationContainer: int, idDestination: int, destinationIndex: int) ---> Client

####Tool card usage:
* Client: toolMoveRequest() ---> Server
  * Server: showAvailableTool(idSlots: int[0..n]) —-> Client
    * Client: toolSlotRequest(slotId: int) ---> Server
    
    _At this point, if needed, server can ask supplementary commands..._
     * Server: showSupportCommands(idCommander: int, idCommands: int[1..n]) ---> Client
      
      _...and the client can answer in different ways, depending on the tool card:_
       * Client: increaseDieValueRequest(dieValue: int) ---> Server
         * Server: showValue(newValue: int) ---> Client
      
     _or_
       * Client: decreaseDieValueRequest(dieValue: int) ---> Server
         * Server: showValue(newValue: int) ---> Client
         
    _or_
      * Client: diceToRollRequest(idSourceContainer: int) ---> Server
        * Server: showValues(idDestinationContainer: int, newValues: int[1..n]) ---> Client
      
    _or (the sourceIndex is the specific die in one slot in the round track, since there can be more than one; it's 0 if the container isn't the round track)_
      * Client: dieToDraftRequest(idSourceContainer: int, idSource: int, idIndex: int) ---> Server
        * Server: showDraftedValue(idSourceContainer: int, idSource: int, idIndex: int, newValue: int) ---> Client
        
    _In the end, a placement has to be done:_
      * Client: placementMoveRequest(idSourceContainer: int, idSource: int, sourceIndex: int, idDestinationContainer: int, idDestination: int, destinationIndex: int) ---> Server
        * Server: showPlacementMove(idSourceContainer: int, idSource: int, sourceIndex: int, idDestinationContainer: int, idDestination: int, destinationIndex: int) ---> Client
      
##End Game
####Ranking and Exit/NewGame:
* Server: showRank(players: String[1..4], scores: int[1..4]) ---> Client

  _Client can now exit the game..._

  * Client: exitGameRequest() ---> Server

  _...or start a new game_

  * Client: startNewGameRequest() ---> Server
    * Server: showRoom(players: String[1..4]) ---> Client
