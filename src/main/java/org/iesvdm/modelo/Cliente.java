package org.iesvdm.modelo;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
//La anotación @Data de lombok proporcionará el código de: 
//getters/setters, toString, equals y hashCode
//propio de los objetos POJOS o tipo Beans
@Data
//Para generar un constructor con lombok con todos los args
@AllArgsConstructor
public class Cliente {

	private int id;

	@NotBlank(message = "{error.introducir.nombre}")
	@Size(min=4, message = "{error.nombre.size.min}")
	@Size(max=30, message = "{error.nombre.size.max}")
	private String nombre;

	@NotBlank(message = "{error.introducir.apellido}")
	@Size(min=4, message = "{error.apellido.size.min}")
	@Size(max=30, message = "{error.apellido.size.max}")
	private String apellido1;
	private String apellido2;

	@NotBlank(message = "{error.introducir.ciudad}")
	@Size(max=50, message = "{error.ciudad.size.max}")
	private String ciudad;

	//NotBlank no es valido para tipos numericos
	@Min(value=100, message = "{error.categoria.size.min}")
	@Max(value=1000, message = "{error.categoria.size.max}")
	private int categoria;

	@Email(message = "{error.email}", regexp="^[a-zA-Z0-9._-]+@[a-zA-Z0-9-]+\\.[a-zA-Z.]{2,5}")
	private String email;

	//Constructor vacio para poder hacer crear-cliente
	public Cliente() {
	}
}
