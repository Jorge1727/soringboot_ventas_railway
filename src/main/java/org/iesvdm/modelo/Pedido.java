package org.iesvdm.modelo;

import java.sql.Date;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

//La anotación @Data de lombok proporcionará el código de:
//getters/setters, toString, equals y hashCode
//propio de los objetos POJOS o tipo Beans
@Data
//Para generar un constructor con lombok con todos los args
@AllArgsConstructor
public class Pedido {
    private int id;

    @NotNull(message = "El campo no puede ser nulo")
    private double total;

    private Date fecha;

    @NotNull(message = "El campo no puede ser nulo")
    private int id_cliente;

    @NotNull(message = "El campo no puede ser nulo")
    private int id_comercial;

    public Pedido(){}
}
