/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.votoseguro.controller;

import com.votoseguro.entity.Tblusuario;
import com.votoseguro.facade.UsuarioFacade;
import com.votoseguro.util.ValidationBean;
import java.io.IOException;
import java.io.Serializable;
import javax.annotation.ManagedBean;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author Luis Eduardo Valdez
 */
@ManagedBean
@Named(value = "login")
@SessionScoped
public class LoginController implements Serializable{
    
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
    
    @PostConstruct
    public void init() {
        loggedIn = false;
        System.err.println("El valor de logged in es: " + loggedIn);
    }
    
    public void logear(){
        try {
            logedUser = uf.loguear(userName, userpass);
            if (logedUser != null) {
            iduser = String.valueOf(logedUser.getIduser());
            userName = logedUser.getUsername();
            nombreuser = logedUser.getNombreuser();
            apellidouser = logedUser.getApellidouser();
            userpass = logedUser.getPassuser();
                redireccionar("/gestionar.xhtml");
            }else{
            vb.lanzarMensaje("warn", "titleLogin", "lblErrorLogin");
            System.err.println("Usuarios incorrecto");
            }
        } catch (Exception e) {
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
    
}
