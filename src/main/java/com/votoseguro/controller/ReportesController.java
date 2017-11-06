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
import com.votoseguro.report.DeptosGanados;
import com.votoseguro.report.RangoEdad;
import com.votoseguro.report.VotantesDepto;
import com.votoseguro.util.ValidationBean;
import com.votoseguro.util.WriteXMLFile;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import lombok.Getter;
import lombok.Setter;
import org.primefaces.model.chart.Axis;
import org.primefaces.model.chart.AxisType;
import org.primefaces.model.chart.BarChartModel;
import org.primefaces.model.chart.BarChartSeries;
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
    
    private @Getter @Setter String pdfActual;
    private @Getter @Setter boolean pdfShow = false; 
    private @Getter @Setter List<Tblperiodo> periodoList = new ArrayList<>();
    
    
    @PostConstruct  
    public void init() {
       periodo = pf.obtenerPeriodoHaboAct();
       periodoList = pf.obtenerPeriodos();
        //createBarModel();
        logicaDeptosGanadosPorPartido();

    }
    
    public BarChartModel createBarModel(){
    BarChartModel barModel = new BarChartModel();
    List<VotantesDepto> listaVoto = new ArrayList<>();
        try {
            //Tblperiodo per = pf.obtenerPeriodoHaboAct();
            listaVoto = rf.obtenerCantidadPorDepto(periodo);
            ChartSeries votos = new ChartSeries();;
            votos.setLabel("TOTAL");
            for (VotantesDepto votantesDepto : listaVoto) {
                
                
                votos.set(votantesDepto.getNomDepto(), votantesDepto.getTotal());
                
            }
            barModel.addSeries(votos);
            
            barModel.setTitle("Total de votantes por departamento");
            barModel.setLegendPosition("e");
            Axis xAxis = barModel.getAxis(AxisType.X);
            xAxis.setLabel("Departamentos");
            xAxis.setMin(0);
            xAxis.setMax(200);
            
            Axis yAxis = barModel.getAxis(AxisType.Y);
            yAxis.setLabel("Votantes");
        } catch (Exception e) {
            System.out.println("com.votoseguro.controller.ReportesController.createBarModel()");
            e.printStackTrace();
        }
       return barModel;
    
    }
    
       public BarChartModel createBarModelDeptoGanado(){
    BarChartModel barModel = new BarChartModel();
    List<DeptosGanados> listaVoto = new ArrayList<>();
        try {
            //Tblperiodo per = pf.obtenerPeriodoHaboAct();
            listaVoto = logicaDeptosGanadosPorPartido();
            
            ChartSeries votos= new ChartSeries();
            votos = new ChartSeries();
            votos.setLabel("Total Voto");
            for (DeptosGanados dg : listaVoto) {
                
                
                
                votos.set(dg.getNompartido()+ " " + dg.getNomdepto(), dg.getTotal());
                
            }
            barModel.addSeries(votos);
            
            barModel.setTitle("Total de votantes por departamento");
            barModel.setLegendPosition("e");
            Axis xAxis = barModel.getAxis(AxisType.X);
            xAxis.setLabel("Departamentos");
            xAxis.setMin(0);
            xAxis.setMax(200);
            
            Axis yAxis = barModel.getAxis(AxisType.Y);
            yAxis.setLabel("Votantes");
        } catch (Exception e) {
            System.out.println("com.votoseguro.controller.ReportesController.createBarModel()");
            e.printStackTrace();
        }
       return barModel;
    
    }
    
    public void onchange(String id){
    vb.updateComponent(id);
    }
    
    public void prueba(){
    rf.pruebaReport();
    //vb.redirecionar("/pdf/prueba1.pdf");
    }
    
     public void cantidadGenero(){
    rf.reporteCantidadGenero(periodo);
    //vb.redirecionar("/pdf/CantidadGenero.pdf");
         mostrarModal("/pdf/CantidadGenero.pdf");
    }
     
      public void cantidadVotaron(){
    rf.reporteCantidadVotaron();
    //vb.redirecionar("/pdf/votaronynovotaron.pdf");
          mostrarModal("/pdf/votaronynovotaron.pdf");
    }
      public void cantidadDepto(){
    rf.reporteCantidadDepto(periodo);
    //vb.redirecionar("/pdf/cantidadDepto.pdf");
    mostrarModal("/pdf/cantidadDepto.pdf");
    }
      
      public String canidadCandidatoPorPartido(){
      rf.reporteCantidadCandidatoPorPartido(periodo);
      return "/pdf/diputadosPorPartido.pdf";
      
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
            pieModel.setDiameter(300);
            
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
            pieModel.setDiameter(300);
        } catch (Exception e) {
            System.out.println("com.votoseguro.controller.ReportesController.createPieModel()");
            e.printStackTrace();
        }
    
      return pieModel;
    }
    
    public PieChartModel createPieRangoEdad(){
    PieChartModel pieModel = new PieChartModel();
        try {
            List<RangoEdad> lista = obtenerListaEdad();
            for (RangoEdad re : lista) {
                pieModel.set(re.getTitulo(), re.getCantidad());
            }
            
            //pieModel.set("Ya Votaron", Double.valueOf(lista.get(0)));
           //pieModel.set("No Votaron", Double.valueOf(lista.get(1)));
            
            pieModel.setTitle("Votantes entre 18 a 30");
            pieModel.setLegendPosition("e");
            
            pieModel.setFill(false);
            pieModel.setShowDataLabels(true);
            pieModel.setDiameter(300);
        } catch (Exception e) {
            System.out.println("com.votoseguro.controller.ReportesController.createPieModel()");
            e.printStackTrace();
        }
    
      return pieModel;
    }
    
    
    
    public void mostrarModal(String url){
    
         pdfShow = true;
         pdfActual = url;
         vb.updateComponent("frmReport:amodal");
         vb.ejecutarJavascript("$('.modalPseudoClass').modal('show');");
    
    }
    
    
    public void pruebaXML(){
        WriteXMLFile f = new WriteXMLFile();
        f.crearXML(logicaDeptosGanadosPorPartido());
        rf.reporteDeptosGanados(periodo);
        mostrarModal("/pdf/deptoPartido.pdf");
    }
    
    public void edadXML(){
        WriteXMLFile f = new WriteXMLFile();
        f.crearXMLEdad(obtenerListaEdad());
        //rf.reporteDeptosGanados(periodo);
        //mostrarModal("/pdf/deptoPartido.pdf");
    }
    public List<DeptosGanados> logicaDeptosGanadosPorPartido(){
    
    List<DeptosGanados> lista = rf.obtenerDeptosGanadosPorPartido(Integer.parseInt(String.valueOf(periodo.getIdperiodo())));
    List<String> listadepto = new ArrayList<>();
    int c = 0;
    int indice= 0;
    double max = 0.0;
        for (DeptosGanados dga : lista) {
            for (String string : listadepto) {
                if (dga.getNomdepto().equals(string)) {
                    c++;
                }
            }
            
            if (c == 0) {
                listadepto.add(dga.getNomdepto());
            }
            c=0;
        }
    
        List<DeptosGanados> listaPorDepto = new ArrayList<>();
        List<DeptosGanados> listaFinal = new ArrayList<>();
         for (String string : listadepto) {
             listaPorDepto = new ArrayList<>();
             indice = 0;
             max = 0.0;
        for (int i = 0; i < lista.size(); i++) {
            
            DeptosGanados dg = lista.get(i);
           
                if (dg.getNomdepto().equals(string)) {
                    listaPorDepto.add(dg);
                }
            
            
          
           
        }
        
             for (int i = 0; i < listaPorDepto.size(); i++) {
                 double total = Double.parseDouble(String.valueOf(listaPorDepto.get(i).getTotal()));
                 if (total > max) {
                     max = total;
                     indice = i;
                 }
             }
             
             listaFinal.add(listaPorDepto.get(indice));
        
             System.out.println("com.votoseguro.controller.ReportesController.logicaDeptosGanadosPorPartido()");
        
        }
      return listaFinal;
    }
    
    
    public List<RangoEdad> obtenerListaEdad(){
        List<RangoEdad> lista = new ArrayList<>();
        lista.add(rf.obtenerDeptosGanadosPorPartido(Integer.parseInt(String.valueOf(periodo.getIdperiodo())),
                18, 30));
        lista.add(rf.obtenerDeptosGanadosPorPartido(Integer.parseInt(String.valueOf(periodo.getIdperiodo())),
                31, 60));
        
        
        
        return lista;
    
    }
    
}
