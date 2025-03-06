package com.example.demo.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.example.demo.domain.Producto;
import com.example.demo.repository.ProductoRepository;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class ProductoService {

	@Autowired
	ProductoRepository productoRepository;

	private List<Producto> productos = List.of(new Producto("1", "Laptop", 1200.0), new Producto("2", "Mouse", 25.0),
			new Producto("3", "Teclado", 45.0));

	public Flux<Producto> obtenerProductos() {
		return Flux.fromIterable(productos);
	}

//----------------- METODOS CRUD PRODUCTO CON BASE DE DATOS ------------------------//

	/**
	 * @Autor: Mariana Acevedo
	 * @Fecha: 04/03/2025
	 * @Descripcion: Inserta un producto en base de datos
	 */
	public Integer insertarProducto(Producto producto) throws Exception {
		return productoRepository.insertarProducto(producto);
	}

	/**
	 * @Autor: Mariana Acevedo
	 * @Fecha: 04/03/2025
	 * @Descripcion: Retorna los productos creados en la base de datos
	 */
	public List<Producto> obtenerAllProductos() throws Exception {
		return productoRepository.obtenerAllProductos();
	}

	/**
	 * @Autor: Mariana Acevedo
	 * @Fecha: 04/03/2025
	 * @Descripcion: Retorna un producto por codigo
	 */
	public Producto consultarById(Producto producto) throws Exception {
		return productoRepository.consultarById(producto);
	}

	/**
	 * @Autor: Mariana Acevedo
	 * @Fecha: 04/03/2025
	 * @Descripcion: Actualiza los datos de un producto por codigo
	 */
	public Integer actualizarProducto(Producto producto) throws Exception {
		return productoRepository.actualizarProducto(producto);
	}

	/**
	 * @Autor: Mariana Acevedo
	 * @Fecha: 04/03/2025
	 * @Descripcion: Elimina un producto por codigo
	 */
	public Integer eliminarProducto(String codigo) throws Exception {
		return productoRepository.eliminarProducto(codigo);
	}

	// ----------------- METODOS CRUD PRODUCTO CON BASE DE DATOS REACTIVO
	// ------------------------//

	/**
	 * @Autor: Mariana Acevedo
	 * @Fecha: 04/03/2025
	 * @Descripcion: Inserta un producto en base de datos
	 */
	public Mono<Integer> insertarProdReactivo(Producto producto) throws Exception {
		return Mono.fromCallable(() -> {
			Integer retorno = productoRepository.insertarProducto(producto);
			return retorno;
		}).onErrorResume(e -> {
			System.err.println("Exception ProductoService insertarProdReactivo: " + e.toString());
			return Mono.just(0);
		});
	}

	/**
	 * @Autor: Mariana Acevedo
	 * @Fecha: 04/03/2025
	 * @Descripcion: Retorna los productos creados en la base de datos
	 */
	public Flux<Producto> obtenerAllProductosReactivo() throws Exception {
		return Flux.fromIterable(productoRepository.obtenerAllProductos()).onErrorResume(e -> {
			System.err.println("Exception ProductoService obtenerAllProductosReactivo: " + e.toString());
			return Flux.empty();
		});

	}

	/**
	 * @Autor: Mariana Acevedo
	 * @Fecha: 04/03/2025
	 * @Descripcion: Retorna un producto por codigo
	 */
	public Mono<Producto> consultarByIdReactivo(Producto producto) {
		return Mono.fromCallable(() -> {
			Producto prod = new Producto();
			prod = productoRepository.consultarById(producto);
			if (prod != null) {
				return prod;
			}
			return null;
		}).onErrorResume(e -> {
			System.err.println("Exception ProductoService consultarByIdReactivo: " + e.toString());
			return Mono.empty();
		});
	}

	/**
	 * @Autor: Mariana Acevedo
	 * @Fecha: 04/03/2025
	 * @Descripcion: Actualiza los datos de un producto por codigo
	 */
	public Mono<Integer> actualizarProdReactivo(Producto producto) throws Exception {
		return Mono.fromCallable(() -> {
			return productoRepository.actualizarProducto(producto);
		}).onErrorResume(e -> {
			System.err.println("Exception ProductoService actualizarProdReactivo: " + e.toString());
			return Mono.just(0);
		});
	}

	/**
	 * @Autor: Mariana Acevedo
	 * @Fecha: 04/03/2025
	 * @Descripcion: Elimina un producto por codigo
	 */
	public void eliminarProdReactivo(String codigo) throws Exception {
		Mono.fromRunnable(() -> {
			try {
				productoRepository.eliminarProducto(codigo);
			} catch (Exception e) {
				System.err.println("Exception ProductoService eliminarProdReactivo: " + e.toString());
				e.printStackTrace();
			}
		}).onErrorResume(e -> {
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error al eliminar producto", e);
		});
	}

}