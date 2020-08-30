package gameplay;

import biuoop.DrawSurface;
import biuoop.GUI;
import geometry.Rectangle;
import sprites.Block;

import java.awt.*;
import java.io.*;

public class DataReader extends Game {

    private boolean reachedEOF = false;
    private int numOfUsers = 0;
    private int numOfBots = 0;
    private GameEnvironment environment;
    private DrawSurface d;


    public DataReader() {
        this.environment = new GameEnvironment();
    }


    public void run() {

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        double width = screenSize.getWidth();
        double height = screenSize.getHeight();
        GUI gui = new GUI("title", (int) width, (int) height);
        this.environment.setGui(gui);



        this.d = this.environment.getGui().getDrawSurface();
        File dataFile = new File("DATA.txt");
        BufferedReader bf = null;
        try {
            InputStream is = new FileInputStream(dataFile);
            bf = new BufferedReader(new InputStreamReader(is));
        } catch(Exception e) {
            System.out.println("Data file not found");
        }
        String line = null;
        try {
            line = bf.readLine();
        } catch (Exception e) {
            System.out.println("cannot read first line of data file");
        }
        readFirstLine(line);



        biuoop.Sleeper sleeper = new biuoop.Sleeper();
        int framesPerSecond = 30;
        int millisecondsPerFrame = 1000 / framesPerSecond;
        while (!this.reachedEOF) {
            this.d = this.environment.getGui().getDrawSurface();
            try {
                line = bf.readLine();
                if (line == null) {
                    this.reachedEOF = true;
                    continue;
                } else {
                    try {
                        drawBoundaries();
                        drawSprites(line);
                    } catch (Exception e) {
                        System.out.println("Simulation done");
                    }
                }
            } catch (Exception e) {
                System.out.println("Error reading line from data file");
            }
            long startTime = System.currentTimeMillis(); // timing
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




    public void readFirstLine(String line) {
        line = line.replace(" ", "");
        String[] sprites = line.split(";");
        for (String sprite : sprites) {
            if (sprite.startsWith("user")) {
                this.numOfUsers++;
            } else if (sprite.startsWith("bot")) {
                this.numOfBots++;
            }
        }
    }




    public void drawBoundaries() {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        double width = screenSize.getWidth();
        double height = screenSize.getHeight();
        geometry.Point p1 = new geometry.Point(0, 0);
        geometry.Rectangle rec = new geometry.Rectangle(p1, width, 20);
        Block block = new Block(rec, java.awt.Color.gray, 0);
        block.drawOn(this.d);
        p1 = new geometry.Point(width - 20, 0);
        rec = new geometry.Rectangle(p1, 20, height);
        block = new Block(rec, java.awt.Color.gray, 0);
        block.drawOn(this.d);
        p1 = new geometry.Point(0, height - 50);
        rec = new geometry.Rectangle(p1, width, 20);
        block = new Block(rec, java.awt.Color.gray, 0);
        block.drawOn(this.d);
        p1 = new geometry.Point(0, 0);
        rec = new Rectangle(p1, 20, height);
        block = new Block(rec, java.awt.Color.gray, 0);
        block.drawOn(this.d);
    }





    public void drawSprites(String line) {
        line = line.replace(" ", "");
        String[] locations = line.split(";");
        int j = 1;
        for (int i = 0; i < this.numOfUsers; i++) {
            draw(locations[j], Color.blue);
            j++;
        }
        for (int i = 0; i < this.numOfBots; i++) {
            draw(locations[j], Color.black);
            j++;
        }
        for (j = j; j < locations.length; j++) {
            draw(locations[j], Color.red);
        }
    }



    public void draw(String location, Color color) {
        int r;
        if (color.equals(Color.red)) {
            r = 5;
        } else {
            r = 10;
        }
        String[] axis = location.split(",");
        axis[0] = axis[0].replace("X:", "");
        axis[1] = axis[1].replace("Y:", "");
        double xLoc = Double.parseDouble(axis[0]);
        double yLoc = Double.parseDouble(axis[1]);
        if (xLoc == -1) {
            return;
        }
        d.setColor(color);
        d.fillCircle((int) xLoc, (int) yLoc, r);
        d.setColor(java.awt.Color.black);
        d.drawCircle((int) xLoc, (int) yLoc, r);
    }

}
