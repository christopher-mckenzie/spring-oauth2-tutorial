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
## tutorial

* [documentation](https://spring.io/guides/tutorials/spring-boot-oauth2/)