package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class controller {
    @Autowired
    public taskService  tService;
    @RequestMapping(value = "/pushtask",method = RequestMethod.POST)
    public String createTask(@RequestParam("csv")MultipartFile file){
        return (tService.executeAsynchronously(file));

    }

    // Endpoint to pause
    @RequestMapping("/pause")
    public String pause(@RequestParam("id") long id){
       return tService.pauseThread(id);
    }

    //Endpoint to resume
    @RequestMapping(value="/resume")
    public String resume(@RequestParam("id") long id){
     return   tService.resumeThread(id);
    }


    //Endpoint to shutdown
    @RequestMapping(value="/shutdown")
    public String shutdown(@RequestParam("id") long id){
       return tService.destroyThread(id);
    }

    //Checkpoint to check task status
    @RequestMapping(value="/check_task_status")
    public String checktast(@RequestParam("id") long id){
        return tService.checkIfTaskIsQueuedOrRunning(id);
    }

}
