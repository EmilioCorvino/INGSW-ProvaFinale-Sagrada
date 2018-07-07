#Socket Protocol

##Initialization
####Login:
* Client: login(playerName: String, ip: String, gameMode: String) ---> Server

  * Server: showRoom(showRoom(players: String[1..4]) ---> Client
  
####Board initialization:
* Server: showPrivateObjective(id: int) ---> Client
* Server: showMapsToChose(list: WpList<SimplifiedWindowPatternCard>) ---> Client
* Server: showCommand(availableCommands: List<Commands> ) ---> Client
  * Client: windowPatternCardRequest(id: int, side: String) ---> Server
    * Server: setCommonBoard(players: Map<String, SimplifiedWindowPatternCard>, idPubObj: int[], idTool: int[]) ---> Client
    * Server: setDraft( draft: List<SetUpInformationUnit>) ---> Client
    * Server: setFavorToken(nFavTokens: int) ---> Client

##Gameplay
####Default move:
* Server: showCommand(availableCommands: List<Commands> ) ---> Client
	* Client: performDefaultMove(infoUnit: SetUpInformationUnit) ---> Server
		* Server: addOnOwnWp(SetUpInformationUnit unit) ---> Client
		* Server: removeOnDraft(SetUpInformationUnit info) ---> Client

####Tool card usage:
* Server: showCommand(availableCommands: List<Commands> ) ---> Client

	_for simple tool_
	* Client: performToolCardMove(infoUnit: SetUpInformationUnit) ---> Server
	
	_for tool with multiple calls_
	* Client: performRestrictedPlacement(SetUpInformationUnit infoUnit) ---> Server
	

		_At this point server answer with combination of method below depending of the tool_

		* Server: addOnOwnWp(SetUpInformationUnit unit) ---> Client
		* Server: removeOnOwnWp(SetUpInformationUnit unit) ---> Client
		* Server: addOnOtherPlayerWp(String userName, SetUpInformationUnit infoUnit)  ---> Client
		* Server: removeOnOtherPlayerWp(String userName, SetUpInformationUnit infoUnit) ---> Client
		* Server: addOnDraft(SetUpInformationUnit info) ---> Client
		* Server: removeOnDraft(SetUpInformationUnit info) ---> Client
		* Server: addOnRoundTrack(SetUpInformationUnit info) ---> Client
		* Server: removeOnRoundTrack(SetUpInformationUnit info) ---> Client
		* Server: showDie(SetUpInformationUnit informationUnit) ---> Client
  
##End Game
####Ranking and Exit/NewGame:
* Server: showRank(players: String[1..4], scores: int[1..4]) ---> Client

  _Client can now exit the game..._

  * Client: exitGame() ---> Server

  _...or start a new game_

  * Client: startNewGameRequest() ---> Server
    * Server: showRoom(players: String[1..4]) ---> Client
