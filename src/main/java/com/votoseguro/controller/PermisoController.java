/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.votoseguro.controller;

import com.votoseguro.entity.Tblpermiso;
import com.votoseguro.entity.Tblrolxpermiso;
import com.votoseguro.facade.PermisoFacade;
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
 * @author dell
 */
@ViewScoped
@ManagedBean(name = "permisoController")
public class PermisoController {
    
    @EJB
    ValidationBean vb;
    
    @EJB
    PermisoFacade pf;
    
    
    private @Getter
    @Setter
    List<Tblpermiso> listaPermiso = new ArrayList<>();
    
    private @Setter
    @Getter
    Tblpermiso selectedPermiso = new Tblpermiso();
    
    private @Setter
    @Getter
    String urlpermiso = "";
    
    private @Setter
    @Getter
    String nombrepermiso = "";
    
    @ManagedProperty(value="#{loginMant}")
    private @Getter @Setter LoginMantController  login;
    
    private @Getter @Setter String nivelPermiso = "";
    
    @PostConstruct
    public void init() {
       
        listaPermiso = pf.obtenerPermisos();
        nivelPermiso= asignarNivel("mantpermiso.xhtml");
        

    }
    
    public void onSelect(Tblpermiso permiso) {

        selectedPermiso = permiso;
        urlpermiso = selectedPermiso.getUrlpermiso();
        nombrepermiso = selectedPermiso.getNombrepermiso();

    }
    
    public void deSelect() {
        limpiar();
    }
    
    public void limpiar() {
        selectedPermiso = new Tblpermiso();
        urlpermiso = "";
        nombrepermiso = "";

    }
    
    public void insert() {

        if (selectedPermiso == null || selectedPermiso.getIdpermiso() == null) {
            if (setValores()) {
                pf.create(selectedPermiso);
                listaPermiso = pf.obtenerPermisos();
                limpiar();

                vb.lanzarMensaje("info", "lblMantPerm", "lblAgregarSuccess");

            }
        } else {
            vb.lanzarMensaje("warn", "lblMantPerm", "lblLimpPerm");
        }
    }
    
    public void modificar() {

        pf.edit(selectedPermiso);
        listaPermiso = pf.obtenerPermisos();
        limpiar();
        vb.lanzarMensaje("info", "lblMantPerm", "lblbtnModifiarSucces");

    }
    
    public void eliminar() {
        selectedPermiso.setEstadodel("I");
        pf.edit(selectedPermiso);
        listaPermiso = pf.obtenerPermisos();
        limpiar();
        vb.lanzarMensaje("info", "lblMantPerm", "lblEliminarSuccess");
    } 
    
    public void cerrarDialogo() {
        limpiar();
        vb.ejecutarJavascript("$('.modalPseudoClass').modal('hide'); ");
    }

    public void cerrarDialogo2() {
        limpiar();
        listaPermiso = pf.obtenerPermisos();
        vb.ejecutarJavascript("$('.modalPseudoClass2').modal('hide'); ");
    }
    
    public void validarEliminar() {
        if (selectedPermiso != null && selectedPermiso.getIdpermiso() != null) {
            vb.ejecutarJavascript("$('.modalPseudoClass').modal('show');");

        } else {
            vb.lanzarMensaje("error", "lblMantPerm", "lblPermReqMod");
        }

    }

    public void validarModificar() {
        if (selectedPermiso != null && selectedPermiso.getIdpermiso() != null) {
            if (setValores()) {
                vb.ejecutarJavascript("$('.modalPseudoClass2').modal('show');");
            }

        } else {
            vb.lanzarMensaje("error", "lblMantPerm", "lblPermReqMod");
        }

    }
    
    public boolean setValores() {
        boolean flag = false;
        try {
            if (vb.validarCampoVacio(nombrepermiso, "warn", "lblMantPerm", "lblNomReqPerm")
                    && vb.validarSoloLetras(nombrepermiso, "warn", "lblMantPerm", "lblNomPermLetras")
                    && vb.validarLongitudCampo(nombrepermiso, 4, 30, "warn", "lblMantPerm", "lblLongPermName")
                    && vb.validarCampoVacio(urlpermiso, "warn", "lblMantPerm", "lblUrlReqPerm")
                    && vb.validarLongitudCampo(urlpermiso, 4, 50, "warn", "lblMantPerm", "lblLongPermUrl")) {
                 
              flag = true;
              selectedPermiso.setNombrepermiso(nombrepermiso);
              selectedPermiso.setUrlpermiso(urlpermiso);
              selectedPermiso.setEstadodel("A");

            }

        } catch (Exception e) {
            System.out.println("com.votoseguro.controller.UsuarioController.setValores()");
            e.printStackTrace();
        }

        return flag;

    }
    
    
     public String asignarNivel(String keyword){
        String res = "";
    for (Tblrolxpermiso t : login.getLogedUser().getIdrol().getTblrolxpermisoList()) {
            if (t.getIdpermiso().getUrlpermiso().toLowerCase().contains(keyword)) {
                res = String.valueOf(t.getNivelpermiso());
                
               
            }
        }
    return res;
    }
}
