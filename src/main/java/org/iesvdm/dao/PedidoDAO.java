package org.iesvdm.dao;

import org.iesvdm.modelo.Pedido;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PedidoDAO {

	public void create(Pedido pedido);
	
	public List<Pedido> getAll();
	public Optional<Pedido>  find(int id);
	
	public void update(Pedido pedido);
	
	public void delete(long id);

	/**
	 * Segun id de comercial vemos las ventas realizadas por el
	 * @param id
	 */
	public List<Pedido> pedidosIdComercial(long id);

}
