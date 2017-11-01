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
import org.primefaces.model.chart.PieChartModel;

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
    Tblperiodo periodo = new Tblperiodo();
    @PostConstruct
    public void init() {
       periodo = pf.obtenerPeriodoHaboAct();
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
    
     public void cantidadGenero(){
    rf.reporteCantidadGenero(Integer.parseInt(String.valueOf(periodo.getIdperiodo())));
    vb.redirecionar("/pdf/CantidadGenero.pdf");
    }
     
      public void cantidadVotaron(){
    rf.reporteCantidadVotaron();
    vb.redirecionar("/pdf/votaronynovotaron.pdf");
    }
    
    public PieChartModel createPieModelGenero(){
    PieChartModel pieModel = new PieChartModel();
        try {
            List<Integer> lista = rf.obtenerCantidadGenero(Integer.parseInt(String.valueOf(periodo.getIdperiodo())));
            pieModel.set("Hombres", lista.get(0));
            pieModel.set("Mujeres", lista.get(1));
            
            pieModel.setTitle("Cantidad de Hombres y Mujeres que votaron");
            pieModel.setLegendPosition("e");
            pieModel.setFill(false);
            pieModel.setShowDataLabels(true);
            pieModel.setDiameter(150);
        } catch (Exception e) {
            System.out.println("com.votoseguro.controller.ReportesController.createPieModel()");
            e.printStackTrace();
        }
    
      return pieModel;
    }
    
    public PieChartModel createPieModelVotaron(){
    PieChartModel pieModel = new PieChartModel();
        try {
            List<String> lista = rf.obtenerCantidadVotaron();
            pieModel.set("Ya Votaron", Double.valueOf(lista.get(0)));
            pieModel.set("No Votaron", Double.valueOf(lista.get(1)));
            
            pieModel.setTitle("Porcentaje de personas que votaron y que no votaron");
            pieModel.setLegendPosition("e");
            pieModel.setFill(false);
            pieModel.setShowDataLabels(true);
            pieModel.setDiameter(150);
        } catch (Exception e) {
            System.out.println("com.votoseguro.controller.ReportesController.createPieModel()");
            e.printStackTrace();
        }
    
      return pieModel;
    }
}
