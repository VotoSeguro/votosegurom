/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.votoseguro.controller;

import com.votoseguro.entity.Tbldepartamento;
import com.votoseguro.entity.Tblperiodo;
import com.votoseguro.facade.PeriodoFacade;
import com.votoseguro.util.ValidationBean;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author Luis Eduardo Valdez
 */
@ViewScoped
@ManagedBean(name = "periodoController")
public class PeriodoController {
    
    @EJB
    PeriodoFacade pf;
    
    @EJB
    ValidationBean vb;
    
    private @Getter
    @Setter
    List<Tblperiodo> listaPeriodo = new ArrayList<>();
    private @Getter @Setter Tblperiodo selectedPeriodo = new Tblperiodo();
    private @Getter @Setter String anio = "";
    private @Getter @Setter String nomper = "";
    private @Getter @Setter String fechainicio = "";
    
    @PostConstruct
    public void init() {
        
        listaPeriodo= pf.obtenerPeriodos();

    }
    
    public void onSelect(Tblperiodo p) {

        selectedPeriodo = p;
       anio = String.valueOf(selectedPeriodo.getAnio());
       nomper = selectedPeriodo.getNomper();
       fechainicio = selectedPeriodo.getFechainicio();
    }
    
    public void deSelect() {
        limpiar();
    }
     
    public void limpiar() {
       selectedPeriodo = new Tblperiodo();
       anio = "";
       nomper = "";
       fechainicio = "";

    } 
    
    public boolean setValores(){
    boolean flag = false;
        try {
            
            
        } catch (Exception e) {
            System.out.println("com.votoseguro.controller.PeriodoController.setValores()");
            e.printStackTrace();
            
        }
    
    return flag;
    }
    
}
