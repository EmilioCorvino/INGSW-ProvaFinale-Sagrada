# INGSW-ProvaFinale-Sagrada

Development of a Java application based on the board game Sagrada, by FloodgateGames, 
as a final project for the Software Engineering course at Politecnico di Milano.

<p align="center">
  <img src="https://github.com/Cr0w19/INGSW-ProvaFinale-Sagrada/blob/Model-Development/src/main/resources/Logo.jpg" alt="Sagrada logo">
</p>

*Group ID:* LM30

*Group members:*
+ 10482725 ADESSO Rita
+ 10504531 CORVINO Emilio
+ 10481786 DRAPPO Gianluca

##Instructions to run INGSW-ProvaFinale-Sagrada.jar
#####On Windows: 
* _CLI:_ double click on playSagradaWindowsCLI.bat
* _GUI:_ double click on playSagradaGUI.bat

If that does not work, open a shell in the directory containing the .jar and then type java -jar INGSW-ProvaFinale-Sagrada.jar
and you will be able to choose which graphical interface to use.

#####On macOS and Linux:
Just open a terminal in the directory containing the jar and then type java -jar INGSW-ProvaFinale-Sagrada.jar and you will be able to choose which graphical interface to use.
You may encounter some problems if you want to run the GUI on linux if you haven't got GTK libraries installed.

##Configuration
There are some settings that can be changed from conf/sagrada.properties. In particular:
* _RMI port:_ set this to a valid port number on both server and client to use a different port from the default one we chose.
* _Room timer:_ this value represents the amount of time (in milliseconds) players will wait until the match starts, after at least two players are connected. You can change it to an arbitrary number.
* _Turn timer:_ this value represents the amount of time (in milliseconds) each player has to complete a turn. After that, if their turn still isn't over, they will be suspended. You can change it to an arbitrary number.
Note that the double of this timer is used by the client as a time out for the connection (it is refreshed at any server notification).
* _Last round:_ this value represents the number of rounds the players will play. However, note that setting this value to anything more than 10 will not make the GUI represent the extra rounds.
