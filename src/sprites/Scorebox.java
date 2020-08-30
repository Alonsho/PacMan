package sprites;

import biuoop.DrawSurface;
import gameplay.Counter;
import gameplay.Game;
import java.util.Map;

public class Scorebox implements Sprite {

    private Map<String, Counter> scoreList;


    public Scorebox(Map<String, Counter> scoreList) {
        this.scoreList = scoreList;
    }


    public void drawOn(DrawSurface d) {
        int i = 40;
        for (String name : this.scoreList.keySet()) {
            String score = Integer.toString(scoreList.get(name).getValue());
            d.drawText(40, i, name + ": " + score, 15);
            i += 30;
        }
    }



    public void timePassed() {
    }



    public void addToGame(Game game) {
        //set the ball to recognize the given game environment.
        game.getSprites().addSprite(this);
    }
}
