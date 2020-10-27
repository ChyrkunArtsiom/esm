package com.epam.esm.controller;

import com.epam.esm.exception.DAOException;
import com.epam.esm.handler.EsmExceptionHandler;
import com.epam.esm.service.impl.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@ComponentScan(basePackageClasses = {TagService.class, EsmExceptionHandler.class})
@RequestMapping("/tags")
public class TagController {

    private TagService service;

    private EsmExceptionHandler handler;

    @Autowired
    public void setService(TagService service) {
        this.service = service;
    }

    @Autowired
    public void setHandler(EsmExceptionHandler handler) {
        this.handler = handler;
    }

    @ResponseBody
    @RequestMapping(value = "/test")
    public String sayHello() {
        return "hello";
    }

    @RequestMapping(value = "/tag/{tagId}", method = RequestMethod.GET)
    public String home(@PathVariable int tagId, Model model) throws DAOException {

        model.addAttribute("tag", service.read(tagId));
        return "tags/tag";
    }


/*    @ExceptionHandler(DAOException.class)
    public String tagDoesntExistHandler() {
        return "error/404";
    }*/
}
