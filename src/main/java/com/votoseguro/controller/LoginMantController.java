/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.votoseguro.controller;

import com.votoseguro.entity.Tblrolxpermiso;
import com.votoseguro.entity.Tblusuario;
import com.votoseguro.facade.UsuarioFacade;
import com.votoseguro.util.ValidationBean;
import java.io.IOException;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author Luis Eduardo Valdez
 */
@Named(value = "loginMant")
@SessionScoped
public class LoginMantController implements Serializable {

    @EJB
    private UsuarioFacade uf;

    @EJB
    private ValidationBean vb;
    
    private @Getter @Setter Tblusuario logedUser = new Tblusuario();
    private @Getter @Setter boolean loggedIn= false;
    private @Setter @Getter String iduser = "";
    private @Getter @Setter String userName = "";
    private @Getter @Setter String userpass = "";
    private @Getter @Setter String nombreuser = "";
    private @Getter @Setter String apellidouser = "";
    private @Getter @Setter String nivelpermiso = "";
    
    public LoginMantController() {
    }
    @PostConstruct
    public void init() {
        loggedIn = false;
        System.err.println("El valor de logged in es: " + loggedIn);
    }
    
    public String logear(){
        try {
            logedUser = uf.loguear(userName, userpass);
            if (logedUser != null) {
            iduser = String.valueOf(logedUser.getIduser());
            userName = logedUser.getUsername();
            nombreuser = logedUser.getNombreuser();
            apellidouser = logedUser.getApellidouser();
            userpass = logedUser.getPassuser();
            loggedIn = true;
                return "/gestionar.xhtml?faces-redirect=true";
            }else{
            vb.lanzarMensaje("warn", "titleLogin", "lblErrorLogin");
            System.err.println("Usuarios incorrecto");
            return  "";
            }
        } catch (Exception e) {
            return "";
        }
    
    }
    
    public void redireccionar(String pag) {
        try {
            ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();
            context.redirect(context.getRequestContextPath() + pag);
        } catch (IOException ex) {
            //JsfUtil.addErrorMessage(ex, ResourceBundle.getBundle("/Bundle").getString("titleUserNotFound"));
            //Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public String logout(){
        HttpSession session = (HttpSession) FacesContext.getCurrentInstance()
                .getExternalContext().getSession(false);
        session.invalidate();
        
        return "/loginMantenimiento.xhtml?faces-redirect=true";
    }
    
   public void setearNivel(Tblrolxpermiso rp){
       System.err.println(rp.getNivelpermiso());
       nivelpermiso = String.valueOf(rp.getNivelpermiso());
       
       
   }
    
   
}
