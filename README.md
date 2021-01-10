## Assignment 3 - Spring MVC

- Andrei G. Taraboi: 829904
- Alice Romagnoli: 829833

Link alla <a href="https://gitlab.com/conerns/2020_assignment3_frontend"> repository</a>.

Si è deciso di sviluppare un progetto Spring-MVC utilizzando l'IDE Eclipse.

### Strumenti
- JDK: `15.0.1`
- Maven: `apache-maven-3.6.3`
- SpringBoot: `2.4.0`

### Avviamento applicazione
Scaricare la repository tramite SSH o HTTPS, spostarsi nella cartella `assignment3` ed eseguire il comando:
```
mvnw spring-boot:run
```
In seguito andare all'indirizzo `localhost:8080`.

### Applicazione
Lo scopo principale è quello di permettere a un amministratore di gestire completamente tutto ciò che riguarda il corretto funzionamento e l'organizzazione di partite (raggrupate in tornei e mayor) per diversi videogiochi.
Deve inoltre essere possibile visualizzare e ispezionare tutte le squadre che hanno partecipato o parteciperanno a partite presenti nell'in-memory database, in base alla tipologia/categoria di squadra e i relativi giocatori.

### Diagramma ER
<img src="https://i.ibb.co/KGZFsTr/diagramma-nuovo.png" width="355" height="502">
