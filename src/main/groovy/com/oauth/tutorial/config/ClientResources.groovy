package com.oauth.tutorial.config

import org.springframework.boot.autoconfigure.security.oauth2.resource.ResourceServerProperties
import org.springframework.boot.context.properties.NestedConfigurationProperty
import org.springframework.security.oauth2.client.token.grant.code.AuthorizationCodeResourceDetails

/**
 * Created by mckenzie on 4/3/18.
 */
class ClientResources {

    @NestedConfigurationProperty
    AuthorizationCodeResourceDetails client = new AuthorizationCodeResourceDetails()

    @NestedConfigurationProperty
    ResourceServerProperties resource = new ResourceServerProperties()
}
