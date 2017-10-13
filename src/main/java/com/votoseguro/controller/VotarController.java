package com.votoseguro.controller;


import com.votoseguro.entity.Tblcandidato;
import com.votoseguro.entity.Tblpartido;
import com.votoseguro.facade.CandidatoFacade;
import com.votoseguro.facade.PartidoFacade;
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
   
   
   private @Getter
    @Setter
    List<Tblpartido> listaPartidos = new ArrayList<>();
   
   private @Getter @Setter List<Tblpartido> SelectedPartidos = new ArrayList<>();
   
   private @Getter @Setter List<Tblcandidato> SelectedCandidatos= new ArrayList<>();
   
   private @Getter @Setter boolean isNulo = false;
   
   @Inject
   VotLoginController login;
   
   @PostConstruct
    public void init() {
        
        listaPartidos = pf.obtenerPartidos();
       
         for (Tblpartido partido : listaPartidos) {
           partido.setTblcandidatoList(cf.obtenerCandidatos(String.valueOf(partido.getIdpartido()),
                   String.valueOf(login.getLoggedVotante().getIdmuni().getIddepto().getIddepto())));
       }
        

    }
   
   public void probar(){
   
      
   
   }
   
   
   public void onClickPartido(Tblpartido partido){
       System.out.println(partido.getNompartido());
       
       
       if (partido.getEstadodel().equals("A")) {
           if (SelectedPartidos.isEmpty()) {
            partido.setEstadodel("S");
             SelectedPartidos.add(partido);   
             System.out.println("agrega");
           }else{
             isNulo =true;
             System.out.println("nulo");
           }
          
       }else 
           if (partido.getEstadodel().equals("S")) {
          SelectedPartidos.remove(partido);
          partido.setEstadodel("A");
               System.out.println("remueve");
          
       }
{
       
       }
       
   
   }
      
   public void onClickCandidato(Tblcandidato candidato){
   
      
         if (candidato.getEstadodel().equals("A")) {
           
         candidato.setEstadodel("S");
         SelectedCandidatos.add(candidato);  
         System.out.println("agrega");
       }else 
           if (candidato.getEstadodel().equals("S")) {
          SelectedCandidatos.remove(candidato);
          candidato.setEstadodel("A");
          System.out.println("remueve");
          
       }
   
   }
   public String candName(Tblcandidato candidato){
   
   return candidato.getNomcand() + " " + candidato.getApecand();
   }

}
