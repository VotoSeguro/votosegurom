/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.votoseguro.controller;

import com.votoseguro.entity.Tblvotante;
import com.votoseguro.facade.VotanteFacade;
import com.votoseguro.util.ValidationBean;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.ejb.EJB;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;
import lombok.Getter;
import lombok.Setter;
import org.joda.time.Days;
import org.joda.time.LocalDate;

/**
 *
 * @author Luis Eduardo Valdez
 */
@Named(value = "votController")
@SessionScoped
public class VotLoginController implements Serializable {

    /**
     * Creates a new instance of VotLoginController
     */
    
    private @Getter @Setter boolean votLogged = false;
    private @Getter @Setter Tblvotante loggedVotante = new Tblvotante();
    private @Getter @Setter String dui;
    private @Getter @Setter String pass;
    private @Getter @Setter String opentag="<div data-carousel-3d>";
    private @Getter @Setter String closetag="</div>";
    private @Getter @Setter String fechaSig = "03/03/2018";
    private @Getter @Setter boolean accesoMant = false;
    @EJB
    VotanteFacade vf;
    @EJB
    ValidationBean vb;
    
    public VotLoginController() {
    }
    
    public String login(){
    String outcome = "";
        try {
            loggedVotante = vf.logear(dui, pass);
            if (loggedVotante != null) {
                outcome = "/index.xhtml?faces-redirect=true";
                votLogged = true;
            }else{
            vb.lanzarMensaje("warn", "titleLogin", "lblErrorLogin");
            }
        } catch (Exception e) {
            System.out.println("com.votoseguro.controller.VotLoginController.login()");
            e.printStackTrace();
        }
    return outcome;
    }
    
     public String logout(){
        HttpSession session = (HttpSession) FacesContext.getCurrentInstance()
                .getExternalContext().getSession(false);
        session.invalidate();
        
        return "/index.xhtml?faces-redirect=true";
    }
     
     public String getFullName(){
     String nombrecompleto = this.loggedVotante.getNombrev().toUpperCase() + "  " + this.loggedVotante.getApellidov().toUpperCase();
     return nombrecompleto;
     }
     
     public String obtenerDiasSig(){
     
         return "";
     }
     
     public String acceder(){
     accesoMant = true;
     return "loginMantenimiento.xhtml?faces-redirect=true";
     }
     
     public  int daysBetweenUsingJoda()
     { 
         Date d1 = new Date();
         SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
         
         Date d2 = null;
         try {
             d2 = sdf.parse(fechaSig);
         } catch (Exception e) {
             e.printStackTrace();
         }
         
         return Days.daysBetween( new LocalDate(d1.getTime()),
                 new LocalDate(d2.getTime())).getDays(); 
     }


    
}
