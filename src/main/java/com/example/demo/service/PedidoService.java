package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.domain.Pedido;
import com.example.demo.repository.PedidoRepository;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class PedidoService {
	
	@Autowired
	PedidoRepository pedidoRepository;
	
	//-------------------------------------------------------------------------
		/**
		 * @Autor: Mariana Acevedo
		 * @Fecha: 04/03/2025
		 * @Descripcion: Retorna los productos creados en la base de datos
		 */
		public Flux<Pedido> obtenerAllProducReacLanguaje(String mensaje) throws Exception {
			return Flux.fromIterable(pedidoRepository.obtenerAllProducReacLanguaje(mensaje)).onErrorResume(e -> {
				System.err.println("Exception PedidoService obtenerAllProducReacLanguaje: " + e.toString());
				return Flux.empty();
			});

		}
	//---------------------------------------------------------------------
		/**
		 * @Autor: Mariana Acevedo
		 * @Fecha: 04/03/2025
		 * @Descripcion: Inserta un producto en base de datos
		 */
		public Mono<Integer> insertarPedido(Pedido pedido) throws Exception {
			return Mono.fromCallable(() -> {
				Integer retorno = pedidoRepository.insertarPedido(pedido);
				return retorno;
			}).onErrorResume(e -> {
				System.err.println("Exception PedidoService insertarPedido: " + e.toString());
				return Mono.just(0);
			});
		}


}
