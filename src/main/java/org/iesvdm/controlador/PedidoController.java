package org.iesvdm.controlador;

import jakarta.validation.Valid;
import org.iesvdm.modelo.Comercial;
import org.iesvdm.modelo.Pedido;
import org.iesvdm.service.ComercialService;
import org.iesvdm.service.PedidoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import java.util.List;
import java.util.stream.Collectors;

@Controller
//Se puede fijar ruta base de las peticiones de este controlador.
//Los mappings de los métodos tendrían este valor /pedidos como
//prefijo.
//@RequestMapping("/pedidos")
public class PedidoController {

	@Autowired
	private PedidoService pedidoService;
	@Autowired
	private ComercialService comercialService;
	//Se utiliza inyección automática por constructor del framework Spring.
	//Por tanto, se puede omitir la anotación Autowired

	//@RequestMapping(value = "/pedidos", method = RequestMethod.GET)
	//equivalente a la siguiente anotación
	@GetMapping("/pedidos") //Al no tener ruta base para el controlador, cada método tiene que tener la ruta completa
	public String listar(Model model) {
		
		List<Pedido> listaPedidos =  pedidoService.listAll();
		model.addAttribute("listaPedidos", listaPedidos);
				
		return "pedidos";// es el nombre de templates, lo redirigue all
	}

	@PostMapping("/pedidos/crear/{id_comercial}")
	public String crear(Model model, @PathVariable Integer id_comercial) {

		Pedido pedido = new Pedido();
		model.addAttribute("pedido", pedido);
		model.addAttribute("id_comercial", id_comercial);

		return "crear-pedido-comercial";
	}

	@GetMapping("/pedidos/buscar")
	public String buscar(Model model) {

		Pedido pedido = new Pedido();
		model.addAttribute("pedido", pedido);

		return "crear-pedido";
	}

	@PostMapping("/pedidos/buscar")
	public String buscar(Model model, @RequestParam("nombreBuscar")String nombreBuscar){

			//obtengo la lista de los comerciales y filtro mediante streams
			List<Comercial> listaBusqueda = this.comercialService.listAll();

			listaBusqueda = listaBusqueda.stream()
					.filter(c -> c.getNombre().toLowerCase().contains(nombreBuscar.toLowerCase()))
					.collect(Collectors.toList());

			if(listaBusqueda.isEmpty())
			{
				model.addAttribute("sinResultados", "No se encontraron resultados.");
			}

			model.addAttribute("listaBusqueda", listaBusqueda);

			return "lista-busqueda";

	}

	@PostMapping("/pedidos/crear")
	public String submitCrear(Model model, @Valid @ModelAttribute("pedido") Pedido pedido, Errors errors, @RequestParam("id_comercial") Integer id_comercial) {

		if(errors.hasErrors()){
			model.addAttribute("id_comercial", id_comercial);
			return "crear-pedido-comercial";
		}

		pedidoService.newPedido(pedido);

		return "redirect:/pedidos";

	}

	@GetMapping("/pedidos/{id}")
	public String detalle(Model model, @PathVariable Integer id ) {

		Pedido pedido = pedidoService.one(id);
		model.addAttribute("pedido", pedido);

		return "detalle-pedido";

	}

	@GetMapping("/pedidos/editar/{id}")
	public String editar(Model model, @PathVariable Integer id) {

		Pedido pedido = pedidoService.one(id);
		model.addAttribute("pedido", pedido);

		return "editar-pedido";

	}

	@PostMapping("/pedidos/editar/{id}")
	public String submitEditar(@Valid @ModelAttribute("pedido") Pedido pedido, Errors errors) {

		if(errors.hasErrors()){
			return "editar-pedido";
		}
		pedidoService.replacePedido(pedido);

		return "redirect:/pedidos";
	}

	@PostMapping("/pedidos/borrar/{id}")
	public RedirectView submitBorrar(@PathVariable Integer id) {

		pedidoService.deletePedido(id);
		return new RedirectView("/pedidos");

	}



}
