/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.votoseguro.controller;

import com.votoseguro.entity.Tblperiodo;
import com.votoseguro.entity.Tblvoto;
import com.votoseguro.facade.PeriodoFacade;
import com.votoseguro.facade.ReportesFacade;
import com.votoseguro.facade.VotoFacade;
import com.votoseguro.util.ValidationBean;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import org.primefaces.model.chart.BarChartModel;
import org.primefaces.model.chart.ChartSeries;

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
    @EJB
    PeriodoFacade pf;
    @EJB
    VotoFacade vf;
    @PostConstruct
    public void init() {
       
        createBarModel();

    }
    
    public BarChartModel createBarModel(){
    BarChartModel barModel = new BarChartModel();
    List<Tblvoto> listaVoto = new ArrayList<>();
        try {
            Tblperiodo per = pf.obtenerPeriodoHaboAct();
            listaVoto = vf.mostrar(String.valueOf(per.getIdperiodo()));
            ChartSeries votos = new ChartSeries();
            votos.setLabel("Votos");
            for (Tblvoto tblvoto : listaVoto) {
              votos.set(tblvoto.getIdcandidato().getNomcand(), tblvoto.getValor());
            }
            
            barModel.addSeries(votos);
        } catch (Exception e) {
            System.out.println("com.votoseguro.controller.ReportesController.createBarModel()");
            e.printStackTrace();
        }
       return barModel;
    
    }
    
    
    
    public void prueba(){
    rf.pruebaReport();
    vb.redirecionar("/pdf/prueba1.pdf");
    }
}
