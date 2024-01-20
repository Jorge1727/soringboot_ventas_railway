package org.iesvdm.dao;

import lombok.extern.slf4j.Slf4j;
import org.iesvdm.modelo.Cliente;
import org.iesvdm.modelo.Comercial;
import org.iesvdm.modelo.Pedido;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.util.List;
import java.util.Optional;

//Anotación lombok para logging (traza) de la aplicación
@Slf4j
//Un Repository es un componente y a su vez un estereotipo de Spring 
//que forma parte de la ‘capa de persistencia’.
@Repository
public class PedidoDAOImpl implements PedidoDAO {

	 //Plantilla jdbc inyectada automáticamente por el framework Spring, gracias a la anotación @Autowired.
	 @Autowired
	 private JdbcTemplate jdbcTemplate;


	
	/**
	 * Inserta en base de datos el nuevo Pedido, actualizando el id en el bean Pedido.
	 */
	@Override	
	public synchronized void create(Pedido pedido) {
		
							//Desde java15+ se tiene la triple quote """ para bloques de texto como cadenas.
		String sqlInsert = """
							INSERT INTO pedido (total, fecha, id_cliente, id_comercial) 
							VALUES  (     ?,         ?,         ?,       ?)
						   """;
		
		KeyHolder keyHolder = new GeneratedKeyHolder();
		//Con recuperación de id generado
		int rows = jdbcTemplate.update(connection -> {
			PreparedStatement ps = connection.prepareStatement(sqlInsert, new String[] { "id" });
			int idx = 1;
			ps.setDouble(idx++, pedido.getTotal());

			java.sql.Date fechaSql = new java.sql.Date(pedido.getFecha().getTime());
			ps.setDate(idx++, fechaSql);

			ps.setInt(idx++, pedido.getCliente().getId());
			ps.setInt(idx++, pedido.getComercial().getId());
			return ps;
		},keyHolder);
		
		pedido.setId(keyHolder.getKey().intValue());
		
		//Sin recuperación de id generado
//		int rows = jdbcTemplate.update(sqlInsert,
//							pedido.getNombre(),
//							pedido.getApellido1(),
//							pedido.getApellido2(),
//							pedido.getCiudad(),
//							pedido.getCategoria()
//					);

		log.info("Insertados {} registros.", rows);
	}

	@Override
	public synchronized void createCliCom(Pedido pedido, Integer id_cliente, Integer id_comercial) {

		//Desde java15+ se tiene la triple quote """ para bloques de texto como cadenas.
		String sqlInsert = """
							INSERT INTO pedido (total, fecha, id_cliente, id_comercial) 
							VALUES  (     ?,         ?,         ?,       ?)
						   """;

		KeyHolder keyHolder = new GeneratedKeyHolder();
		//Con recuperación de id generado
		int rows = jdbcTemplate.update(connection -> {
			PreparedStatement ps = connection.prepareStatement(sqlInsert, new String[] { "id" });
			int idx = 1;
			ps.setDouble(idx++, pedido.getTotal());

			java.sql.Date fechaSql = new java.sql.Date(pedido.getFecha().getTime());
			ps.setDate(idx++, fechaSql);

			ps.setInt(idx++, id_cliente);
			ps.setInt(idx++, id_comercial);
			return ps;
		},keyHolder);

		pedido.setId(keyHolder.getKey().intValue());

		//Sin recuperación de id generado
//		int rows = jdbcTemplate.update(sqlInsert,
//							pedido.getNombre(),
//							pedido.getApellido1(),
//							pedido.getApellido2(),
//							pedido.getCiudad(),
//							pedido.getCategoria()
//					);

		log.info("Insertados {} registros.", rows);
	}


	/**
	 * Devuelve lista con todos loa Pedidos.
	 */
	@Override
	public List<Pedido> getAll() {

		List<Pedido> listPedido = this.jdbcTemplate.query("""
                SELECT * FROM  pedido P left join cliente C on  P.id_cliente = C.id
                                        left join comercial CO on P.id_comercial = CO.id
                """, (rs, rowNum) -> UtilDAO.newPedido(rs)
		);

		log.info("Devueltos {} registros.", listPedido.size());
		
        return listPedido;
        
	}

	/**
	 * Devuelve Optional de Pedido con el ID dado.
	 */
	@Override
	public Optional<Pedido> find(int id) {

		Pedido pedido= this.jdbcTemplate.queryForObject("""
                    select * from pedido P left join cliente C on  P.id_cliente = C.id
                                        left join comercial CO on P.id_comercial = CO.id
                                        WHERE P.id = ?
                """, (rs, rowNum) -> UtilDAO.newPedido(rs), id);

		if (pedido != null) return Optional.of(pedido);
		log.debug("No encontrado pedido con id {} devolviendo Optional.empty()", id);
		return Optional.empty();
	}
	/**
	 * Actualiza Pedido con campos del bean Pedido según ID del mismo.
	 */
	@Override
	public void update(Pedido pedido) {
		
		int rows = jdbcTemplate.update("""
										UPDATE pedido SET 
														total = ?, 
														fecha = ?, 
														id_cliente = ?,
														id_comercial = ?  
												WHERE id = ?
										""", pedido.getTotal()
										, pedido.getFecha()
										, pedido.getCliente().getId()
										, pedido.getComercial().getId()
										, pedido.getId());
		
		log.info("Update de Pedido con {} registros actualizados.", rows);
    
	}

	/**
	 * Borra Pedido con ID proporcionado.
	 */
	@Override
	public void delete(long id) {
		
		int rows = jdbcTemplate.update("DELETE FROM pedido WHERE id = ?", id);
		
		log.info("Delete de Pedido con {} registros eliminados.", rows);
		
	}

	@Override
	public List<Pedido> pedidosIdComercial(long idComercial) {
		List<Pedido> listPedido = this.jdbcTemplate.query("""
                SELECT * FROM  pedido P left join cliente as C on  P.id_cliente = C.id
                                        left join comercial as CO on P.id_comercial = CO.id
                                        where CO.id = ?
                """, (rs, rowNum) -> UtilDAO.newPedido(rs), idComercial);

		return listPedido;
	}

}
