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
public class CandidatoPartido {
    private @Getter @Setter double total;
    private @Getter @Setter String nompartido;
    private @Getter @Setter String nomcandiato;

    public CandidatoPartido() {
    }

    public CandidatoPartido(double total, String nompartido, String nomcandiato) {
        this.total = total;
        this.nompartido = nompartido;
        this.nomcandiato = nomcandiato;
    }
    
    
}
