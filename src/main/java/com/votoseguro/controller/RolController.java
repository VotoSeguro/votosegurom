/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.votoseguro.controller;

import com.votoseguro.entity.Tblrol;
import com.votoseguro.entity.Tblrolxpermiso;
import com.votoseguro.facade.RolFacade;
import com.votoseguro.util.ValidationBean;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author Luis Eduardo Valdez
 */
@ViewScoped
@ManagedBean(name = "rolController")
public class RolController {
    @EJB
    RolFacade rf;
    
    @EJB
    ValidationBean vb;
    
    private @Getter @Setter List<Tblrol> listaRoles = new ArrayList<>();
    private @Setter @Getter Tblrol selectedRol = new Tblrol();
    private @Setter @Getter String nomRol = "";
    
     @ManagedProperty(value = "#{loginMant}")
    private @Getter
    @Setter
    LoginMantController login;

    private @Getter
    @Setter
    String nivelPermiso = "";
    
    @PostConstruct
    public void init() {
        
        listaRoles = rf.obtenerRoles();
        nivelPermiso = asignarNivel("mantrol.xhtml");

    }
    
    
public String asignarNivel(String keyword) {
        String res = "";
        for (Tblrolxpermiso t : login.getLogedUser().getIdrol().getTblrolxpermisoList()) {
            if (t.getIdpermiso().getUrlpermiso().toLowerCase().contains(keyword.toLowerCase())) {
                res = String.valueOf(t.getNivelpermiso());

            }
        }
        return res;
    }
    
     public void onSelect(Tblrol rol) {

        selectedRol = rol;
        nomRol = selectedRol.getNomrol();
    }
    
     public void deSelect() {
        limpiar();
    }
     
    public void limpiar() {
        selectedRol = new Tblrol();
        nomRol = "";

    } 
     
    public String contarRoles(String id){
    return rf.contarRoles(id);
    }
    
    public void insert(){
      Tblrol rol = new Tblrol();
        if (selectedRol == null || selectedRol.getIdrol() == null) {
              if (setValores(rol)) {
                  rol.setEstadodel("A");
            rf.create(rol);
            vb.lanzarMensaje("info", "lblMantRol","lblAgregarSuccess" );
            listaRoles = rf.obtenerRoles();
            limpiar();
        }
        }else{
        
                vb.lanzarMensaje("error", "lblMantRol", "lblRolReqLimp");
        }
    }
    public void modificar(){
    
        if (selectedRol != null && selectedRol.getIdrol() != null) {
            if (setValores(selectedRol)) {
                rf.edit(selectedRol);
                vb.lanzarMensaje("info", "lblMantRol","lblbtnModifiarSucces" );
                listaRoles = rf.obtenerRoles();
                limpiar();
            }
            
        }else{
        vb.lanzarMensaje("error", "lblMantRol", "lblRolReqMod");
        }
    }
    public void validarEliminar(){
     if (selectedRol != null && selectedRol.getIdrol() != null) {
          
                
                
                vb.ejecutarJavascript("$('.modalPseudoClass').modal('show');");
                
          
        }else{
        vb.lanzarMensaje("error", "lblMantRol", "lblRolReqMod");
        }
    }
    public void eliminar(){
        selectedRol.setEstadodel("I");
     rf.edit(selectedRol);
       vb.lanzarMensaje("info", "lblMantRol", "lblEliminarSuccess");
       listaRoles = rf.obtenerRoles();
       limpiar();
    }
    
    public void cerrarDialogo(){
        limpiar();
        vb.ejecutarJavascript("$('.modalPseudoClass').modal('hide'); ");
    }
    
    public boolean setValores(Tblrol rol) {
        boolean flag = false;
        try {
            if (vb.validarCampoVacio(nomRol, "warn", "lblMantRol", "lblNomReqRol")
                    && vb.validarSoloLetras(nomRol, "warn", "lblMantRol", "lblNomDRolLetras")
                    && vb.validarLongitudCampo(nomRol, 4, 30, "warn", "lblMantRol", "lblLongNomRol")) {
                flag = true;
                rol.setNomrol(nomRol.toUpperCase());
            }
        } catch (Exception e) {
            System.out.println("com.votoseguro.controller.RolController.setValores()");
            e.printStackTrace();
        }
        return flag;
    }
    
}
