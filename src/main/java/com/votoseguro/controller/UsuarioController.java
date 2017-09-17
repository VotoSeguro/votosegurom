/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.votoseguro.controller;

import com.votoseguro.entity.Tblrol;
import com.votoseguro.entity.Tblusuario;
import com.votoseguro.facade.RolFacade;
import com.votoseguro.facade.UsuarioFacade;
import com.votoseguro.util.ValidationBean;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
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
@ManagedBean(name = "usuarioController")
public class UsuarioController {
    
    @EJB
    UsuarioFacade uf;
    @EJB
    ValidationBean vb;
    @EJB
    RolFacade rf;
    
    private @Getter @Setter List<Tblusuario> listaUsuarios= new ArrayList<>();
    private @Setter @Getter Tblusuario selectedUsuario = new Tblusuario();
    private @Getter @Setter List<Tblrol> listaRoles = new ArrayList<>();
    private @Setter @Getter String idRol = "";
    private @Setter @Getter String userName = "";
    private @Setter @Getter String passUser = "";
    private @Setter @Getter String nombreUser = "";
    private @Setter @Getter String apellidoUser = "";
    private @Setter @Getter String passRe = "";
    
    @PostConstruct
    public void init() {
        
        
        listaRoles = rf.obtenerRoles();
        listaUsuarios = uf.obtenerUsuarios(String.valueOf(listaRoles.get(0)));

    }
    
    public void onSelect(Tblusuario usuario) {

        selectedUsuario = usuario;
        userName = selectedUsuario.getUsername();
        nombreUser = selectedUsuario.getNombreuser();
        passUser = selectedUsuario.getPassuser();
        passRe = selectedUsuario.getPassuser();
        apellidoUser=selectedUsuario.getApellidouser();
        idRol = String.valueOf(selectedUsuario.getIdrol().getIdrol());
        
    } 
    
    public void onChange() {
        limpiar();
        listaUsuarios = uf.obtenerUsuarios(idRol);
    }
    
    public void deSelect() {
        limpiar();
    }
    
    public void limpiar() {
        selectedUsuario = new Tblusuario();
        userName = "";
        nombreUser = "";
        passUser = "";
        passRe = "";
        apellidoUser="";
        idRol = "";

    }
    public void insert() {}
    public void modificar(){}
    public void eliminar(){}
    public void cerrarDialogo(){
    limpiar();
        vb.ejecutarJavascript("$('.modalPseudoClass').modal('hide'); ");
    }
    public void validarEliminar(){}
    
    public boolean setValores(Tblusuario user) {return true;}
    
    
}
