package org.iesvdm.dao;

import lombok.extern.slf4j.Slf4j;
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

			ps.setInt(idx++, pedido.getId_cliente());
			ps.setInt(idx++, pedido.getId_comercial());
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
		
		List<Pedido> listFab = jdbcTemplate.query(
                "SELECT * FROM pedido",
                (rs, rowNum) -> new Pedido(rs.getInt("id"),
                						 	rs.getDouble("total"),
                						 	rs.getDate("fecha"),
                						 	rs.getInt("id_cliente"),
                						 	rs.getInt("id_comercial")
                						 	)
        );
		
		log.info("Devueltos {} registros.", listFab.size());
		
        return listFab;
        
	}

	/**
	 * Devuelve Optional de Pedido con el ID dado.
	 */
	@Override
	public Optional<Pedido> find(int id) {
		
		Pedido fab =  jdbcTemplate
				.queryForObject("SELECT * FROM pedido WHERE id = ?"
								, (rs, rowNum) -> new Pedido(rs.getInt("id"),
            						 						rs.getDouble("total"),
            						 						rs.getDate("fecha"),
            						 						rs.getInt("id_cliente"),
            						 						rs.getInt("id_comercial"))
								,id);
		
		if (fab != null) { 
			return Optional.of(fab);}
		else { 
			log.info("Pedido no encontrado.");
			return Optional.empty(); }
        
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
										, pedido.getId_cliente()
										, pedido.getId_comercial()
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
	public List<Pedido> findVentasComercial(long idComercial) {
		List<Pedido> listPed =  jdbcTemplate
				.query("SELECT * FROM pedido WHERE id_comercial = ?"
						, (rs, rowNum) -> new Pedido(rs.getInt("id"),
								rs.getDouble("total"),
								rs.getDate("fecha"),
								rs.getInt("id_cliente"),
								rs.getInt("id_comercial"))
						,idComercial);

		return listPed;
	}

}
