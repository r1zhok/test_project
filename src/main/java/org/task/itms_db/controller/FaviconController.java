package org.task.itms_db.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/favicon.ico")
public class FaviconController {

    @ResponseBody
    @GetMapping
    public void favicon() {
    }
}
