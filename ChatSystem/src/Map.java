import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Reads and contains in memory the map of the game.
 */
public class Map
{
    /* Representation of the map */
    private char[][] map;

    /* Map name */
    private String mapName;

    /* Gold required for the human player to win */
    private int goldRequired;

    /* Position of where the player currently is */
    private int[] playerPosition;

    /* Position of were the bot is */
    private int[] botPosition;

    /* Stores the previous position where player was */
    private int[] previousPosition;

    /**
     * Default constructor, creates the default map "Very small Labyrinth of doom".
     */
    public Map()
    {
        mapName = "Very small Labyrinth of Doom";
        goldRequired = 2;

        // 20 x 9 map right now
        map = new char[][]
                {
                {'#','#','#','#','#','#','#','#','#','#','#','#','#','#','#','#','#','#','#','#'},
                {'#','.','.','#','.','.','.','.','.','.','.','.','.','.','.','.','.','.','.','#'},
                {'#','.','.','G','.','.','.','.','.','.','.','.','.','.','.','.','.','E','.','#'},
                {'#','.','.','.','.','.','.','.','.','.','.','.','.','.','.','.','.','.','.','#'},
                {'#','.','.','E','.','.','.','.','.','.','.','.','.','.','.','.','.','.','.','#'},
                {'#','.','.','.','.','.','.','.','.','.','.','.','G','.','.','.','.','.','.','#'},
                {'#','.','.','.','.','.','.','.','.','.','.','.','.','.','.','.','.','.','.','#'},
                {'#','.','.','.','.','.','.','.','.','.','.','.','.','.','.','.','.','.','.','#'},
                {'#','#','#','#','#','#','#','#','#','#','#','#','#','#','#','#','#','#','#','#'}
                };
    }

    /**
     * Constructor that accepts a map to read in from.
     * @param fileName: Name of file
     * @throws Exception: Exception is thrown
     */
    public Map(String fileName) throws Exception
    {
        readMap(fileName);
    }

    /**
     * @return Gold required to exit the current map.
     */
    protected int getGoldRequired()
    {
        return goldRequired;
    }

    /**
     * @return The map as stored in memory.
     */
    protected char[][] getMap()
    {
        return map;
    }


    /**
     * @return The name of the current map.
     */
    protected String getMapName()
    {
        return mapName;
    }

    /**
     * Removes gold off the map
     * @param position: Position of the gold
     */
    protected void removeGold(int[] position)
    {
        map[position[1]][position[0]] = '.';
    }


    /**
     * Reads the map from file.
     * @param fileName: Name of the map's file.
     */
    protected void readMap(String fileName) throws Exception
    {
        Scanner sc = null;
        if(fileName == "large" || fileName == "medium" || fileName == "small")
        {
            if(fileName == "large")
            {
                File file = new File("/home/bib/Documents/University/Coding/DungeonOfDoom/src/large_example_map.txt"); // Linux directories be like
                sc = new Scanner(file);
            }
            else if(fileName == "medium")
            {
                File file = new File("/home/bib/Documents/University/Coding/DungeonOfDoom/src/medium_example_map.txt");
                sc = new Scanner(file);
            }
            else if(fileName == "small")
            {
                File file = new File("/home/bib/Documents/University/Coding/DungeonOfDoom/src/small_example_map.txt");
                sc = new Scanner(file);
            }
            List<String> mapFile = new ArrayList<>();
            int counter = 0;
            while (sc.hasNextLine()) // Keep looping until end of file has been reached
            {
                if(counter == 0)
                {
                    mapName = sc.nextLine().substring((5)); // Take out the 'Name '
                    counter++;
                }
                else if(counter == 1)
                {
                    goldRequired = Integer.parseInt(sc.nextLine().substring((4))); // Take out the 'Win '
                    counter++;
                }
                else
                {
                    mapFile.add(sc.nextLine());
                }
            }
            //mapFile.size(); y length
            //mapFile.get(0).length(); x length
            char[][] newMap = new char[mapFile.size()][mapFile.get(0).length()];
            int yCounter = 0;

            for(String a:mapFile)
            {
                for(int i = 0; i < a.length(); i++)
                {
                    newMap[yCounter][i] = a.charAt(i);
                }
                yCounter++;
            }
            map = newMap;
        }
        else //Keep default map
        {
            map = map;
        }

    }
}