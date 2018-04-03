package com.oauth.tutorial.controller

import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

import java.security.Principal

@RestController
class OauthTutorialController {

    @RequestMapping('/me')
    Map<String, String> me(Principal principal){
        return [name: principal.name]
    }
    @RequestMapping('/user')
    Map<String, String> user(Principal principal){
        return [name: principal.name]
    }
}
