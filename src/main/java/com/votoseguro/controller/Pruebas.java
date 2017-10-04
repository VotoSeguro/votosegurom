/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.votoseguro.controller;

import com.votoseguro.facade.VotanteFacade;
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
@ManagedBean(name = "loginController")
public class Pruebas {
   private @Getter @Setter String receptor;
   private @Getter @Setter String asunto;
   private @Getter @Setter String mensaje;
   
   @EJB
    VotanteFacade vb;
   
   public void enviarEmail(){
   vb.enviarEmail(receptor, asunto, mensaje);
   }
   
}
