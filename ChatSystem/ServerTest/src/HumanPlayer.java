import java.util.Scanner;

/**
 * Runs the game with a human player and contains code needed to read inputs.
 */

public class HumanPlayer
{

    /* Current gold player has */
    private int currentGold = 0;

    int[] playerPosition = new int[]{0, 0};

    /**
     * Gets the gold the player has
     *
     * @return An int containing the gold the player currently has
     */
    protected int getCurrentGold()
    {
        return currentGold;
    }

    /**
     * User gets extra gold
     */
    protected void receiveGold()
    {
        currentGold++;
    }

    /**
     * This method is only used when user has met the endgame condition.
     */
    protected void maxGold()
    {
        currentGold = 9999;
    }

    /**
     * Returns the position the player is currently at
     *
     * @return The current position of bot
     */
    protected int[] getPlayerPosition()
    {
        return playerPosition;
    }

    /**
     * Changes the position of a player
     * @param nextPosition: The position you want to move to
     */
    protected void changePositions(int[] nextPosition)
    {
        playerPosition[0] = nextPosition[0];
        playerPosition[1] = nextPosition[1];
    }

    /**
     * Reads player's input from the console.
     * @return An array of strings containing the input the player entered.
     */
    protected String[] getInputFromConsole()
    {
        Scanner in = new Scanner(System.in);
        String inputs = in.nextLine();
        String[] splitInput = inputs.split(" ");
        return splitInput;

    }
}