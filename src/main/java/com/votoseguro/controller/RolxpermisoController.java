/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.votoseguro.controller;
import com.votoseguro.entity.Tblpermiso;
import com.votoseguro.entity.Tblrol;
import com.votoseguro.entity.Tblrolxpermiso;
import com.votoseguro.facade.PermisoFacade;
import com.votoseguro.facade.RolFacade;
import com.votoseguro.facade.RolxpermisoFacade;
import com.votoseguro.util.ValidationBean;
import java.math.BigDecimal;
import java.math.BigInteger;
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
@ManagedBean(name = "rolxpermisoController")
public class RolxpermisoController {
    
    @EJB
    RolxpermisoFacade rpf;
    
    @EJB
    RolFacade rf;
    
    @EJB
    PermisoFacade pf;
    
    @EJB
    ValidationBean vb;
    
    private @Getter
    @Setter
    List<Tblrol> listaRoles = new ArrayList<>();
    
     @ManagedProperty(value = "#{loginMant}")
    private @Getter
    @Setter
    LoginMantController login;

    private @Getter
    @Setter
    String nivel = "";
    
    private @Getter
    @Setter
    List<Tblpermiso> listaPermisos = new ArrayList<>();
    
    private @Getter
    @Setter
    List<Tblrolxpermiso> listaRolesxpermiso = new ArrayList<>();
    
    private @Getter @Setter Tblrolxpermiso 
            SelectedRolxpermiso = new Tblrolxpermiso();
    
    private @Setter
    @Getter
    String idRol = "";
    
    private @Setter
    @Getter
    String idPermiso = "";
    
    private @Setter
    @Getter
    String nivelPermiso = "";
    
    @PostConstruct
    public void init() {

        listaRoles = rf.obtenerRoles();
        listaPermisos = pf.obtenerPermisos();
        nivel = asignarNivel("mantrolxpermiso.xhtml");
        listaRolesxpermiso = rpf.obtenerPermisosxrol(String.valueOf(listaRoles.get(0).getIdrol()));
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
    
    public void onSelect(Tblrolxpermiso rxp) {

        SelectedRolxpermiso= rxp;
        nivelPermiso = String.valueOf(SelectedRolxpermiso.getNivelpermiso());
        
        idRol = String.valueOf(SelectedRolxpermiso.getIdrol().getIdrol());
        idPermiso = String.valueOf(SelectedRolxpermiso.getIdpermiso().getIdpermiso());

    }
    
    public void onChange() {
        listaRolesxpermiso = rpf.obtenerPermisosxrol(idRol);
        limpiar();

    }
    
    public void deSelect() {
        limpiar();
    }
    
    public void limpiar() {
        SelectedRolxpermiso = new Tblrolxpermiso();
        listaRoles = rf.obtenerRoles();
        listaPermisos = pf.obtenerPermisos();
        nivelPermiso = "1";

    }
    
    public void insert() {

        if (SelectedRolxpermiso  == null || SelectedRolxpermiso.getIdrolxpermiso() == null) {
            if (setValores()) {
                
                if (rpf.revisarRepetido(String.valueOf(SelectedRolxpermiso.getIdrol().getIdrol()),
                        String.valueOf(SelectedRolxpermiso.getIdpermiso().getIdpermiso())) == 0) {
                   rpf.create(SelectedRolxpermiso);
                listaRolesxpermiso = rpf.obtenerPermisosxrol(idRol);
                limpiar();

                vb.lanzarMensaje("info", "lblMantRolxperm", "lblAgregarSuccess");
 
                }else{
                 vb.lanzarMensaje("error", "lblMantRolxperm", "lblRolxpermRepetido");
                }
                
                
            }
        } else {
            vb.lanzarMensaje("warn", "lblMantRolxperm", "lblLimpRolxperm");
        }
    }
    
    public void modificar() {

        rpf.edit(SelectedRolxpermiso);
        listaRolesxpermiso = rpf.obtenerPermisosxrol(idRol);
        limpiar();
        vb.lanzarMensaje("info", "lblMantRolxperm", "lblbtnModifiarSucces");

    }

    public void eliminar() {
       
        rpf.remove(SelectedRolxpermiso);
        listaRolesxpermiso = rpf.obtenerPermisosxrol(idRol);
        limpiar();
        vb.lanzarMensaje("info", "lblMantRolxperm", "lblEliminarSuccess");
    }
    
    public void cerrarDialogo() {
        limpiar();
        vb.ejecutarJavascript("$('.modalPseudoClass').modal('hide'); ");
    }

    public void cerrarDialogo2() {
        limpiar();
        listaRolesxpermiso = rpf.obtenerPermisosxrol(idRol);
        vb.ejecutarJavascript("$('.modalPseudoClass2').modal('hide'); ");
    }
    
     public void validarEliminar() {
        if (SelectedRolxpermiso != null && SelectedRolxpermiso.getIdrolxpermiso() != null) {
            vb.ejecutarJavascript("$('.modalPseudoClass').modal('show');");

        } else {
            vb.lanzarMensaje("error", "lblMantRolxperm", "lblRolxpermReqMod");
        }

    }

    public void validarModificar() {
        if (SelectedRolxpermiso != null && SelectedRolxpermiso.getIdrolxpermiso() != null) {
            if (setValores()) {
                if (rpf.revisarRepetido(String.valueOf(SelectedRolxpermiso.getIdrol().getIdrol()),
                        String.valueOf(SelectedRolxpermiso.getIdpermiso().getIdpermiso()),
                        String.valueOf(SelectedRolxpermiso.getIdrolxpermiso())) == 0) {
                    vb.ejecutarJavascript("$('.modalPseudoClass2').modal('show');");
                }else{
                vb.lanzarMensaje("error", "lblMantRolxperm", "lblRolxpermRepetido");
                }
                
                
            }

        } else {
            vb.lanzarMensaje("error", "lblMantRolxperm", "lblRolxpermReqMod");
        }

    }
    
   public boolean setValores() {
        boolean flag = false;
        try {
            
                flag = true;
                SelectedRolxpermiso.setIdrol(rf.find(new BigDecimal(idRol)));
                SelectedRolxpermiso.setIdpermiso(pf.find(new BigDecimal(idPermiso)));
                SelectedRolxpermiso.setNivelpermiso(new BigInteger(nivelPermiso));
            

        } catch (Exception e) {
            System.out.println("com.votoseguro.controller.UsuarioController.setValores()");
            e.printStackTrace();
        }

        return flag;

    }  
}
