package com.example.demo.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.example.demo.domain.Producto;
import com.example.demo.service.ProductoService;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/v1/productos")
public class ProductoController {

	@Autowired
	ProductoService productoService;

	@GetMapping("/listarProductos")
	public Flux<Producto> listarProductos() {
		return productoService.obtenerProductos();
	}

	// ----------------- METODOS CRUD PRODUCTO CON BASE DE DATOS
	// ------------------------//

	/**
	 * @Autor: Mariana Acevedo
	 * @Fecha: 04/03/2025
	 * @Descripcion: Inserta un producto en base de datos
	 */
	@PostMapping("/insertarProducto")
	public ResponseEntity<Integer> insertarProducto(@RequestBody Producto producto) throws Exception {
		return ResponseEntity.ok(productoService.insertarProducto(producto));
	}

	/**
	 * @Autor: Mariana Acevedo
	 * @Fecha: 04/03/2025
	 * @Descripcion: Retorna los productos creados en la base de datos
	 */
	@GetMapping("/obtenerAllProductos")
	public ResponseEntity<List<Producto>> obtenerAllProductos() throws Exception {
		return ResponseEntity.ok(productoService.obtenerAllProductos());
	}

	/**
	 * @Autor: Mariana Acevedo
	 * @Fecha: 04/03/2025
	 * @Descripcion: Retorna un producto por codigo
	 */
	@PostMapping("/consultarById")
	public ResponseEntity<Producto> consultarById(@RequestBody Producto producto) throws Exception {
		return ResponseEntity.ok(productoService.consultarById(producto));
	}

	/**
	 * @Autor: Mariana Acevedo
	 * @Fecha: 04/03/2025
	 * @Descripcion: Actualiza los datos de un producto por codigo
	 */
	@PutMapping("/actualizarProducto")
	public ResponseEntity<Integer> actualizarProducto(@RequestBody Producto producto) throws Exception {
		return ResponseEntity.ok(productoService.actualizarProducto(producto));
	}

	/**
	 * @Autor: Mariana Acevedo
	 * @Fecha: 04/03/2025
	 * @Descripcion: Elimina un producto por codigo
	 */
	@DeleteMapping("/eliminarProducto/{codigo}")
	public ResponseEntity<Integer> eliminarProducto(@PathVariable String codigo) throws Exception {
		return ResponseEntity.ok(productoService.eliminarProducto(codigo));
	}

	// ----------------- METODOS CRUD PRODUCTO CON BASE DE DATOS REACTIVO
	// ------------------------//

	/**
	 * @Autor: Mariana Acevedo
	 * @Fecha: 04/03/2025
	 * @Descripcion: Inserta un producto en base de datos
	 */
	@PostMapping(value = "/insertarProdReactivo", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public Mono<Integer> insertarProdReactivo(@RequestBody Producto producto) throws Exception {
		return productoService.insertarProdReactivo(producto).onErrorResume(e -> {
			System.err.println("Exception ProductoController insertarProdReactivo: " + e.toString());
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error al agregar producto", e);
		});
	}

	/**
	 * @Autor: Mariana Acevedo
	 * @Fecha: 04/03/2025
	 * @Descripcion: Retorna los productos creados en la base de datos
	 */
	@GetMapping(value = "/obtenerAllProductosReactivo", produces = MediaType.APPLICATION_JSON_VALUE)
	public Flux<Producto> obtenerAllProductosReactivo() throws Exception {
		return productoService.obtenerAllProductosReactivo().onErrorResume(e -> {
			System.err.println("Exception ProductoController obtenerAllProductosReactivo: " + e.toString());
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error al listar los productos", e);
		});
	}

	/**
	 * @Autor: Mariana Acevedo
	 * @Fecha: 04/03/2025
	 * @Descripcion: Retorna un producto por codigo
	 */
	@PostMapping(value = "/consultarByIdReactivo", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public Mono<Producto> consultarByIdReactivo(@RequestBody Producto producto) throws Exception {
		return productoService.consultarByIdReactivo(producto).onErrorResume(e -> {
			System.err.println("Exception ProductoController consultarByIdReactivo: " + e.toString());
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
					"Error al obtener el producto por código", e);
		});
	}

	/**
	 * @Autor: Mariana Acevedo
	 * @Fecha: 04/03/2025
	 * @Descripcion: Actualiza los datos de un producto por codigo
	 */
	@PutMapping(value = "/actualizarProdReactivo", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public Mono<Integer> actualizarProdReactivo(@RequestBody Producto producto) throws Exception {
		return productoService.actualizarProdReactivo(producto).onErrorResume(e -> {
			System.err.println("Exception ProductoController actualizarProdReactivo: " + e.toString());
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error al actualizar el producto", e);
		});
	}

	/**
	 * @Autor: Mariana Acevedo
	 * @Fecha: 04/03/2025
	 * @Descripcion: Elimina un producto por codigo
	 */
	@DeleteMapping(value = "/eliminarProdReactivo/{codigo}", produces = MediaType.APPLICATION_JSON_VALUE)
	public void eliminarProdReactivo(@PathVariable String codigo) throws Exception {
		productoService.eliminarProdReactivo(codigo);
	}

}
