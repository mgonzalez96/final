package com.example.demo.controller;

import java.util.Locale;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.service.ProductoService;

import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/v1/saludar")
public class SaludoController {

	@Autowired
	private MessageSource messageSource;

	@Autowired
	ProductoService productoService;

	@GetMapping("/saludo")
	public Mono<String> obtenerSaludo(@RequestHeader(name = "Accept-Language", required = false) Locale locale) {
		return Mono.just(messageSource.getMessage("saludo", null, locale));
	}

}
