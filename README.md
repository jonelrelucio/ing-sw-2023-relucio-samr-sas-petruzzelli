
# Progetto INGSW 2022-2023

Game Implementation [My Shelfie](https://www.craniocreations.it/prodotto/my-shelfie/)




<img src="src/main/resources/view/gui/publisher_material/box_noShadow.png" align="right" width="200" alt="My Shelfie" >

### Group GC25 Components:
- [Jonel Relucio](https://github.com/jonelrelucio)
- [Dalila Samr](https://github.com/DalilaPolimi)
- [Lucian Sas](https://github.com/LucianSasPolimi) 
- [Alessandro Petruzzelli](https://github.com/AlessandroPetruzzelli) 

### Diagramma UML
UML Diagrams have been develped using [diagrams](https://www.diagrams.net/).  
Main UML Diagram: [UML Diagram](src/deliverables/umlDiagram/ClassDiagram_Model.png)



### Functionality
| Functionality                | State |
|:-----------------------------|:-----:|
| Basic rules                  |  🟢   |
| Complete rules               |  🟢   |
| RMI                          |  🟢   |
| Socket                       |  🟢   |
| CLI                          |  🟢   |
| GUI                          |  🟢   |
| Multiple games               |  🔴   |
| Persistence                  |  🔴   |
| Resilience to disconnections |  🔴   |
| Chat                         |  🟢   |


##### Legend
🔴 Not Implemented | 🟢 Implemented |🟡 Being Implemented...


## How to run the jar files
Before running either server-softeng-gc25.jar or client-softeng-gc25.jar be sure to have disabled the firewall for both of them.

##### Server
To run the server type: java -Djava.rmi.server.hostname=serverIp -jar server-softeng-gc25.jar
where serverIp is the Ip Address of the server
When the server is running, choose the id (int value) that corresponds to the serverIp used running the jar file.

##### Client
To run the client type: java -jar client-softeng-gc25.jar
when the client is running, set the ip address of the server interface.
Then select the the id (int value) that corresponds to the ip address of your chosen NIC


















