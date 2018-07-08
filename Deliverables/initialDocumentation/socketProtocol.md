# Socket Protocol

## Initialization
#### Login:
* Client: login(playerName: String, ip: String, gameMode: String) ---> Server

  * Server: showRoom(showRoom(players: String[1..4]) ---> Client
  
#### Board initialization:
* Server: showPrivateObjective(id: int) ---> Client
* Server: showMapsToChose(list: WpList<SimplifiedWindowPatternCard>) ---> Client
* Server: showCommand(availableCommands: List<Commands> ) ---> Client
  * Client: windowPatternCardRequest(id: int, side: String) ---> Server
    * Server: setCommonBoard(players: Map<String, SimplifiedWindowPatternCard>, idPubObj: int[], idTool: int[]) ---> Client
    * Server: setDraft(draft: List<SetUpInformationUnit>) ---> Client
    * Server: setFavorToken(nFavTokens: int) ---> Client

## Gameplay

_At first the Server send all the availbale commands to the user_
* Server: showCommand(availableCommands: List<Commands> ) ---> Client

_Then the user can do different things:_
#### Default move:
* Client: performDefaultMove(infoUnit: SetUpInformationUnit) ---> Server
	* Server: addOnOwnWp(infoUnit: SetUpInformationUnit) ---> Client
	* Server: removeOnDraft(infoUnit: SetUpInformationUnit) ---> Client

#### Tool card usage:

_For tools with single call:_
* Client: performToolCardMove(infoUnit: SetUpInformationUnit) ---> Server
	* Server: updateFavTokenPlayer(nFavorToken: int) ---> Client
	* Server: updateToolCost(idSlot: int, cost: int) ---> Client
	
_For tools with a second call the following is also needed after the first answer:_
* Client: performRestrictedPlacement(infoUnit: SetUpInformationUnit) ---> Server
	* Server: updateFavTokenPlayer(nFavorToken: int) ---> Client
	* Server: updateToolCost(idSlot: int, cost: int) ---> Client
	
_At this point server answers with a combination of the methods below depending on the tool:_

   * Server: addOnOwnWp(infoUnit: SetUpInformationUnit) ---> Client
   * Server: removeOnOwnWp(infoUnit: SetUpInformationUnit) ---> Client
   * Server: addOnOtherPlayerWp(userName: String, infoUnit: SetUpInformationUnit)  ---> Client
   * Server: removeOnOtherPlayerWp(userName: String, infoUnit: SetUpInformationUnit) ---> Client
   * Server: addOnDraft(infoUnit: SetUpInformationUnit) ---> Client
   * Server: removeOnDraft(infoUnit: SetUpInformationUnit) ---> Client
   * Server: addOnRoundTrack(infoUnit: SetUpInformationUnit) ---> Client
   * Server: removeOnRoundTrack(infoUnit: SetUpInformationUnit) ---> Client
   * Server: showDie(infoUnit: SetUpInformationUnit) ---> Client

#### During the turn:
_If the user want to pass:_
* Client: moveToNextTurn() ---> Server

_If the user has been suspended and wants to reconnect:_
* Client: reconnect() ---> Server
	* Server: showPrivateObjective(id: int) ---> Client
    	* Server: setCommonBoard(players: Map<String, SimplifiedWindowPatternCard>, idPubObj: int[], idTool: int[]) ---> Client
    	* Server: setDraft(draft: List<SetUpInformationUnit>) ---> Client
	* Server: setFavorToken(nFavorToken: int) ---> Client
	* Server: updateToolCost(idSlot: int, cost: int) ---> Client
	* Server: setRestoredWindowPatternCards(diceToRestore: Map<String, List<SetUpInformationUnit>>) ---> Client
	* Server: setRestoredRoundTrack(roundTrackToRestore: List<ArrayList<SetUpInformationUnit>>) ---> Client
	

_If the user want to logout:_
* Client: exitGame() ---> Server
	* Server: forceLogOut() ---> Client
	

  
## End Game
#### Ranking and Exit/NewGame:
* Server: showRank(players: String[1..4], scores: int[1..4]) ---> Client
* Server: showCommand(availableCommands: List<Commands> ) ---> Client

  _Client can now exit the game..._

  * Client: exitGame() ---> Server
	* Server: forcedLogout() ---> Client

  _...or start a new game_

  * Client: startNewGameRequest() ---> Server
    * Server: showRoom(players: String[1..4]) ---> Client

## Extra
_If at any time the server needs to send a message to the client:_ 
* Server: showNotice(message: String) ---> Client