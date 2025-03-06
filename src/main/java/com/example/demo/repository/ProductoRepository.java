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

import com.example.demo.domain.Producto;

@Repository
public class ProductoRepository extends JdbcDaoSupport {

	public ProductoRepository(DataSource dataSource) {
		setDataSource(dataSource);
	}

	/**
	 * @Autor: Mariana Acevedo
	 * @Fecha: 04/03/2025
	 * @Descripcion: Inserta un producto en base de datos
	 */
	public Integer insertarProducto(Producto producto) throws Exception {
		try {
			String SQL = " INSERT INTO public.producto(id, nombre, precio) VALUES (?, ?, ?) ";

			PreparedStatementSetter setter = new PreparedStatementSetter() {
				@Override
				public void setValues(PreparedStatement ps) throws SQLException {
					ps.setString(1, producto.getId());
					ps.setString(2, producto.getNombre());
					ps.setDouble(3, producto.getPrecio());
				}
			};
			return getJdbcTemplate().update(SQL, setter);
		} catch (Exception e) {
			System.err.println("Exception ProductoRepository insertarProducto: " + e.toString());
			throw new Exception("Producto ya existe en la base de datos");
		}
	}
//----------------------------------------------------------------------------

	/**
	 * @Autor: Mariana Acevedo
	 * @Fecha: 04/03/2025
	 * @Descripcion: Retorna los productos creados en la base de datos
	 */
	public List<Producto> obtenerAllProductos() throws Exception {
		try {
			String SQL = " SELECT * FROM public.producto ";
			return getJdbcTemplate().query(SQL, obtenerAllProductosMapper);
		} catch (Exception e) {
			System.err.println("Exception ProductoRepository obtenerAllProductos: " + e.toString());
			throw new Exception("No existen productos en la base de datos");
		}
	}

	private RowMapper<Producto> obtenerAllProductosMapper = new RowMapper<Producto>() {
		@Override
		public Producto mapRow(ResultSet rs, int rowNum) throws SQLException {
			var producto = new Producto();
			producto.setId(rs.getString(1));
			producto.setNombre(rs.getString(2));
			producto.setPrecio(rs.getDouble(3));
			return producto;
		}
	};

//----------------------------------------------------------------------------
	/**
	 * @Autor: Mariana Acevedo
	 * @Fecha: 04/03/2025
	 * @Descripcion: Retorna un producto por codigo
	 */
	public Producto consultarById(Producto producto) throws Exception {
		try {
			String SQL = " SELECT id, nombre, precio FROM public.producto  " + " WHERE id = ? ";
			return getJdbcTemplate().queryForObject(SQL, consultarByIdRowMapper, producto.getId());
		} catch (Exception e) {
			System.err.println("Exception ProductoRepository consultarById: " + e.toString());
			throw new Exception("Producto no existe, valide el código ingresado");
		}
	}

	private RowMapper<Producto> consultarByIdRowMapper = new RowMapper<Producto>() {
		@Override
		public Producto mapRow(ResultSet rs, int rowNum) throws SQLException {
			var producto = new Producto();
			producto.setId(rs.getString(1));
			producto.setNombre(rs.getString(2));
			producto.setPrecio(rs.getDouble(3));
			return producto;
		}
	};

//------------------------------------------------------------------------------
	/**
	 * @Autor: Mariana Acevedo
	 * @Fecha: 04/03/2025
	 * @Descripcion: Actualiza los datos de un producto por codigo
	 */
	public Integer actualizarProducto(Producto producto) throws Exception {
		try {
			String SQL = " UPDATE public.producto SET nombre=?, precio=? WHERE id=? ";

			PreparedStatementSetter setter = new PreparedStatementSetter() {
				@Override
				public void setValues(PreparedStatement ps) throws SQLException {
					ps.setString(1, producto.getNombre());
					ps.setDouble(2, producto.getPrecio());
					ps.setString(3, producto.getId());
				}
			};
			return getJdbcTemplate().update(SQL, setter);
		} catch (Exception e) {
			System.err.println("Exception ProductoRepository actualizarProducto: " + e.toString());
			throw new Exception("Producto no existe en la base de datos");
		}
	}

//------------------------------------------------------------------------------
	/**
	 * @Autor: Mariana Acevedo
	 * @Fecha: 04/03/2025
	 * @Descripcion: Elimina un producto por codigo
	 */
	public Integer eliminarProducto(String codigo) throws Exception {
		try {
			String SQL = " DELETE FROM public.producto WHERE id=? ";

			PreparedStatementSetter setter = new PreparedStatementSetter() {
				@Override
				public void setValues(PreparedStatement ps) throws SQLException {
					ps.setString(1, codigo);
				}
			};
			return getJdbcTemplate().update(SQL, setter);
		} catch (Exception e) {
			System.err.println("Exception ProductoRepository eliminarProducto: " + e.toString());
			throw new Exception("Producto no existe en la base de datos");
		}
	}

}