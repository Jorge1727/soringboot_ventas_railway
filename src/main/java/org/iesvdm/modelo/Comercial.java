package org.iesvdm.modelo;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
public class Comercial {

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

	//NotBlank no es valido para tipos numericos
	//Al ser numeros decimales incluir anotación @DecimalMinMax
	@NotNull(message = "{error.introducir.comision}")
	@DecimalMin(value = "0.276", inclusive = true, message = "{error.comision.size.min}")
	@DecimalMax(value = "0.946", inclusive = true, message = "{error.comision.size.max}")
	private BigDecimal comision;

	public Comercial() {
	}
}
