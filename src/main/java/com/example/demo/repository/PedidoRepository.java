package com.example.demo.repository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Repository;

import com.example.demo.domain.Pedido;

@Repository
public class PedidoRepository extends JdbcDaoSupport {

	public PedidoRepository(DataSource dataSource) {
		setDataSource(dataSource);
	}

	// ----------------------------------------------------------------------

	/**
	 * @Autor: Mariana Acevedo
	 * @Fecha: 04/03/2025
	 * @Descripcion: Retorna los productos creados en la base de datos
	 */
	String mensaje = "";

	public List<Pedido> obtenerAllProducReacLanguaje(String msj) throws Exception {
		mensaje = msj;
		try {
			String SQL = " SELECT * FROM public.pedido ";
			return getJdbcTemplate().query(SQL, obtenerAllProducReacLanguajeMapper);
		} catch (Exception e) {
			System.err.println("Exception ProductoRepository obtenerAllProductos: " + e.toString());
			throw new Exception("No existen productos en la base de datos");
		}
	}

	private RowMapper<Pedido> obtenerAllProducReacLanguajeMapper = new RowMapper<Pedido>() {
		@Override
		public Pedido mapRow(ResultSet rs, int rowNum) throws SQLException {
			var producto = new Pedido();
			producto.setMensaje(mensaje);
			producto.setProducto(rs.getInt(2));
			return producto;
		}
	};

	// ----------------------------------------------------------------------
	/**
	 * @Autor: Mariana Acevedo
	 * @Fecha: 04/03/2025
	 * @Descripcion: Inserta un producto en base de datos
	 */
	public Integer insertarPedido(Pedido pedido) throws Exception {
		try {
			String SQL = " INSERT INTO public.pedido(mensaje, producto) VALUES (?, ?) ";

			PreparedStatementSetter setter = new PreparedStatementSetter() {
				@Override
				public void setValues(PreparedStatement ps) throws SQLException {
					ps.setString(1, pedido.getMensaje());
					ps.setInt(2, pedido.getProducto());
				}
			};
			return getJdbcTemplate().update(SQL, setter);
		} catch (Exception e) {
			System.err.println("Exception ProductoRepository insertarPedido: " + e.toString());
			throw new Exception("Pedido ya existe en la base de datos");
		}
	}

}
