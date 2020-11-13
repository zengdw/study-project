package com.zengdw.oauth2.server.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.OAuth2Request;
import org.springframework.security.oauth2.provider.client.JdbcClientDetailsService;
import org.springframework.security.oauth2.provider.code.JdbcAuthorizationCodeServices;
import org.springframework.security.oauth2.provider.token.DefaultAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JdbcTokenStore;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

/**
 * @description: 授权服务器配置
 * @author: zengd
 * @date: 2020/10/30 14:12
 */
@Configuration
@EnableAuthorizationServer
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {
    @Resource
    private AuthenticationManager authenticationManager;
    @Resource
    private DataSource dataSource;

    /**
     * 第三方客户端配置
     */
    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients.withClientDetails(detailsService());
    }

    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) {
        security.checkTokenAccess("permitAll()")
                .tokenKeyAccess("permitAll()")
                .allowFormAuthenticationForClients();
    }

    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) {
        endpoints.authenticationManager(authenticationManager)
                //token储存位置
                .tokenStore(new JdbcTokenStore(dataSource))
                .accessTokenConverter(new CustomAccessTokenConverter())
                //使用刷新token
                .reuseRefreshTokens(true)
                //授权码模式 code存放在内存中
                .authorizationCodeServices(new JdbcAuthorizationCodeServices(dataSource));
    }

    static class CustomAccessTokenConverter extends DefaultAccessTokenConverter {

        @Override
        public Map<String, ?> convertAccessToken(OAuth2AccessToken token, OAuth2Authentication authentication) {
            Map<String, Object> response = new HashMap();
            OAuth2Request clientToken = authentication.getOAuth2Request();
            response.put("authorities", AuthorityUtils.authorityListToSet(clientToken.getAuthorities()));

            if (token.getScope() != null) {
                String scopeAttribute = "scope";
                response.put(scopeAttribute, token.getScope());
            }

            if (token.getAdditionalInformation().containsKey("jti")) {
                response.put("jti", token.getAdditionalInformation().get("jti"));
            }

            if (token.getExpiration() != null) {
                response.put("exp", token.getExpiration().getTime() / 1000L);
            }

            if (authentication.getOAuth2Request().getGrantType() != null) {
                response.put("grant_type", authentication.getOAuth2Request().getGrantType());
            }

            response.putAll(token.getAdditionalInformation());
            String clientIdAttribute = "client_id";
            response.put(clientIdAttribute, clientToken.getClientId());
            if (clientToken.getResourceIds() != null && !clientToken.getResourceIds().isEmpty()) {
                response.put("aud", clientToken.getResourceIds());
            }

            return response;
        }
    }

    @Bean
    public ClientDetailsService detailsService() {
        return new JdbcClientDetailsService(dataSource);
    }
}
