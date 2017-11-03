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
public class VotantesDepto {
    private @Getter @Setter String nomDepto;
    private @Getter @Setter int total ;

    public VotantesDepto(String nomDepto, int total) {
        this.nomDepto = nomDepto;
        this.total = total;
    }
    
    
}
