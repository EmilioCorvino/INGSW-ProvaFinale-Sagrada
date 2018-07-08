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

## Instructions to run INGSW-ProvaFinale-Sagrada.jar
First of all, decompress the `.jar`. It has been compressed and split in two files using 7-Zip (it can be downloaded it at https://www.7-zip.org/). However any file archiver should be able to decompress the archive. This has to be done both for server's and client's jars.
#### To start the server
##### On Windows:
Move to the directory `Deliverables/jar/INGSW_ProvaFinale_SagradaServer_jar` and double click on `startServer.bat`.
Alternatively, open a shell in the directory and type `java -jar INGSW-ProvaFinale-Sagrada.jar`.

##### On macOS and Linux:
Open a terminal in the directory containing the jar (`Deliverables/jar/INGSW_ProvaFinale_SagradaServer_jar`) and then type `java -jar INGSW-ProvaFinale-Sagrada.jar`.

#### To start the client
##### On Windows: 
First, move to the directory `Deliverables/jar/INGSW_ProvaFinale_SagradaClient_jar`, then depending on the graphical interface of preference:
* _CLI:_ double click on `playSagradaWindowsCLI.bat`
  * This will work only if Ubuntu bash on Windows is installed and configured on your machine, which is highly reccommended to visualize the formatting correctly. Follow the guide at the link https://github.com/michele-bertoni/W10JavaCLI to do so (from point 2 to 5). If there is no interest about the formatting, the jar can be opened as usual with `java -jar INGSW-ProvaFinale-Sagrada.jar` using Windows PowerShell or cmd.
* _GUI:_ double click on `playSagradaGUI.bat`

If that does not work, open a shell in the directory containing the `.jar` (`Deliverables/jar/INGSW_ProvaFinale_SagradaClient_jar`) and then type `java -jar INGSW-ProvaFinale-Sagrada.jar`. After that, thw program will ask which graphical interface to use.

##### On macOS and Linux:
Open a terminal in the directory containing the jar (`Deliverables/jar/INGSW_ProvaFinale_SagradaClient_jar`) and then type `java -jar INGSW-ProvaFinale-Sagrada.jar`. After that, the program will ask which graphical interface to use.
Some problems may occurr if the GUI is run on linux if the right GTK libraries are not installed.

## Configuration
There are some settings that can be changed from `conf/sagrada.properties` (located inside both client and server directories). In particular:
* _RMI port:_ set this to a valid port number on both server and client to use a different port from the default one.
* _Room timer:_ this value represents the amount of time (in milliseconds) players will wait until the match starts, after at least two players are connected. It can be changed to any arbitrary number.
* _Turn timer:_ this value represents the amount of time (in milliseconds) each player has to complete a turn. After that, if their turn still isn't over, they will be suspended. It can be changed to any arbitrary number.
Note that twice the amount of this timer is used by the client as a time out for the connection (it is refreshed at any server notification).
* _Last round:_ this value represents the number of rounds the match is going to last. However, note that setting this value to anything more than 10 will not make the GUI represent the extra rounds.
  * NOTE THAT THE PROPERTIES (AT LEAST `last.round` AND `rmi.port`) MUST BE THE SAME IN BOTH `sagrada.properties` FILES IN CLIENT AND SERVER DIRECTORIES.
