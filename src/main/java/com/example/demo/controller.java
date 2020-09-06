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

    @RequestMapping("/pause")
    public String pause(@RequestParam("id") long id){
       return tService.pauseThread(id);
    }

    @RequestMapping(value="/resume")
    public String resume(@RequestParam("id") long id){
     return   tService.resumeThread(id);
    }

    @RequestMapping(value="/shutdown")
    public String shutdown(@RequestParam("id") long id){
       return tService.destroyThread(id);
    }

}
