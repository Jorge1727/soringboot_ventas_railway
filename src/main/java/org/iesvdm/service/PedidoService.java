package org.iesvdm.service;

import org.iesvdm.dao.PedidoDAO;
import org.iesvdm.modelo.Pedido;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PedidoService {

	@Autowired
	private PedidoDAO pedidoDAO;

	//Se utiliza inyección automática por constructor del framework Spring.
	//Por tanto, se puede omitir la anotación Autowired

	public List<Pedido> listAll() {
		return pedidoDAO.getAll();
	}

	public Pedido one(Integer id) {
		Optional<Pedido> optPedido = pedidoDAO.find(id);
		if (optPedido.isPresent())
			return optPedido.get();
		else
			return null;
	}

	public void newPedido(Pedido pedido) {

		pedidoDAO.create(pedido);
	}

	public void newPedidoIds(Pedido pedido, Integer id_cliente, Integer id_comercial) {

		pedidoDAO.createCliCom(pedido, id_cliente, id_comercial);
	}

	public void replacePedido(Pedido pedido) {

		pedidoDAO.update(pedido);
	}

	public void deletePedido(int id){
		pedidoDAO.delete(id);
	}
}
