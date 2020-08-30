package gameplay;

import sprites.Ball;
import sprites.Bot;
import sprites.Target;
import sprites.User;

import java.io.File;
import java.io.PrintWriter;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.TimerTask;

public class DataWriter extends TimerTask {

    private List<Target> originalTargetList;
    private List<Target> targetList;
    private List<User> userList;
    private List<Bot> botList;
    private PrintWriter pw;
    private long startTime;


    public DataWriter(List<Target> targetList, List<User> userList, List<Bot> botList, PrintWriter pw) {
        this.originalTargetList = targetList;
        this.targetList = new ArrayList<>(targetList);
        this.userList = new ArrayList<>(userList);
        this.botList = new ArrayList<>(botList);
        this.pw = pw;
        this.startTime = System.nanoTime();
        this.firstLine();
    }


    public void firstLine() {
        int i = 1;
        this.pw.print("                    ");
        for (User user : this.userList) {
            this.pw.print("user " + i + ";                      ");
            i++;
        }
        i = 1;
        for (Bot bot : this.botList) {
            this.pw.print("bot " + i + ";                       ");
            i++;
        }
        i = 1;
        for (Target tg : this.targetList) {
            if (tg.getType() == TargetType.LARGE_RED) {
                this.pw.printf("LR target %03d;", i);
            } else if (tg.getType() == TargetType.SMALL_RED) {
                this.pw.printf("SR target %03d;", i);
            } else if (tg.getType() == TargetType.LARGE_BLUE) {
                this.pw.printf("LB target %03d;", i);
            } else if (tg.getType() == TargetType.SMALL_BLUE) {
                this.pw.printf("SB target %03d;", i);
            }
            this.pw.print("                 ");
            i++;
        }
        this.pw.println();
    }


    public void run() {
        Long elapsedTime = System.nanoTime() - this.startTime;
        double currTime = elapsedTime / (double) 1000000000;
        DecimalFormat myFormatter = new DecimalFormat("000.00");
        String output = myFormatter.format(currTime);
        String output2;
        myFormatter = new DecimalFormat("0000.00");
        this.pw.printf(output + ";     ");
        for (User user : this.userList) {
            output = myFormatter.format(user.getCenter().getX());
            output2 = myFormatter.format(user.getCenter().getY());
            this.pw.printf("X: " + output + ",  Y: " + output2 + ";     ");
        }
        for (Bot bot : this.botList) {
            output = myFormatter.format(bot.getCenter().getX());
            output2 = myFormatter.format(bot.getCenter().getY());
            this.pw.printf("X: " + output + ",  Y: " + output2 + ";     ");
        }
        for (Target tg : this.targetList) {
            if (!this.originalTargetList.contains(tg)) {
                this.pw.printf("X: -1,       Y: -1;            ");
            }
            else {
                output = myFormatter.format(tg.getCenter().getX());
                output2 = myFormatter.format(tg.getCenter().getY());
                this.pw.printf("X: " + output + ",  Y: " + output2 + ";       ");
            }
        }
        this.pw.println();
    }

}
