package com.app.easy.notes.controller.notes;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class NotesController {
    @RequestMapping(value = "/greeting", method = RequestMethod.GET)
    public Boolean greetings(){
        return true;
    }
}
