package com.example.demo.service;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import com.example.demo.domain.Producto;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@TestMethodOrder(OrderAnnotation.class)
class ProductoServiceMockMvcTest {

	@Autowired
	private WebApplicationContext webApplicationContext;

	@SuppressWarnings("removal")
	@MockBean
	ProductoService productoService;

	private MockMvc mockMvc;

	@BeforeEach
	public void setUp() throws Exception {
		this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
	}

	/**
	 * @Autor: Mariana Acevedo
	 * @Fecha: 06/03/2025
	 * @Descripcion: Test MockMvc para insertar un producto en base de datos
	 */
	@Test
	@Order(1)
	public void insertarProdReactivo() throws Exception {
		when(productoService.insertarProdReactivo(any(Producto.class))).thenReturn(Mono.just(1));

		mockMvc.perform(post("/api/v1/productos/insertarProdReactivo").contentType("application/json")
				.content("{\"nombre\":\"Producto 1\",\"precio\":1000}")).andExpect(status().isOk());
	}

	/**
	 * @Autor: Mariana Acevedo
	 * @Fecha: 06/03/2025
	 * @Descripcion: Test MockMvc para obtener todos los productos en base de datos
	 */
	@Test
	@Order(2)
	public void obtenerAllProductosReactivo() throws Exception {
		Producto producto1 = new Producto("1", "Producto 1", 1000);
		Producto producto2 = new Producto("2", "Producto 2", 2000);
		when(productoService.obtenerAllProductosReactivo()).thenReturn(Flux.just(producto1, producto2));

		mockMvc.perform(get("/api/v1/productos/obtenerAllProductosReactivo").contentType("application/json"))
				.andExpect(status().isOk());

	}

	/**
	 * @Autor: Mariana Acevedo
	 * @Fecha: 06/03/2025
	 * @Descripcion: Test MockMvc para obtener un producto por id en base de datos
	 */
	@Test
	@Order(3)
	public void consultarByIdReactivo() throws Exception {
		Producto producto = new Producto("7", "Producto Test", 1000);
		when(productoService.consultarByIdReactivo(any(Producto.class))).thenReturn(Mono.just(producto));

		mockMvc.perform(post("/api/v1/productos/consultarByIdReactivo").contentType("application/json")
				.content("{\"id\":\"7\"}")).andExpect(status().isOk());
	}

	/**
	 * @Autor: Mariana Acevedo
	 * @Fecha: 06/03/2025
	 * @Descripcion: Test MockMvc para actualizar un producto en base de datos
	 */
	@Test
	@Order(4)
	public void actualizarProdReactivo() throws Exception {
		when(productoService.actualizarProdReactivo(any(Producto.class))).thenReturn(Mono.just(1));

		mockMvc.perform(put("/api/v1/productos/actualizarProdReactivo").contentType("application/json")
				.content("{\"id\":\"1\",\"nombre\":\"Producto Actualizado\",\"precio\":1500}"))
				.andExpect(status().isOk());
	}

	/**
	 * @Autor: Mariana Acevedo
	 * @Fecha: 06/03/2025
	 * @Descripcion: Test MockMvc para eliminar un producto en base de datos
	 */
	@Test
	@Order(5)
	public void eliminarProdReactivo() throws Exception {
		doNothing().when(productoService).eliminarProdReactivo("2");

		mockMvc.perform(delete("/api/v1/productos/eliminarProdReactivo/2").contentType("application/json"))
				.andExpect(status().isOk());
	}
}
