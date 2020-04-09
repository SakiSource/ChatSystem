/**
 * Runs the game with the bot player and contains attributes that the bot needs
 */

public class Bot
{
    /* Current position of bot */
    int[] botPosition = new int[]{0, 0};

    /* When this is true, the bot will 'chase' the player until player is caught */
    boolean botChase = false;

    /* The bot has a timer before he starts 'chasing' */
    int botCounter = 0;

    /* The bot has also a look counter, where it will turn 'blind' after the player escapes */
    int lookCounter = 0;

    /**
     * Returns the position the bot is currently at
     * @return The current position of bot
     */
    protected int[] getBotPosition()
    {
        return botPosition;
    }

    /**
     * Bot is going to chase the booty
     * @return The chase state
     */
    protected boolean chaseMode()
    {
        return botChase;
    }

    /**
     * Change the boolean botChase to true
     */
    protected void botChase()
    {
        botChase = true;
    }

    /**
     * Countdown before bot chases the booty
     * @return The counter
     */
    protected int counter()
    {
        return botCounter;
    }

    /**
     * Increment the counter
     */
    protected void increaseCounter()
    {
        botCounter++;
    }

    /**
     * Counter for when the bot sees the player
     * @return The look counter
     */
    protected int lookCounter()
    {
        return lookCounter;
    }

    /**
     * Increment the lookCounter
     */
    protected void increaseLook()
    {
        lookCounter++;
    }

    /**
     * When player is out of the bot's sight for a certain amount of turns, reset the bot state that's not in 'chase'
     */
    protected void resetState()
    {
        botChase = false;
        botCounter = 0;
        lookCounter = 0;
    }

    /**
     * Changes the position of the bot
     * @param nextPosition: The position you want to move to
     */
    protected void changePositions(int[] nextPosition)
    {
        botPosition[0] = nextPosition[0];
        botPosition[1] = nextPosition[1];
    }
}
