package com.epam.esm.controller;

import com.epam.esm.service.impl.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@ComponentScan(basePackageClasses = TagService.class)
@RequestMapping("/tags")
public class TagController {

    private TagService service;

    @Autowired
    public void setService(TagService service) {
        this.service = service;
    }

    @RequestMapping(value = "/tag/{tagId}", method = RequestMethod.GET)
    public String home(@PathVariable int tagId, Model model) {

        model.addAttribute("tag", service.read(tagId));
        return "tags/tag";
    }
}
