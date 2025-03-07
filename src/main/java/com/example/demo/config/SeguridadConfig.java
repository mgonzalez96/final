package com.example.demo.config;

import java.util.Locale;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.MapReactiveUserDetailsService;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

@Configuration
@EnableAutoConfiguration
public class SeguridadConfig {

	/*
	 * Protege las rutas a los endpoints, valida a que endpoint se puede acceder
	 */
	@Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable()) // Desactiva CSRF para permitir POST, PUT y DELETE en Postman
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/api/v1/").permitAll()
                .anyRequest().authenticated()
            )
            .httpBasic(Customizer.withDefaults()) // Habilita autenticación básica
            .formLogin(Customizer.withDefaults()); // Habilita formulario de login

        return http.build();
    }

	/*
	 * Gestiona la autenticacion de usuarios, valida que la contraseña y usuario sean
	 * los que estan especificados
	 */
    @Bean
    MapReactiveUserDetailsService userDetailsService() {
        UserDetails user = User.builder()
            .username("user")
            .password("password") 
            .roles("USER")
            .build();

        return new MapReactiveUserDetailsService(user);
    }
    
	/*
	 * Gestiona y obtiene mensajes en diferentes idiomas, 
	 * por defecto va a buscar los archivos properties
	 */
	@Bean
	MessageSource messageSource() {
		ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
		messageSource.setBasename("messages");
		messageSource.setDefaultEncoding("UTF-8");
		return messageSource;
	}
//------------------------------------------------------------------///
	/**
	 * Utilizado para determinar y establecer el idioma en un aplicación web, 
	 * por defecto se dejó en Inglés
	 */
	@Bean
	LocaleResolver localeResolver() {
		SessionLocaleResolver resolver = new SessionLocaleResolver();
		resolver.setDefaultLocale(Locale.ENGLISH);
		return resolver;
	}

	/**
	 * Utilizado para cambiar el idioma en un aplicación web, utiliza un parametro en la URL
	 * ?lan= es => significa que el idioma cambiara a español
	 */
	@Bean
	LocaleChangeInterceptor localeChangeInterceptor() {
		LocaleChangeInterceptor interceptor = new LocaleChangeInterceptor();
		interceptor.setParamName("lang");
		return interceptor;
	}


	
	

}
