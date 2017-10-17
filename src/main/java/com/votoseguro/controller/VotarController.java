package com.votoseguro.controller;

import com.votoseguro.entity.Tblcandidato;
import com.votoseguro.entity.Tblpartido;
import com.votoseguro.facade.CandidatoFacade;
import com.votoseguro.facade.PartidoFacade;
import com.votoseguro.facade.PeriodoFacade;
import com.votoseguro.facade.VotoFacade;
import com.votoseguro.util.ValidationBean;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;
import lombok.Getter;
import lombok.Setter;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Luis Eduardo Valdez
 */
@ViewScoped
@ManagedBean(name = "sufragioController")
public class VotarController {

    @EJB
    PartidoFacade pf;
    @EJB
    CandidatoFacade cf;
    @EJB
    ValidationBean vb;
    @EJB
    PeriodoFacade perf;
    @EJB
    VotoFacade vf;
   

    private @Getter
    @Setter
    List<Tblpartido> listaPartidos = new ArrayList<>();

    private @Getter
    @Setter
    List<Tblpartido> SelectedPartidos = new ArrayList<>();

    private @Getter
    @Setter
    List<Tblcandidato> SelectedCandidatos = new ArrayList<>();
    private @Getter
    @Setter
    List<Tblcandidato> mostrarCandidatos = new ArrayList<>();
    private @Getter
    @Setter
    boolean isNulo = false;

    @Inject
    VotLoginController login;

    @PostConstruct
    public void init() {

        listaPartidos = pf.obtenerPartidos();

        for (Tblpartido partido : listaPartidos) {

            partido.setTblcandidatoList(cf.obtenerCandidatos(String.valueOf(partido.getIdpartido()),
                    String.valueOf(login.getLoggedVotante().getIdmuni().getIddepto().getIddepto())));

        }

        System.out.println("com.votoseguro.controller.VotarController.init()");

    }

    public void probar() {

    }

    public void onClickPartido(Tblpartido partido) {
        System.out.println(partido.getNompartido());
        int y =0;
        for (Tblcandidato cands : SelectedCandidatos) {
            if (!cands.getIdpartido().equals(partido)) {
                y++;
            }
        }
        /*if (SelectedCandidatos.isEmpty()) {
            y = true;
        }*/
       if (y == 0) {
            if (partido.getEstadodel().equals("A")) {
            if (SelectedPartidos.isEmpty()) {
                partido.setEstadodel("S");
                SelectedPartidos.add(partido);
                vb.ejecutarJavascript("draw('" + partido.getIdpartido() + "');");
                System.out.println("agregapart");
                vb.lanzarMensaje("info", "lblVotar","lblAgregarPartido");
                vb.updateComponent("growl");
                SelectedCandidatos = new ArrayList<>();
                for (Tblcandidato candidato : partido.getTblcandidatoList()) {
                    candidato.setEstadodel("S");
                    SelectedCandidatos.add(candidato);
                    vb.ejecutarJavascript("draw('" + candidato.getIdcandidato() + "');");
                }
            } else {
                isNulo = true;
                System.out.println("nulo 2 partidos selec");
                vb.lanzarMensaje("error", "lblVotar","lblNulo2Part");
                vb.updateComponent("growl");
            }

        } else if (partido.getEstadodel().equals("S")) {
            SelectedPartidos.remove(partido);
            vb.ejecutarJavascript("limpiar('../.." + partido.getBanderapartido() + "','" + partido.getIdpartido() + "');");
            partido.setEstadodel("A");
           //aqui
                limpiar();
            System.out.println("remuevepart");
            vb.lanzarMensaje("warn", "lblVotar","lblRemoverPartido");
                vb.updateComponent("growl");

        }
        }else{
            System.out.println("nulo por seleccionar un partido teniendo un candidato de otro partido seleccionado");
             vb.lanzarMensaje("error", "lblVotar","lblPartidoCO");
                vb.updateComponent("growl");
        }
        
        
       

    }

    public void onClickCandidato(Tblcandidato candidato) {
        int maxcand = Integer.parseInt(String.valueOf(login.getLoggedVotante().getIdmuni().getIddepto().getMaxcand()));

        if (SelectedPartidos.isEmpty() || SelectedPartidos.get(0).equals(candidato.getIdpartido())) {
            System.out.println("agrega cand");
            if (candidato.getEstadodel().equals("A")) {
                if (SelectedCandidatos.size() < maxcand) {
                    candidato.setEstadodel("S");
                    SelectedCandidatos.add(candidato);
                    vb.ejecutarJavascript("draw('" + candidato.getIdcandidato() + "');");
                    System.out.println("agregacand");
                    vb.lanzarMensaje("info", "lblVotar","lblAgregarCandidato");
                vb.updateComponent("growl");
                } else {
                    vb.lanzarMensaje("error", "lblVotar", "lblMaxCand");
                    vb.updateComponent("growl");
                    System.out.println("no puede seleccionar mas de " + maxcand);
                }
            } else if (candidato.getEstadodel().equals("S")) {
                SelectedCandidatos.remove(candidato);
                candidato.setEstadodel("A");
                vb.ejecutarJavascript("limpiar('../.." + candidato.getFotourl() + "','" + candidato.getIdcandidato() + "');");
                System.out.println("remuevecand");
                 vb.lanzarMensaje("warn", "lblVotar","lblRemoverCandidato");
                vb.updateComponent("growl");
            }

        } else {
            System.out.println("nulo añadio candidato de otro partido selec");
            vb.lanzarMensaje("error", "lblVotar","lblSelectCandOP");
                vb.updateComponent("growl");
        }

    }

    public String candName(Tblcandidato candidato) {

        return candidato.getNomcand() + " " + candidato.getApecand();
    }

    
      public void limpiar(){
      for (Tblcandidato SelectedCandidato : SelectedCandidatos) {
                  vb.ejecutarJavascript("limpiar('../.." + SelectedCandidato.getFotourl() + "','" + SelectedCandidato.getIdcandidato() + "');"); 
                  SelectedCandidato.setEstadodel("A");
                }
                SelectedCandidatos = new ArrayList<>();
      }
      
      
      
      public void votar(){
          
          vf.votar(SelectedCandidatos, perf.obtenerPeriodoHab(), login.getLoggedVotante());
      
      }
}
