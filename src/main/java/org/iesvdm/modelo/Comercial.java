package org.iesvdm.modelo;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Comercial {

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

	//NotBlank no es valido para tipos numericos
	//Al ser numeros decimales incluir anotación @DecimalMinMax
	@DecimalMin(value= "0.01" , message = "Comisión debe ser mayor o igual a 0.01.")
	@DecimalMax(value="0.99", message = "Comisión debe ser menor o igual a 0.99.")
	private float comision;

	public Comercial() {
	}
}
