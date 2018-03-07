package it.polito.lombardo.esp_webserver.util;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("prototype")
public class MergingThread extends Thread{

    private static final Integer SLEEPING_TIME_MS = 5000;	//5s

    @Override
    public void run(){
        while(true) {
            try {
                Thread.sleep(SLEEPING_TIME_MS);
                //do something....

            } catch (InterruptedException e) {
                System.out.println("Some error occured during sleep...");
            }
        }
    }
}
