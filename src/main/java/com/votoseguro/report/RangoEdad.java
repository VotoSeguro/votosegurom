/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.votoseguro.report;

import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author Luis Eduardo Valdez
 */
public class RangoEdad {
    private  @Getter @Setter String titulo;
    private  @Getter @Setter int cantidad;

    public RangoEdad() {
    }

    public RangoEdad(String titulo, int cantidad) {
        this.titulo = titulo;
        this.cantidad = cantidad;
    }
    
    
}
