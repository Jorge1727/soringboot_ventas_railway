package org.iesvdm;

import org.iesvdm.dao.ClienteDAOImpl;
import org.iesvdm.modelo.Cliente;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;

@SpringBootTest
public class TestVentas {
    @Autowired
    private ClienteDAOImpl clienteDAOImpl;

    @Test
    void testRecarga_id_autoIncrement(){

        Cliente cliente = new Cliente(0, "Jhon", "Perez", "Lopez", "Las Lagunas", 2);

        this.clienteDAOImpl.create(cliente);

        Assertions.assertTrue(cliente.getId() > 0);
        System.out.println("ID autoincrement: " + cliente.getId());

        this.clienteDAOImpl.delete(cliente.getId());
    }

    @Test
    void testGetAll(){

        int clientesActuales = this.clienteDAOImpl.getAll().size();
        System.out.println("Actuales = " + clientesActuales);

        Cliente cliente = new Cliente(0, "Jhon", "Perez", "Lopez", "Las Lagunas", 2);
        this.clienteDAOImpl.create(cliente);

        int clientesMasNuevo = this.clienteDAOImpl.getAll().size();
        Assertions.assertTrue(clientesMasNuevo == clientesActuales + 1);
        System.out.println("Actuales + el nuevo = " + clientesMasNuevo);

        this.clienteDAOImpl.delete(cliente.getId());
    }

    @Test
    void  testFind(){

        Cliente cliente = new Cliente(0, "Jhon", "Perez", "Lopez", "Las Lagunas", 2);
        this.clienteDAOImpl.create(cliente);

        int idABuscar = cliente.getId();

        Assertions.assertTrue(this.clienteDAOImpl.find(idABuscar).get().getNombre().equals("Jhon"));
        System.out.println("Nombre: "+ this.clienteDAOImpl.find(idABuscar).get().getNombre());

        this.clienteDAOImpl.delete(cliente.getId());
    }

    @Test
    void testUpdate() {
        Cliente cliente = new Cliente(0, "Jhon", "Perez", "Lopez", "Las Lagunas", 2);
        clienteDAOImpl.create(cliente);

        int idCliente = cliente.getId();
        System.out.println("Cliente con id: " + idCliente + ", Nombre actual: " + cliente.getNombre());

        Cliente clienteModificado = new Cliente(idCliente, "Jose", "Perez", "Lopez", "Las Lagunas", 2);
        clienteDAOImpl.update(clienteModificado);

        Assertions.assertEquals("Jose", clienteDAOImpl.find(idCliente).get().getNombre());
        System.out.println("Cliente con id: " + idCliente + ", Nombre Modificado: " + clienteDAOImpl.find(idCliente).get().getNombre());

        clienteDAOImpl.delete(cliente.getId());
    }

    @Test
    void testDelete(){

        int totalClientesAntes = this.clienteDAOImpl.getAll().size();
        System.out.println("Total Clientes antes: "+ totalClientesAntes);

        Cliente cliente = new Cliente(0, "Jhon", "Perez", "Lopez", "Las Lagunas", 2);
        clienteDAOImpl.create(cliente);
        int totalConNuevoCliente = this.clienteDAOImpl.getAll().size();
        System.out.println("Total Clientes con nuevo cliente" + totalConNuevoCliente);

        this.clienteDAOImpl.delete(cliente.getId());
        Assertions.assertTrue(this.clienteDAOImpl.getAll().size() == totalClientesAntes);
        System.out.println("Vuelve a tener los mismos tras la eliminacion : " + this.clienteDAOImpl.getAll().size() + " clientes.");
    }

}
