package processing.impl;

import controllers.Controller;
import processing.Status;
import processing.StatusListener;

import java.util.Timer;
import java.util.TimerTask;


public class StatusListenerImpl implements StatusListener {
    private Controller controller;
    public TimerTask task;
    Timer timer;

    public StatusListenerImpl(Controller controller) {
        this.controller = controller;
    }

    @Override
    public void statusChanged(Status status) {
           task = new TimerTask() {
            @Override
            public void run() {
                controller.setTextResult(false);

//                System.out.println(status.getTaskName() + ": " + status.getProgress() + "%");
//                System.out.println("Setting progress " + status.getProgress());
                controller.setProgress(status.getProgress());
                if (status.getProgress() == 104) {
                    controller.setTextResult(true);
                }
            }
        };
        timer = new Timer("Timer");

        long delay = 1000L * status.getProgress()/20;
        timer.schedule(task, delay);


    }
}

