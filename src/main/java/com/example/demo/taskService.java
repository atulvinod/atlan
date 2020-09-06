package com.example.demo;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.util.Hashtable;
import java.util.LinkedList;
import java.util.Queue;
import java.util.stream.Collectors;


import static java.lang.Thread.sleep;

@Service
public class taskService {
    Hashtable<Long, Thread> map = null;
    Queue<Thread> queue = null;
    int concurrentTaskCount = 10;
    public taskService(){
        map = new Hashtable<>();
        queue = new LinkedList<Thread>();
        Thread checkLoad = new Thread(r);
        checkLoad.start();
    }

    // A seperate thread to manage concurrent threads
    Runnable r = new Runnable() {

        @Override
        public void run() {
            System.out.println("CheckLoad running");

            // Keeps checking if the job queue has new tasks , if the job queue has tasks and the concurrent thread
            // count is less than max, then that task can be executed.
            while(true) {

                while (queue.size() > 0) {
                    if (map.size() < concurrentTaskCount) {
                        System.out.println("Task from Queue");
                        Thread t = queue.poll();
                        map.put(t.getId(), t);
                        t.start();
                    }
                }
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    };





    public String executeAsynchronously(MultipartFile file) {
        task t = new task(file,map);

        // if the concurrent thread count has reached max, then push that into the queue.
        if(map.size()>concurrentTaskCount){
            queue.add(t);
            return "Queued : " +t.getId();

        }
        t.start();
        map.put(t.getId(),t);
        return ""+t.getId();

    }
    public String pauseThread(long id){
        System.out.println("Suspeding thread");

        if(map.containsKey(id)) {
            map.get(id).suspend();
            return "pausedtask";

        }else
            return "No Task exists";
    }
    public String  resumeThread(long id){
        System.out.println("Resuming Thread");
        if(map.containsKey(id)) {
            map.get(id).resume();
            return "Paused Thread";
        }else{
            return "No task exists";
        }
    }

    public String destroyThread(long id){
        try{
            if(map.containsKey(id)) {
                map.get(id).stop();
                map.remove(id);
            }else{
                return "No task exists";
            }
            return "Task ended";
        }catch (Exception e){
            e.printStackTrace();
            return "No task exists";
        }

   }
   public String checkIfTaskIsQueuedOrRunning(long id){
        if(map.containsKey(id)){
            return "Your task is running";
        }
       if(queue.stream().filter(e->e.getId()==id).collect(Collectors.toList()).size()>0){
           return "Your task is queue";
       }
       return "Invalid task id or the task has been completed";
   }
}
