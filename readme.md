## oauth2 tutorial

* left off at WebSecurityConfigurer - notes
* the following dependencies are needed for oauth2 in spring boot 1.5.10
    * ```groovy
        buildscript {
        	ext {
        		springBootVersion = '1.5.10.RELEASE'
        	}
        	repositories {
        		maven {
        			url "https://plugins.gradle.org/m2/"
        		}
        		mavenCentral()
        	}
        	dependencies {
        		classpath("org.springframework.boot:spring-boot-gradle-plugin:${springBootVersion}")
        		classpath "io.spring.gradle:dependency-management-plugin:1.0.4.RELEASE"
        	}
        }
        apply plugin: 'io.spring.dependency-management'
        
        dependencyManagement {
        	imports {
        		mavenBom "org.springframework.cloud:spring-cloud-dependencies:Edgware.SR2"
        	}
        }
        compile('org.springframework.boot:spring-boot-starter-security')
        compile('org.springframework.security.oauth:spring-security-oauth2')
      ```
* with spring boot 1.5.10 the .yml should be configured
    * ```yaml
        security:
          oauth2:
            client:
              client-id: 233668646673605
              client-secret: 33b17e044ee6a4fa383f46ec6e28ea1d
              access-token-uri: https://graph.facebook.com/oauth/access_token
              user-authorization-uri: https://www.facebook.com/dialog/oauth
              token-name: oauth_token
              authentication-scheme: query
              client-authentication-scheme: form
            resource:
              user-info-uri: https://graph.facebook.com/me
        ```
* need to add the following annotation to application main class
    * ```groovy
        import org.springframework.boot.autoconfigure.security.oauth2.client.EnableOAuth2Sso
        
        @EnableOAuth2Sso
        class OauthTutorialApplication {}
      ```
* client auth in sample is from facebook developers
    * simply an oauth2 login and once logged in will be redirected to apps home page (index.html)
    * in oauth2 terms this app is a client and uses auth code grant to obtain access tokens from facebook (the authorization server)
        * we then use the access token to get personal info from facebook
    * if redirects are successful a set-cookie header is visible with JSESSIONID by default
* when extending WebSecurityConfigurerAdapter to the class that carries the annotation @EnableOAuth2Sso the annotation uses his to configure the security filter chain that carries the OAuth2 auth processor
* when creating application with authorization server can verify tokens are generating by running the following
    * ```cmd
         $ curl acme:acmesecret@localhost:8080/oauth/token -d grant_type=client_credentials
         {"access_token":"370592fd-b9f8-452d-816a-4fd5c6b4b8a6","token_type":"bearer","expires_in":43199,"scope":"read write"}
      ```
    * to get a token for a particular user use the random generated password from the logs on start up and run:
        * ```cmd
             $ curl acme:acmesecret@localhost:8080/oauth/token -d grant_type=password -d username=user -d password=...
             {"access_token":"aa49e025-c4fe-4892-86af-15af2e6b72a2","token_type":"bearer","refresh_token":"97a9f978-7aad-4af7-9329-78ff2ce9962d","expires_in":43199,"scope":"read write"}
          ```


---
## tutorial

* [documentation](https://spring.io/guides/tutorials/spring-boot-oauth2/)