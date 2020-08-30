package gameplay;

import biuoop.DialogManager;
import biuoop.GUI;
import biuoop.DrawSurface;
import geometry.Point;
import geometry.Rectangle;
import net.java.games.input.Controller;
import net.java.games.input.ControllerEnvironment;
import sprites.*;

import java.awt.*;
import java.io.*;
import java.util.*;
import java.util.List;

/**
* @author Alon Shoval <alon_shoval@hotmail.com>
* @version 1.0
* @since 2019-03-03  */
public class Game {
   //fields of Game.
   private SpriteCollection sprites;
   private GameEnvironment environment;
   private Counter remainingTargets;
   private DataWriter dw;

   public static int GAME_SPEED;


   /**
    * Game - construct a Game object.
    * <p>
    * creates a SpriteCollection and a GameEnvironment.
    * <p>
    *
    */
   public Game() {
      this.sprites = new SpriteCollection();
      this.environment = new GameEnvironment();
      this.remainingTargets = new Counter();
   }
   /**
    * addCollidable - add the given collidable to the game's game environment.
    * <p>
    *
    * <p>
    *
    * @param c - the new collidable to be added to the list.
    */
   public void addCollidable(Collidable c) {
      this.environment.addCollidable(c);
      return;
   }


   public void removeCollidable(Collidable c) {
      this.environment.removeCollidable(c);
      return;
   }
   /**
    * addSprite - add a new sprite to the game's sprite collection.
    * <p>
    *
    * <p>
    *
    * @param s - the new sprite to be added.
    */
   public void addSprite(Sprite s) {
      this.sprites.addSprite(s);
      return;
   }


   public void removeSprite(Sprite s) {
      this.sprites.removeSprite(s);
      return;
   }
   /**
    * initialize - Initialize a new game.
    * <p>
    *
    * <p>
    * create the Blocks and Ball (and Paddle) and add them to the game.
    *
    *
    */
   public void initialize() {
      // get screen size
      Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
      double width = screenSize.getWidth();
      double height = screenSize.getHeight();
      //create a new GUI and add it to the game environment.
      GUI gui = new GUI("title", (int) width, (int) height);
      this.environment.setGui(gui);
      //create dialog box
      DialogManager dialog = gui.getDialogManager();
      // create a list of game pads
      Controller[] controllers = ControllerEnvironment.getDefaultEnvironment().getControllers();
      List<Controller> gamePadList = new ArrayList<>();
      for(int i=0;i<controllers.length;i++) {
         if(controllers[i].getType()==Controller.Type.GAMEPAD) {
            // Found a game pad
            gamePadList.add(controllers[i]);
         }
      }
      if(gamePadList.isEmpty()) {
         // Couldn't find a game pad
         dialog.showErrorDialog("No GamePad", "No GamePad found. Connect at least one GamePad");
         System.exit(0);
      }



















      boolean goodInput = false;
      String name;
      // set the game speed
      while (!goodInput) {
         try {
            GAME_SPEED = Integer.parseInt(dialog.showQuestionDialog("", "Enter game speed (1 - 5)", "3"));
            if (GAME_SPEED >= 6 || GAME_SPEED <= 0) {
               dialog.showErrorDialog("", "Invalid input");
               continue;
            } else {
               goodInput = true;
            }
         } catch (Exception e) {
            dialog.showErrorDialog("", "Invalid input");
         }
      }
      goodInput = false;
      int numOfBots = 0;
      // set number of bots
      while (!goodInput) {
         try {
            name = dialog.showQuestionDialog("Bots", "Enter the number of bots", "1");
            numOfBots = Integer.parseInt(name);
            goodInput = true;
         } catch (Exception e) {
            dialog.showErrorDialog("", "Invalid input");
         }
      }
      goodInput = false;
      int numOfTargets = 0;
      //set the number of targets
      while (!goodInput) {
         try {
            name = dialog.showQuestionDialog("Targets", "Enter the number of large red targets", "10");
            numOfTargets = Integer.parseInt(name);
            goodInput = true;
         } catch (Exception e) {
            dialog.showErrorDialog("", "Invalid input");
         }
      }
      List<Target> targetList = new ArrayList<>();
      this.remainingTargets.increase(numOfTargets);
      Random rand = new Random();
      for (int i = 0; i < numOfTargets; i++) {
         Target target = new Target(rand.nextInt((int)width - 42) + 21, rand.nextInt((int)height - 72) + 21, TargetType.LARGE_RED);
         target.setVelocity((2 + GAME_SPEED) * rand.nextDouble(), (2 + GAME_SPEED) * rand.nextDouble());
         target.addToGame(this);
         targetList.add(target);
      }


      goodInput = false;
      numOfTargets = 0;
      //set the number of targets
      while (!goodInput) {
         try {
            name = dialog.showQuestionDialog("Targets", "Enter the number of small red targets", "10");
            numOfTargets = Integer.parseInt(name);
            goodInput = true;
         } catch (Exception e) {
            dialog.showErrorDialog("", "Invalid input");
         }
      }
      this.remainingTargets.increase(numOfTargets);
      rand = new Random();
      for (int i = 0; i < numOfTargets; i++) {
         Target target = new Target(rand.nextInt((int)width - 42) + 21, rand.nextInt((int)height - 72) + 21, TargetType.SMALL_RED);
         target.setVelocity((2 + GAME_SPEED) * rand.nextDouble(), (2 + GAME_SPEED) * rand.nextDouble());
         target.addToGame(this);
         targetList.add(target);
      }


      goodInput = false;
      numOfTargets = 0;
      //set the number of targets
      while (!goodInput) {
         try {
            name = dialog.showQuestionDialog("Targets", "Enter the number of large blue targets", "10");
            numOfTargets = Integer.parseInt(name);
            goodInput = true;
         } catch (Exception e) {
            dialog.showErrorDialog("", "Invalid input");
         }
      }
      this.remainingTargets.increase(numOfTargets);
      rand = new Random();
      for (int i = 0; i < numOfTargets; i++) {
         Target target = new Target(rand.nextInt((int)width - 42) + 21, rand.nextInt((int)height - 72) + 21, TargetType.LARGE_BLUE);
         target.setVelocity((2 + GAME_SPEED) * rand.nextDouble(), (2 + GAME_SPEED) * rand.nextDouble());
         target.addToGame(this);
         targetList.add(target);
      }


      goodInput = false;
      numOfTargets = 0;
      //set the number of targets
      while (!goodInput) {
         try {
            name = dialog.showQuestionDialog("Targets", "Enter the number of small blue targets", "10");
            numOfTargets = Integer.parseInt(name);
            goodInput = true;
         } catch (Exception e) {
            dialog.showErrorDialog("", "Invalid input");
         }
      }
      this.remainingTargets.increase(numOfTargets);
      rand = new Random();
      for (int i = 0; i < numOfTargets; i++) {
         Target target = new Target(rand.nextInt((int)width - 42) + 21, rand.nextInt((int)height - 72) + 21, TargetType.SMALL_BLUE);
         target.setVelocity((2 + GAME_SPEED) * rand.nextDouble(), (2 + GAME_SPEED) * rand.nextDouble());
         target.addToGame(this);
         targetList.add(target);
      }


      List<User> userList = new ArrayList<>();
      int red = 255;
      Map<String, Counter> scoreList = new HashMap<>();
      int k = 1;
      for (Controller gp : gamePadList) {
          Counter score = new Counter();
          scoreList.put("User " + k, score);
          User user1 = new User(width / 2, height / 2, 10, new Color(red, 255, 0), gp, targetList, this, score);
          userList.add(user1);
          user1.addToGame(this);
          red = red - 40;
          k++;
      }
      List<Bot> botList = new ArrayList<>();
      for (int i = 1; i <= numOfBots; i++) {
         boolean[] typesToChase = new boolean[4];
         for (TargetType type : TargetType.values()) {
            typesToChase[type.ordinal()] = dialog.showYesNoDialog("", "Should bot" + i + " chase " + type + " targets?");
         }
          Counter score = new Counter();
          scoreList.put("Bot " + i, score);
         Bot bot = new Bot(rand.nextInt((int)width - 42) + 21, rand.nextInt((int)height - 72) + 21,
                 10, java.awt.Color.black, targetList, typesToChase, this, score);
         bot.addToGame(this);
         botList.add(bot);
      }
      Scorebox sb = new Scorebox(scoreList);

      sb.addToGame(this);



      try {
         PrintWriter pw = new PrintWriter(new OutputStreamWriter(new FileOutputStream(new File("DATA.txt"))));
         this.dw = new DataWriter(targetList, userList, botList, pw);
      } catch (IOException e) {
         System.out.println("Something went wrong while writing!");
         System.exit(1);
      }




      // create the 4 blocks that block the borders and add them to the game.
      Point p1 = new Point(0, 0);
      Rectangle rec = new Rectangle(p1, width, 20);
      Block block = new Block(rec, java.awt.Color.gray, 0);
      block.addToGame(this);
      p1 = new Point(width - 20, 0);
      rec = new Rectangle(p1, 20, height);
      block = new Block(rec, java.awt.Color.gray, 0);
      block.addToGame(this);
      p1 = new Point(0, height - 50);
      rec = new Rectangle(p1, width, 20);
      block = new Block(rec, java.awt.Color.gray, 0);
      block.addToGame(this);
      p1 = new Point(0, 0);
      rec = new Rectangle(p1, 20, height);
      block = new Block(rec, java.awt.Color.gray, 0);
      block.addToGame(this);
   }
   /**
    * run - Run the game -- start the animation loop.
    * <p>
    *
    * <p>
    *
    *
    *
    */
   public void run() {
      biuoop.Sleeper sleeper = new biuoop.Sleeper();
      int framesPerSecond = 60;
      int millisecondsPerFrame = 1000 / framesPerSecond;
      runCountdown();
      Timer timer = new Timer();
      //change the time phase of each line in the data file
      timer.schedule(this.dw, 0, 1000 / 30);
      while (true) {
         long startTime = System.currentTimeMillis(); // timing
         DrawSurface d = this.environment.getGui().getDrawSurface();
         //draw all sprites to the screen.
         this.sprites.drawAllOn(d);
         this.environment.getGui().show(d);
         //notify all sprites that time has passed.
         this.sprites.notifyAllTimePassed();
         // timing
         long usedTime = System.currentTimeMillis() - startTime;
         long milliSecondLeftToSleep = millisecondsPerFrame - usedTime;
         //make sure all actions were done before displaying next frame.
         if (milliSecondLeftToSleep > 0) {
            sleeper.sleepFor(milliSecondLeftToSleep);
         }
      }
   }


   public void runCountdown() {
      biuoop.Sleeper sleeper = new biuoop.Sleeper();
      int framesPerSecond = 60;
      int millisecondsPerFrame = 1000 / framesPerSecond;
      int countdown = 3;
      double numOfSeconds = 2;
      double framesLeft = numOfSeconds / 3 * 60;
      while (true) {
         if (framesLeft <= 0) {
            if (countdown == 0) {
               return;
            }
            framesLeft = numOfSeconds / 3 * 60;
            countdown--;
         }
         long startTime = System.currentTimeMillis(); // timing
         DrawSurface d = this.environment.getGui().getDrawSurface();
         //draw all sprites to the screen.
         this.sprites.drawAllOn(d);
         d.setColor(Color.magenta);
         if (countdown != 0) {
            d.drawText(d.getWidth() / 2 - 16, d.getHeight() / 2, Integer.toString(countdown), 32);
         } else {
            d.drawText(d.getWidth() / 2 - 16, d.getHeight() / 2, "GO", 32);
         }
         framesLeft--;
         this.environment.getGui().show(d);
         // timing
         long usedTime = System.currentTimeMillis() - startTime;
         long milliSecondLeftToSleep = millisecondsPerFrame - usedTime;
         //make sure all actions were done before displaying next frame.
         if (milliSecondLeftToSleep > 0) {
            sleeper.sleepFor(milliSecondLeftToSleep);
         }
      }
   }
   /**
    * getEnvironment - Return the GameEnvironment of the game.
    * <p>
    *
    * <p>
    *
    *
    * @return the GameEnvironment of the game.
    */
   public GameEnvironment getEnvironment() {
      return this.environment;
   }
   /**
    * getSprites - Return the SpriteCollection of the game.
    * <p>
    *
    * <p>
    *
    *
    * @return the SpriteCollection of the game.
    */
   public SpriteCollection getSprites() {
      return this.sprites;
   }



}