import gameplay.Game;

/**
* @author Alon Shoval <alon_shoval@hotmail.com>
* @version 1.0
* @since 2019-03-03  */
public class PacMan {
   /**
    * main - creates a new game, initializes it, and runs it.
    * <p>
    *
    * <p>
    *
    *
    *@param args - user inputted arguments. not used in this class.
    */
   public static void main(String[] args) {
      Game game = new Game();
      game.initialize();
      game.run();
   }
}