package com.zengdw.oauth2.resource.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.security.oauth2.resource.ResourceServerProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.ResourceServerTokenServices;

/**
 * @description:
 * @author: zengd
 * @date: 2020/11/13 9:38
 */
@Configuration
@EnableResourceServer
public class ResourceConfig extends ResourceServerConfigurerAdapter {
    private final ResourceServerProperties resourceServerProperties;
    private final ResourceServerTokenServices resourceServerTokenServices;

    public ResourceConfig(ResourceServerProperties resourceServerProperties, @Qualifier("remoteTokenServices") ResourceServerTokenServices resourceServerTokenServices) {
        this.resourceServerProperties = resourceServerProperties;
        this.resourceServerTokenServices = resourceServerTokenServices;
    }

    @Override
    public void configure(ResourceServerSecurityConfigurer resources) {
        resources.resourceId(resourceServerProperties.getId())
                .tokenServices(resourceServerTokenServices);
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/test").hasAuthority("/test")
                .anyRequest().authenticated();
    }
}
