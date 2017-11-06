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
public class DeptosGanados {
    private @Getter @Setter double total;
    private @Getter @Setter String nomdepto;
    private @Getter @Setter String nompartido;

    public DeptosGanados() {
    }

    public DeptosGanados(double total, String nomdepto, String nompartido) {
        this.total = total;
        this.nomdepto = nomdepto;
        this.nompartido = nompartido;
    }
    
    
}
