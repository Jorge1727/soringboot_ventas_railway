package org.iesvdm.modelo;



import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

//La anotación @Data de lombok proporcionará el código de:
//getters/setters, toString, equals y hashCode
//propio de los objetos POJOS o tipo Beans
@Data
//Para generar un constructor con lombok con todos los args
@AllArgsConstructor
public class Pedido {
    private int id;

    @NotNull(message = "El campo no puede estar vacio")
    @Positive(message = "El valor debe ser mayor o igual que 0")
    private double total;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @NotNull(message = "El campo no puede estar vacio")
    private Date fecha;

    private Cliente cliente;

    private Comercial comercial;
    public Pedido(){}

}
