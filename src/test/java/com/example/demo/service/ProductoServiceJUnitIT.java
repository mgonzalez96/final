package com.example.demo.service;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.demo.domain.Producto;

@SpringBootTest
@TestMethodOrder(OrderAnnotation.class)
@EnableAutoConfiguration
class ProductoServiceJUnitIT {

	@Test
	void test() {
		fail("Not yet implemented");
	}
	
	@Autowired
	ProductoService productoService;

	/*@Test
	void test() {
		fail("Not yet implemented");
	}*/

	// ------------- TEST CONEXION BD ------------------------------//

	/**
	 * @Autor: Mariana Acevedo
	 * @Fecha: 06/03/2025
	 * @Descripcion: Test para insertar un producto en base de datos
	 */
	@Test
	@Order(1)
	void insertarProducto() throws Exception {
		Producto producto = new Producto();
		producto.setNombre("Producto Test");
		producto.setPrecio(1000);
		assertNotNull(productoService.insertarProducto(producto), "Producto creado correctamente");
	}

	/**
	 * @Autor: Mariana Acevedo
	 * @Fecha: 06/03/2025
	 * @Descripcion: Test para retornar los productos creados en la base de datos
	 */
	@Test
	@Order(2)
	void obtenerAllProductos() throws Exception {
		List<Producto> lista = new ArrayList<>();
		lista = productoService.obtenerAllProductos();
		assertTrue(!lista.isEmpty(), "Productos consultados correctamente");
	}

	/**
	 * @Autor: Mariana Acevedo
	 * @Fecha: 06/03/2025
	 * @Descripcion: Test para retorna un producto por codigo
	 */
	@Test
	@Order(3)
	void consultarById() throws Exception {
		Producto producto = new Producto();
		producto.setId("2");
		assertNotNull(productoService.consultarById(producto), "Producto consultado correctamente");
	}

	/**
	 * @Autor: Mariana Acevedo
	 * @Fecha: 06/03/2025
	 * @Descripcion: Test para actualizar los datos de un producto por codigo
	 */
	@Test
	@Order(4)
	void actualizarProducto() throws Exception {
		Producto producto = new Producto();
		producto.setId("2");
		producto.setNombre("Producto Test Update");
		producto.setPrecio(5000);
		assertNotNull(productoService.actualizarProducto(producto), "Producto actualizado correctamente");
	}

	/**
	 * @Autor: Mariana Acevedo
	 * @Fecha: 06/03/2025
	 * @Descripcion: Test para elimina un producto por codigo
	 */
	@Test
	@Order(5)
	void eliminarProducto() throws Exception {
		assertNotNull(productoService.eliminarProducto("3"), "Producto eliminado correctamente");
	}



}
