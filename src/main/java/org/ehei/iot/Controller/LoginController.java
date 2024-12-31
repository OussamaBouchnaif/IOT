package org.ehei.iot.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {

    @GetMapping("/SeConnecter")
    public String login(){
        return "login";
    }
}
