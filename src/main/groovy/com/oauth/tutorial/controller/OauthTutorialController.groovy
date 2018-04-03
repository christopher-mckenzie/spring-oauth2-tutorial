package com.oauth.tutorial.controller

import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

import java.security.Principal

@RestController
class OauthTutorialController {

    @RequestMapping('/user')
    Principal user(Principal principal){
        return principal
    }
}
