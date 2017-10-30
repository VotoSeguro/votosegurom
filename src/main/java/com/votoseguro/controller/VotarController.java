package com.votoseguro.controller;

import com.votoseguro.entity.Tblcandidato;
import com.votoseguro.entity.Tblpartido;
import com.votoseguro.facade.CandidatoFacade;
import com.votoseguro.facade.PartidoFacade;
import com.votoseguro.facade.PeriodoFacade;
import com.votoseguro.facade.VotoFacade;
import com.votoseguro.util.ValidationBean;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
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
         vb.ejecutarJavascript("$('.modalPseudoClass2').modal('show'); ");
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
                    vb.ejecutarJavascript("draw('C" + candidato.getIdcandidato() + "');");
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
                    vb.ejecutarJavascript("draw('C" + candidato.getIdcandidato() + "');");
                    System.out.println("agregacand");
                    //vb.lanzarMensaje("info", "lblVotar","lblAgregarCandidato");
                vb.updateComponent("growl");
                } else {
                    vb.lanzarMensaje("error", "lblVotar", "lblMaxCand");
                    vb.updateComponent("growl");
                    System.out.println("no puede seleccionar mas de " + maxcand);
                }
            } else if (candidato.getEstadodel().equals("S")) {
                SelectedCandidatos.remove(candidato);
                candidato.setEstadodel("A");
                vb.ejecutarJavascript("limpiar('../.." + candidato.getFotourl() + "','C" + candidato.getIdcandidato() + "');");
                System.out.println("remuevecand");
                 //vb.lanzarMensaje("warn", "lblVotar","lblRemoverCandidato");
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
                  vb.ejecutarJavascript("limpiar('../.." + SelectedCandidato.getFotourl() + "','C" + SelectedCandidato.getIdcandidato() + "');"); 
                  SelectedCandidato.setEstadodel("A");
                }
                SelectedCandidatos = new ArrayList<>();
      }
      
      public void limpiarPartido(){
      SelectedPartidos = new ArrayList<>();
      
      }
      
      public void limpiarTodo(){
      limpiar();
      limpiarPartido();
      }
      
      public void validarVotar(){
         if (!SelectedCandidatos.isEmpty()) {
              try {
                  vb.ejecutarJavascript("$('.modalPseudoClass').modal('show');");
              } catch (Exception e) {
                  System.out.println("com.votoseguro.controller.VotarController.votar()");
                  e.printStackTrace();
              }
 
          }else {
      vb.lanzarMensaje("error", "lblVotar","lblSeleccReq");
                vb.updateComponent("growl");
                
      }
      
      }
      
      
      public void votar(){
          if (!SelectedCandidatos.isEmpty()) {
              try {
                  vf.votar(SelectedCandidatos, perf.obtenerPeriodoHab(), login.getLoggedVotante());
                  vb.redirecionar("/pages/votante/confirmacion.xhtml");
              } catch (Exception e) {
                  System.out.println("com.votoseguro.controller.VotarController.votar()");
                  e.printStackTrace();
              }
 
          }else {
      vb.lanzarMensaje("error", "lblVotar","lblSeleccReq");
                vb.updateComponent("growl");
                
      }
          
      
      }
      
      public void salir() throws IOException{
          vb.redirecionar("/index.xhtml");
      }
}
