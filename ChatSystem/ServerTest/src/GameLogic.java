import java.sql.SQLOutput;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Random;

/**
 * Contains the main logic part of the game, as it processes.
 * @author Bibash Rai
 */

public class GameLogic
{

    private Map map;
    private HumanPlayer player;
    private Bot bot;
    private int playerCounter = 0;
    Random rand = new Random();

    /**
     * Default constructor
     */
    public GameLogic(Map map, HumanPlayer player, Bot bot)
    {
        this.map = map;
        this.player = player;
        this.bot = bot;
    }

    /**
     * Checks if the game is running
     *
     * @return If the game is running.
     */
    protected boolean gameRunning()
    {
        int currentGold = player.getCurrentGold();
        if(currentGold == 9999) // Impossible to get this amount unless user hits endgame condition.
        {
            return false;
        }
        return true;
    }

    /**
     * Returns the gold required to win.
     *
     * @return Gold required to win.
     */
    protected int goldRequired()
    {
        return map.getGoldRequired();
    }

    /**
     * Returns the gold currently owned by the player.
     *
     * @return Gold currently owned.
     */
    protected int gold()
    {
        return player.getCurrentGold();
    }

    /**
     * Checks if movement is legal and updates player's location on the map.
     *
     * @param direction : The direction of the movement.
     * @return Protocol if success or not.
     */
    protected String move(String direction)
    {
        // First element is y, second is x
        char[][] currentMap = map.getMap();
        int[] playerPosition = player.getPlayerPosition();
        int[] nextPosition = playerPosition;

        switch(direction)
        {
            case "n":
                if(currentMap[playerPosition[1] - 1][playerPosition[0]] != '#') // If you can go up
                {
                    nextPosition[0] = playerPosition[0];
                    nextPosition[1] = playerPosition[1] - 1;
                    player.changePositions(nextPosition);
                    System.out.println("SUCCESS");
                }
                else
                {
                    System.out.println("FAIL");
                }
                break;
            case "s":
                if(currentMap[playerPosition[1] + 1][playerPosition[0]] != '#') // If you can go down
                {
                    nextPosition[0] = playerPosition[0];
                    nextPosition[1] = playerPosition[1] + 1;
                    player.changePositions(nextPosition);
                    System.out.println("SUCCESS");
                }
                else
                {
                    System.out.println("FAIL");
                }
                break;
            case "e":
                if(currentMap[playerPosition[1]][playerPosition[0] + 1] != '#') // If you can go east
                {
                    nextPosition[0] = playerPosition[0] + 1;
                    nextPosition[1] = playerPosition[1];
                    player.changePositions(nextPosition);
                    System.out.println("SUCCESS");
                }
                else
                {
                    System.out.println("FAIL");
                }
                break;
            case "w":
                if(currentMap[playerPosition[1]][playerPosition[0] - 1] != '#') // If you can go west
                {
                    nextPosition[0] = playerPosition[0] - 1;
                    nextPosition[1] = playerPosition[1];
                    player.changePositions(nextPosition);
                    System.out.println("SUCCESS");
                }
                else
                {
                    System.out.println("FAIL");
                }
                break;

        }
        return null;
    }

    /**
     * This method moves the bot in our game
     * @param direction : Direction of where the bot shall move
     * @return "FAIL" if bot was unable to move.
     */
    protected String botMove(String direction)
    {
        // First element is y, second is x
        char[][] currentMap = map.getMap();
        int[] botPosition = bot.getBotPosition(); // 4,1
        int[] nextPosition = botPosition;

        switch(direction)
        {
            case "n":
                if(currentMap[botPosition[1] - 1][botPosition[0]] != '#') // If you can go up
                {
                    nextPosition[0] = botPosition[0];
                    nextPosition[1] = botPosition[1] - 1;
                    bot.changePositions(nextPosition);
                }
                break;
            case "s":
                if(currentMap[botPosition[1] + 1][botPosition[0]] != '#') // If you can go down
                {
                    nextPosition[0] = botPosition[0];
                    nextPosition[1] = botPosition[1] + 1;
                    bot.changePositions(nextPosition);
                }
                break;
            case "e":
                if(currentMap[botPosition[1]][botPosition[0] + 1] != '#') // If you can go east
                {
                    nextPosition[0] = botPosition[0] + 1;
                    nextPosition[1] = botPosition[1];
                    bot.changePositions(nextPosition);
                }
                break;
            case "w":
                if(currentMap[botPosition[1]][botPosition[0] - 1] != '#') // If you can go west
                {
                    nextPosition[0] = botPosition[0] - 1;
                    nextPosition[1] = botPosition[1];
                    bot.changePositions(nextPosition);
                }
                break;

        }
        return "FAIL";
    }

    /**
     * Generates a random direction for the bot
     * @return A direction
     */
    private String generateDirection()
    {
        int a = rand.nextInt(4);
        switch(a)
        {
            case 0:
                return "n";
            case 1:
                return "s";
            case 2:
                return "e";
            case 3:
                return "w";
        }
        return "what";
    }

    /**
     * Converts the map from a 2D char array to a single string.
     * @return A String representation of the game map.
     */
    protected String look()
    {
        /*
            Get player's location
            Read 5x5 around player's location
            If above the boundary then place '#'
            #####
            #***#
            #*P*#
            #***#
            #####
         */
        char[][] currentMap = map.getMap();
        int[] playerPosition = player.getPlayerPosition();
        int[] botPosition = bot.getBotPosition();

        /* Look constraints */
        int xMin = playerPosition[0] - 2;
        int xMax = playerPosition[0] + 2;
        int yMin = playerPosition[1] - 2;
        int yMax = playerPosition[1] + 2;

        String lookMap = "";
        /*
            Reading TOP DOWN
            Start at top left (yMin and xMin because of how fucking stupid this thing is)
            Loop until you get to the end of first line (xMax)
            Go next line (yMin + 1)
            Repeat (until yMin = yMax + 1)
        */
        int xCounter = xMin;
        do
        {
            try
            {
                while(xCounter != xMax + 1) // Loop until you reach end of line
                {
                    if(xCounter == playerPosition[0] && yMin == playerPosition[1]) // Player is in this coordinate
                    {
                        lookMap += "P";
                    }
                    else if(xCounter == botPosition[0] && yMin == botPosition[1]) // Bot is in this coordinate
                    {
                        lookMap += "B";
                    }
                    else // Produce the map
                    {
                        lookMap += currentMap[yMin][xCounter];

                    }
                    xCounter++;
                }
                lookMap += "\n";
                yMin++; // Go to next line
                xCounter = xMin;
            }
            catch(ArrayIndexOutOfBoundsException e) // Does not exist, 'Outside' the map therefore produce a wall.
            {
                lookMap += "#";
                xCounter++;
            }
        } while(yMin != yMax + 1);

        return lookMap;
    }

    /**
     * Processes the player's pickup command, updating the map and the player's gold amount.
     * @return If the player successfully picked-up gold or not.
     */
    protected String pickup()
    {
        int[] playerPosition = player.getPlayerPosition();
        char[][] entireMap = map.getMap();

        if(entireMap[playerPosition[1]][playerPosition[0]] == 'G') // Check if player position is on top of gold
        {
            map.removeGold(playerPosition);
            player.receiveGold();
            System.out.println("SUCCESS. " + "Gold Owned: " + gold());
        }
        else
        {
            System.out.println("FAIL. " + "Gold Owned: " + gold());
        }
        return null;
    }

    /**
     * Spawns the player
     */
    protected void spawnPlayers()
    {
        boolean spawnable = false;
        char[][] entireMap = map.getMap();

        for(int i = 0; i < 2; i++) // Spawns player and bot
        {

            while(spawnable == false)
            {
                int y = rand.nextInt(entireMap.length);
                int x = rand.nextInt(entireMap[0].length);
                int[] positions = {x,y};
                if(i == 0)
                {
                    player.changePositions(positions);
                }
                else if(i == 1)
                {
                    bot.changePositions(positions);
                }

                if(entireMap[y][x] != '#')
                {
                    spawnable = true;
                }
            }
            spawnable = false;
        }
    }

    /**
     * Quits the game, shutting down the application.
     */
    protected void quitGame()
    {
        int[] playerPosition = player.getPlayerPosition();
        char[][] entireMap = map.getMap();
        int currentGold = player.getCurrentGold();
        int goldNeeded = map.getGoldRequired();

        if(entireMap[playerPosition[1]][playerPosition[0]] == 'E' &&  currentGold == goldNeeded) // User wins the game if they are on top of the exit and have the required amount of gold
        {
            System.out.println("GAME WON");
            player.maxGold();
            gameRunning();
        }
        else
        {
            System.out.println("GAME LOST"); // LOSS CONDITION
            player.maxGold();
            gameRunning();
        }
    }

    /**
     * Main Method
     */
    public static void main(String[] args) throws Exception
    {
        Map map = new Map();
        HumanPlayer player = new HumanPlayer();
        Bot bot = new Bot();
        GameLogic logic = new GameLogic(map, player, bot); // Creating a GameLogic object

        logic.spawnPlayers();
        int counter = logic.playerCounter;
        String[] input;

        System.out.println("Game Start!");
        System.out.println("Which map would you like to use?");
        System.out.println("1) Default Map");
        System.out.println("2) Small Dungeon of Doom");
        System.out.println("3) Medium Dungeon of Disaster");
        System.out.println("4) Large Dungeon of Despair");
        input = player.getInputFromConsole();
        switch (input[0])
        {
            case "1":
                System.out.println("1");
                break;
            case "2":
                map.readMap("small");
                break;
            case "3":
                map.readMap("medium");
                break;
            case "4":
                map.readMap("large");
                break;
            default:
                System.out.println("Invalid, therefore default has been chosen");
                break;
        }
        System.out.println(map.getMapName());
        do
        {
            int[] coordinates = player.getPlayerPosition();
            int[] botCoordinates = bot.getBotPosition();

            if(counter % 2 == 0) // Player turn
            {
                System.out.println("Player's Turn!");
                //  Main code
                input = player.getInputFromConsole();
                switch(input[0])
                {
                    case "hello":
                        System.out.println("Gold to win: " + logic.goldRequired());
                        break;
                    case "gold":
                        System.out.println("Gold owned: " + logic.gold());
                        break;
                    case "move":
                        try
                        {
                            switch (input[1])
                            {
                                case "n":
                                case "e":
                                case "s":
                                case "w":
                                    logic.move(input[1]);
                                    break;
                                default:
                                    System.out.println("Failed...");
                                    break;
                            }
                        }
                        catch (ArrayIndexOutOfBoundsException e) // If unable to process the element after 'next'
                        {
                            System.out.println("Failed...");
                        }
                        break;
                    case "pickup":
                        System.out.println("Picking...");
                        logic.pickup();
                        break;
                    case "look":
                        System.out.println(logic.look());
                        break;
                    case "quit":
                        logic.quitGame();
                        break;
                    default:
                        System.out.println("Invalid");
                        break;
                }
            }
            else // Bot turn
            {
                System.out.println("Bot's Turn!");

                if(bot.botChase == true && bot.botCounter > 4)
                {
                    /* P*B Therefore, bot moves left (West) */
                    if(coordinates[0] < botCoordinates[0])
                    {
                        logic.botMove("w");
                    }
                    /* P*B Therefore, bot moves right (East) */
                    else if(coordinates[0] > botCoordinates[0])
                    {
                        logic.botMove("e");
                    }
                    /* B
                    *  *
                    *  P Therefore, bot moves down (North because this thing is annoying asf)
                    */
                    else if(coordinates[1] < botCoordinates[1])
                    {
                        logic.botMove("n");
                    }
                    /* P
                     * *
                     * B Therefore, bot moves up (South)
                     */
                    else if(coordinates[1] > botCoordinates[1])
                    {
                        logic.botMove("s");
                    }
                }

                /* Bot does not chase at this stage but is now at chasing mode */
                else if(bot.botChase == true && bot.botCounter == 4)
                {
                    System.out.println("It's coming for you...");
                    bot.increaseCounter();
                }
                /* Bot has seen player therefore start the counter */
                else if(bot.botChase == true)
                {
                    if(logic.look().contains("B") == false) // Can't find player
                    {
                        bot.increaseLook();
                    }
                    if(bot.lookCounter == 2)
                    {
                        System.out.println("It has lost sight of you...");
                        bot.resetState();
                    }
                    else
                    {
                        System.out.println("BOT IS GOING TO CHASE IN: " + (bot.counter() * -1 + 4) + " TURNS");
                        bot.increaseCounter();
                    }
                }
                /* Random movement */
                else
                {
                    String direction = logic.generateDirection();
                    logic.botMove(direction);
                    String botSight = logic.look();
                    if(botSight.contains("B") == true) // Bot has been seen by the player
                    {
                        bot.botChase(); // Activate the bot's chase mechanism
                    }
                    System.out.println("Bot has moved");
                }
            }
            counter++;

            /* Coordinate of player matches bot's coordinates */
            if(coordinates[0] == botCoordinates[0] && coordinates[1] == botCoordinates[1])
            {
                System.out.println("You have been caught...");
                player.maxGold();
            }
        } while(logic.gameRunning() == true); //Loop until game ends
        System.out.println("Game has ended!");
    }
}