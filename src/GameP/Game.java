

/**
 *  This class is the main class of the "World of Zuul" application. 
 *  "World of Zuul" is a very simple, text based adventure game.  Users 
 *  can walk around some scenery. That's all. It should really be extended 
 *  to make it more interesting!
 * 
 *  To play this game, create an instance of this class and call the "play"
 *  method.
 * 
 *  This main class creates and initialises all the others: it creates all
 *  rooms, creates the parser and starts the game.  It also evaluates and
 *  executes the commands that the parser returns.
 * 
 * @author  Michael Kölling and David J. Barnes
 * @version 2016.02.29
 */

public class Game 
{
    private Parser parser;
    private Room currentRoom;
    private Diploma diploma;
    private Monstro cachorro;

    /**
     * Create the game and initialise its internal map.
     */
    public Game() 
    {
        createRooms();
        parser = new Parser();
        diploma = new Diploma();
        cachorro = new Monstro();
    }


    public Game(Parser parser2, Room room) {
    }

    /**
     * Create all the rooms and link their exits together.
     */
    private void createRooms()
    {
        Room entrada, saida, no1, no2, no3, no4, no5, no6, no7, patio, matinho, did1, did2, did3, did4, did5, did6;
      
        // create the rooms
        entrada = new Room("na entrada da ufs");
        saida = new Room("saida da UFS");
        no1 = new Room("no inicio do corredor principal.");
        no2 = new Room("no corredor principal entre a did 1 e 6");
        no3 = new Room("no corredor principal entre a did 2 e a entrada");
        no4 = new Room("no corredor principal frente à did 5");
        no5 = new Room("no corredor principal frente ao matinho");
        no6 = new Room("no corredor principal frente ao pátio");
        no7 = new Room("no fim do corredor principal.");
        patio = new Room("no pátio entre a did 3, 4 e o corredor principal.");
        matinho = new Room("no matinho entre o corredor e a saída");

        did1 = new Room("didática 1");
        did2 = new Room("didática 2");
        did3 = new Room("didática 3");
        did4 = new Room("didática 4");
        did5 = new Room("didática 5");
        did6 = new Room("didática 6");
       
        // initialise room 
        entrada.setExit("south", no3);

        saida.setExit("south", matinho);
        saida.setExit("north", null);
        saida.setExit("west", null);
        saida.setExit("east", null);

        no1.setExit("east", no2);

        no2.setExit("west", no1);
        no2.setExit("east", no3);
        no2.setExit("south", did1);
        no2.setExit("north", did6);

        no3.setExit("west", no2);
        no3.setExit("east", no4);
        no3.setExit("south", did2);
        no3.setExit("north", entrada);

        no4.setExit("west", no3);
        no4.setExit("east", no5);
        no4.setExit("north", did5);

        no5.setExit("west", no4);
        no5.setExit("east", no6);
        no5.setExit("north", matinho);

        no6.setExit("west", no5);
        no6.setExit("east", no7);
        no6.setExit("south", patio);

        no7.setExit("west", no6);

        patio.setExit("east", did4);
        patio.setExit("west", did3);
        patio.setExit("north", no6);

        matinho.setExit("south", no5);
        matinho.setExit("north", saida);

        did1.setExit("north", no2);
        did2.setExit("north", no3);
        did3.setExit("east", patio);
        did4.setExit("west", patio);
        did5.setExit("south", no4);
        did6.setExit("south", no2);
        
        currentRoom = entrada;  // start game outside
    }

    /**
     *  Main play routine.  Loops until end of play.
     */
    public void play(Game jogo) 
    {            
        printWelcome();

        // Enter the main command loop.  Here we repeatedly read commands and
        // execute them until the game is over.
                
        boolean finished = false;
        while (! finished) {
            jogo.colocaNaSala();
            jogo.monsterKill();
            jogo.atualizaMonstro();
            Command command = parser.getCommand();
            finished = processCommand(command);
        }
        if (cachorro.vidaOUmorte == 0) {
            System.out.println("GAME OVER\n");
        }
        else{
            System.out.println("Parabéns, você se formou com sucesso !!\n");
        }
    }

    /**
     * Print out the opening message for the player.
     */
    private void printWelcome()
    {
        System.out.println();
        System.out.println("Bem vindo(a) ao Escape UFS.");
        System.out.println("Escape UFS é um jogo em que você deve achar o diploma para conseguir sair da ufs.");
        System.out.println("Ao achar o diploma, e achar a saída, é só ir para qualquer direção que estará livre e o jogo será zerado.");
        System.out.println("O diploma estará escondido em algum lugar aleatório do mapa da UFS.");
        System.out.println("No mapa do jogo há um corredor principal, nele um cachorro raivoso fica vagando e mudando de posição a cada rodada");
        System.out.println("Não seja pego(a) pelo cachorro!");
        System.out.println("Digite '" + CommandWord.HELP + "' se precisar de ajuda");
        System.out.println();
        System.out.println("Objetivo 1: ACHAR O DIPLOMA;\nObjetivo 2: ESCAPAR DA UFS");
        System.out.println();
        System.out.println(currentRoom.getLongDescription());
    }

    /**
     * Given a command, process (that is: execute) the command.
     * @param command The command to be processed.
     * @return true If the command ends the game, false otherwise.
     */
    private boolean processCommand(Command command) 
    {
        boolean wantToQuit = false;

        CommandWord commandWord = command.getCommandWord();

        if ((diploma.local == true) && (currentRoom.description == "saida da UFS")) {
            return wantToQuit = true;
        }

        if (cachorro.vidaOUmorte == 0) {
            return wantToQuit = true;
        }

        switch (commandWord) {
            case UNKNOWN:
                System.out.println("Esperou no mesmo lugar por esta rodada!!");
                break;

            case HELP:
                printHelp();
                break;

            case GO:
                goRoom(command);
                break;

            case QUIT:
                wantToQuit = quit(command);
                cachorro.vidaOUmorte = 0;
                break;
        }
        return wantToQuit;
    }

    // implementations of user commands:

    /**
     * Print out some help information.
     * Here we print some stupid, cryptic message and a list of the 
     * command words.
     */
    private void printHelp() 
    {
        System.out.println();
        System.out.println("Você ta perdido(a) e sozinho(a) na universidade...");
        System.out.println("Não seja pego(a) pelo cachorro.");
        System.out.println("Ao achar o diploma e chegar na saída vá para qualquer direção que sairá da UFS.");
        System.out.println("Seus comandos são:");
        parser.showCommands();
        System.out.println();
    }

    /** 
     * Try to go in one direction. If there is an exit, enter the new
     * room, otherwise print an error message.
     */
    private void goRoom(Command command) 
    {
        if(!command.hasSecondWord()) {
            // if there is no second word, we don't know where to go...
            System.out.println("Go where?");
            return;
        }

        String direction = command.getSecondWord();

        // Try to leave current room.
        Room nextRoom = currentRoom.getExit(direction);

        if (nextRoom == null) {
            System.out.println("Se bateu na parede!!");
        }
        else {
            currentRoom = nextRoom;
            System.out.println(currentRoom.getLongDescription());
        }
    }

    /** 
     * "Quit" was entered. Check the rest of the command to see
     * whether we really quit the game.
     * @return true, if this command quits the game, false otherwise.
     */
    private boolean quit(Command command) 
    {
        if(command.hasSecondWord()) {
            System.out.println("Quit what?");
            return false;
        }
        else {
            return true;  // signal that we want to quit
        }
    }


    // Verifica se o player chegou na sala do diploma.
    public void colocaNaSala() {
        if (diploma.aleatorio == 1) {
            if (currentRoom.description == "didática 1") {
                System.out.println("\nAchou o diploma!!\nAgora corra para a saída!!\n");
                diploma.mudalocal();
            }
        }
        if (diploma.aleatorio == 2) {
            if (currentRoom.description == "didática 2") {
                System.out.println("\nAchou o diploma!!\nAgora corra para a saída!!\n");
                diploma.mudalocal();
            }
        }
        if (diploma.aleatorio == 3) {
            if (currentRoom.description == "didática 3") {
                System.out.println("\nAchou o diploma!!\nAgora corra para a saída!!\n");
                diploma.mudalocal();
            }
        }
        if (diploma.aleatorio == 4) {
            if (currentRoom.description == "didática 4") {
                System.out.println("\nAchou o diploma!!\nAgora corra para a saída!!\n");
                diploma.mudalocal();
            }
        }
        if (diploma.aleatorio == 5) {
            if (currentRoom.description == "didática 5") {
                System.out.println("\nAchou o diploma!!\nAgora corra para a saída!!\n");
                diploma.mudalocal();
            }
        }
        if (diploma.aleatorio == 6) {
            if (currentRoom.description == "didática 6") {
                System.out.println("\nAchou o diploma!!\nAgora corra para a saída!!\n");
                diploma.mudalocal();
            }
        }
        else {}
    }

    // Diz onde está o cachorro e verifica se você morreu por ser pego pelo cachorro.
    public void monsterKill(){
        if (cachorro.lugarMonstro == 1) {
            System.out.println("\nO Cachorro está no incio do corredor.\n");
            if (currentRoom.description == "no inicio do corredor principal.") {
                System.out.println("\nO cachorro te pegou\nVocê morreu\n");
                cachorro.morreu();
            }
        }
        if (cachorro.lugarMonstro == 2) {
            System.out.println("\nO Cachorro está na frente da didática 1.\n");
            if (currentRoom.description == "no corredor principal entre a did 1 e 6") {
                System.out.println("\nO cachorro te pegou\nVocê morreu\n");
                cachorro.morreu();
            }
        }
        if (cachorro.lugarMonstro == 3) {
            System.out.println("\nO Cachorro está na frente da didática 2.\n");
            if (currentRoom.description == "no corredor principal entre a did 2 e a entrada") {
                System.out.println("\nO cachorro te pegou\nVocê morreu\n");
                cachorro.morreu();
            }
        }
        if (cachorro.lugarMonstro == 4) {
            System.out.println("\nO Cachorro está na frente da didática 5.\n");
            if (currentRoom.description == "no corredor principal frente à did 5") {
                System.out.println("\nO cachorro te pegou\nVocê morreu\n");
                cachorro.morreu();
            }
        }
        if (cachorro.lugarMonstro == 5) {
            System.out.println("\nO Cachorro está na frente do matinho\n");
            if (currentRoom.description == "no corredor principal frente ao matinho") {
                System.out.println("\nO cachorro te pegou\nVocê morreu\n");
                cachorro.morreu();
            }
        }
        if (cachorro.lugarMonstro == 6) {
            System.out.println("\nO Cachorro está na frente do pátio\n");
            if (currentRoom.description == "no corredor principal frente ao pátio") {
                System.out.println("\nO cachorro te pegou\nVocê morreu\n");
                cachorro.morreu();
            }
        }
        if (cachorro.lugarMonstro == 7) {
            System.out.println("\nO Cachorro está no fim do corredor.\n");
            if (currentRoom.description == "no fim do corredor principal.") {
                System.out.println("\nO cachorro te pegou\nVocê morreu\n");
                cachorro.morreu();
            }
        }
        else {}
    }

    // Atualiza a posição do cachorro.
    public void atualizaMonstro() {
        cachorro.criaAleatorio3();
        if (cachorro.lugarMonstro == 1){
            cachorro.nextLugar1();
        }
        if (cachorro.lugarMonstro == 7){
            cachorro.nextLugar2();
        }
        else{
            if ((cachorro.defMovimento == 1) || (cachorro.defMovimento == 5)) {
                cachorro.nextLugar1();
            }
            if ((cachorro.defMovimento == 2) || (cachorro.defMovimento == 4)) {
                cachorro.nextLugar2();
            }
            if (cachorro.defMovimento == 3) {
                cachorro.nextLugar3();
            }
        }
    }

}