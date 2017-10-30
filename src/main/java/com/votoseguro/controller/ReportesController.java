/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.votoseguro.controller;

import com.votoseguro.facade.ReportesFacade;
import com.votoseguro.util.ValidationBean;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

/**
 *
 * @author Luis Eduardo Valdez
 */
@ViewScoped
@ManagedBean(name = "reportes")
public class ReportesController {
    @EJB
    ValidationBean vb;
    @EJB
    ReportesFacade rf;
    
    @PostConstruct
    public void init() {
       
        

    }
    
    public void prueba(){
    rf.pruebaReport();
    vb.redirecionar("/pdf/prueba1.pdf");
    }
}
