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

	@NotBlank(message = "Por favor, introduzca nombre.")
	@Size(min=4, message = "Nombre al menos de 4 caracteres.")
	@Size(max=10, message = "Nombre como máximo de 10 caracteres.")
	private String nombre;

	@NotBlank(message = "Por favor, introduzca apellido.")
	@Size(min=4, message = "Nombre al menos de 4 caracteres.")
	@Size(max=10, message = "Nombre como máximo de 10 caracteres.")
	private String apellido1;
	private String apellido2;

	@NotBlank(message = "Por favor, introduzca ciudad.")
	@Size(max=10, message = "Nombre como máximo de 30 caracteres.")
	private String ciudad;

	//NotBlank no es valido para tipos numericos
	@Min(value=100, message = "Categoría debe ser mayor o igual a 100.")
	@Max(value=500, message = "Categoría debe ser menor o igual a 500.")
	private int categoria;

	//Constructor vacio para poder hacer crear-cliente
	public Cliente() {
	}
}
