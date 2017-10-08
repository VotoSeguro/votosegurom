package com.votoseguro.controller;


import com.votoseguro.entity.Tblcandidato;
import com.votoseguro.entity.Tblpartido;
import com.votoseguro.facade.CandidatoFacade;
import com.votoseguro.facade.PartidoFacade;
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
   private @Getter
    @Setter
    List<Tblpartido> listaPartidos = new ArrayList<>();
   
   private @Getter @Setter List<Tblpartido> SelectedPartidos;
   
   private @Getter @Setter List<Tblcandidato> SelectedCandidatos;
   
   @Inject
   VotLoginController login;
   
   @PostConstruct
    public void init() {
        
        listaPartidos = pf.obtenerPartidos();
       
         for (Tblpartido partido : listaPartidos) {
           partido.setTblcandidatoList(cf.obtenerCandidatos(String.valueOf(partido.getIdpartido())));
       }
        

    }
   
   public void probar(){
   
      
   
   }
   
   
   public void onClickPartido(Tblpartido partido){
       System.out.println(partido.getNompartido());
       SelectedPartidos.add(partido);
   
   }
   
   public String candName(Tblcandidato candidato){
   
   return candidato.getNomcand() + " " + candidato.getApecand();
   }
   
   public void onClickCandidato(Tblcandidato candidato){
   
       System.out.println(candidato.getNomcand());
       SelectedCandidatos.add(candidato);
   
   }
}
