package gameplay;

import biuoop.DialogManager;
import biuoop.DrawSurface;
import biuoop.GUI;
import geometry.Rectangle;
import sprites.Block;

import java.awt.*;
import java.io.*;

public class SimulationRunner extends Game {

    private boolean reachedEOF = false;
    private int numOfUsers = 0;
    private int numOfBots = 0;
    private int numOfLR = 0;
    private int numOfSR = 0;
    private int numOfLB = 0;
    private int numOfSB = 0;
    private GameEnvironment environment;
    private DrawSurface d;
    private int numOfSprites;
    private boolean shouldAssignSize = true;


    public SimulationRunner() {
        this.environment = new GameEnvironment();
    }


    public void run() {

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        double width = screenSize.getWidth();
        double height = screenSize.getHeight();
        GUI gui = new GUI("title", (int) width, (int) height);
        this.environment.setGui(gui);
        DialogManager dialog = gui.getDialogManager();



        this.d = this.environment.getGui().getDrawSurface();
        File dataFile = new File("DATA.txt");
        BufferedReader bf = null;
        try {
            InputStream is = new FileInputStream(dataFile);
            bf = new BufferedReader(new InputStreamReader(is));
        } catch(Exception e) {
            dialog.showErrorDialog("", "Data file not found");
            System.exit(0);
        }
        String line = null;
        try {
            line = bf.readLine();
        } catch (Exception e) {
            dialog.showErrorDialog("", "Error reading first line of data file");
            System.exit(0);
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
                        drawSprites(line);
                        drawBoundaries();
                    } catch (Exception e) {
                        dialog.showInformationDialog("", "Simulation done");
                        System.exit(0);
                    }
                }
            } catch (Exception e) {
                dialog.showErrorDialog("", "Error reading line from data file");
                System.exit(0);
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
            } else if (sprite.startsWith("LR")) {
                this.numOfLR++;
            } else if (sprite.startsWith("SR")) {
                this.numOfSR++;
            } else if (sprite.startsWith("LB")) {
                this.numOfLB++;
            } else if (sprite.startsWith("SB")) {
                this.numOfSB++;
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





    public void drawSprites(String line) throws Exception {
        line = line.replace(" ", "");
        String[] locations = line.split(";");
        if (this.shouldAssignSize) {
            this.numOfSprites = locations.length;
            this.shouldAssignSize = false;
        }
        if (locations.length != this.numOfSprites) {
            throw new Exception();
        }
        int j = 1;
        int red = 255;
        for (int i = 0; i < this.numOfUsers; i++) {
            draw(locations[j], new Color(red, 255, 0), 10);
            j++;
            red = red - 40;
        }
        for (int i = 0; i < this.numOfBots; i++) {
            draw(locations[j], Color.black, 10);
            j++;
        }
        for (int i = 0; i < this.numOfLR; i++) {
            draw(locations[j], Color.red, 8);
            j++;
        }
        for (int i = 0; i < this.numOfSR; i++) {
            draw(locations[j], Color.red, 5);
            j++;
        }
        for (int i = 0; i < this.numOfLB; i++) {
            draw(locations[j], Color.blue, 8);
            j++;
        }
        for (int i = 0; i < this.numOfSB; i++) {
            draw(locations[j], Color.blue, 5);
            j++;
        }
    }



    public void draw(String location, Color color, int size) {
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
        if (color.equals(Color.black)) {
            d.fillRectangle((int)xLoc - size, (int)yLoc - size, 2 * size, 2 * size);
            d.setColor(java.awt.Color.black);
            d.drawRectangle((int)xLoc - size, (int)yLoc - size, 2 * size, 2 * size);
        }
        d.fillCircle((int) xLoc, (int) yLoc, size);
        d.setColor(java.awt.Color.black);
        d.drawCircle((int) xLoc, (int) yLoc, size);
    }

}
