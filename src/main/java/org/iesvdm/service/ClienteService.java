package org.iesvdm.service;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.iesvdm.dao.ClienteDAO;
import org.iesvdm.dao.PedidoDAO;
import org.iesvdm.modelo.Cliente;
import org.iesvdm.modelo.Pedido;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static java.util.Comparator.comparing;

@Service
public class ClienteService {

	@Autowired
	private ClienteDAO clienteDAO;
	@Autowired
	private PedidoDAO pedidoDAO;

	//Se utiliza inyección automática por constructor del framework Spring.
	//Por tanto, se puede omitir la anotación Autowired

	public List<Cliente> listAll() {
		return clienteDAO.getAll();
	}



	public Cliente one(Integer id) {
		Optional<Cliente> optCliente = clienteDAO.find(id);
		if (optCliente.isPresent())
			return optCliente.get();
		else
			return null;
	}

	public void newCliente(Cliente cliente) {

		clienteDAO.create(cliente);
	}

	public void replaceCliente(Cliente cliente) {

		clienteDAO.update(cliente);
	}

	public void deleteCliente(int id){
		clienteDAO.delete(id);
	}


}
