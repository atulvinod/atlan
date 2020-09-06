package com.example.demo;

import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Hashtable;

public class task extends Thread {

    boolean pause = false;
    MultipartFile file;
    Hashtable<Long,Thread> ref;
    public void init(){
        this.run();
    }

    public void pauseThread(){
        System.out.println("Thread Paused");
        pause = true;

    }
    task(MultipartFile file, Hashtable<Long,Thread> completed){
        this.file = file;
        this.ref = completed;
    }

    public void destroy()  {
        try {
            Thread.currentThread().suspend();
        }catch (Exception e){

        }
    }


    public void run() {

       try(BufferedReader reader = new BufferedReader(new InputStreamReader(file.getInputStream()))){
           String line =reader.readLine();
           while(line!=null) {
               line = reader.readLine();
               System.out.println(line);
               Thread.sleep(500);
           }

           // When completed , remove reference from the map
           System.out.println("Task completed");
           ref.remove(Thread.currentThread().getId());

       }catch(Exception e){
           e.printStackTrace();
       }
    }
};
