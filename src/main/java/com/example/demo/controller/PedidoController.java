package com.example.demo.controller;

import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.example.demo.domain.Pedido;
import com.example.demo.service.PedidoService;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/v1/pedido")
public class PedidoController {
	
	@Autowired
	PedidoService pedidoService;
	
	@Autowired
	private MessageSource messageSource;
	
	//-----------------------------------------------------------------------

		/**
		 * @Autor: Mariana Acevedo
		 * @Fecha: 04/03/2025
		 * @Descripcion: Retorna los productos creados en la base de datos
		 */
		@GetMapping(value = "/obtenerAllProducReacLanguaje", produces = MediaType.APPLICATION_JSON_VALUE)
		public Flux<Pedido> obtenerAllProducReacLanguaje(
				@RequestHeader(name = "Accept-Language", required = false) Locale locale) throws Exception {
			return pedidoService.obtenerAllProducReacLanguaje(messageSource.getMessage("pedido", null, locale))
					.onErrorResume(e -> {
						System.err.println("Exception ProductoController obtenerAllProductosReactivo: " + e.toString());
						throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error al listar los productos",
								e);
					});
		}
	//-----------------------------------------------------------------------
		
		/**
		 * @Autor: Mariana Acevedo
		 * @Fecha: 04/03/2025
		 * @Descripcion: Inserta un producto en base de datos
		 */
		@PostMapping(value = "/insertarPedido", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
		public Mono<Integer> insertarPedido(@RequestBody Pedido pedido, @RequestHeader(name = "Accept-Language", required = false) Locale locale) throws Exception {
			pedido.setMensaje(messageSource.getMessage("pedido", null, locale));
			return pedidoService.insertarPedido(pedido).onErrorResume(e -> {
				System.err.println("Exception ProductoController insertarPedido: " + e.toString());
				throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error al agregar pedido", e);
			});
		}

}
