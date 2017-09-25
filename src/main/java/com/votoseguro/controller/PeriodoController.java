/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.votoseguro.controller;

import com.votoseguro.entity.Tblperiodo;
import com.votoseguro.entity.Tblrolxpermiso;
import com.votoseguro.facade.PeriodoFacade;
import com.votoseguro.util.ValidationBean;
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
 * @author Luis Eduardo Valdez
 */
@ViewScoped
@ManagedBean(name = "periodoController")
public class PeriodoController {

    @EJB
    PeriodoFacade pf;

    @EJB
    ValidationBean vb;

    private @Getter
    @Setter
    List<Tblperiodo> listaPeriodo = new ArrayList<>();
    private @Getter
    @Setter
    Tblperiodo selectedPeriodo = new Tblperiodo();
    private @Getter
    @Setter
    String anio = "";
    private @Getter
    @Setter
    String nomper = "";
    private @Getter
    @Setter
    String fechainicio = "";

     @ManagedProperty(value = "#{loginMant}")
    private @Getter
    @Setter
    LoginMantController login;

    private @Getter
    @Setter
    String nivelPermiso = "";
    
    @PostConstruct
    public void init() {

        listaPeriodo = pf.obtenerPeriodos();
        nivelPermiso = asignarNivel("mantperiodo.xhtml");

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
    
    public void onSelect(Tblperiodo p) {

        selectedPeriodo = p;
        anio = String.valueOf(selectedPeriodo.getAnio());
        nomper = selectedPeriodo.getNomper();
        fechainicio = selectedPeriodo.getFechainicio();
    }

    public void deSelect() {
        limpiar();
    }

    public void limpiar() {
        selectedPeriodo = new Tblperiodo();
        anio = "";
        nomper = "";
        fechainicio = "";

    }

    public boolean setValores() {
        boolean flag = false;
        try {
            if (vb.validarCampoVacio(nomper.trim(), "warn", "lblMantPer", "lblNomPerReq")
                    && vb.validarLongitudCampo(nomper, 6, 50, "warn", "lblMantPer", "lblNomPerLong")
                    && vb.validarCampoVacio(anio.trim(), "warn", "lblMantPer", "lblAnioPerReq")
                    && vb.validarLongitudCampo(anio, 4, 4, "warn", "lblMantPer", "lblAnioPerLong")
                    && vb.validarCampoVacio(fechainicio, "warn", "lblMantPer", "lblFechaPer")
                    && vb.validarFecha(fechainicio, "warn", "lblMantPer", "lblFechaPerVal")) {

                flag = true;
                selectedPeriodo.setAnio(new BigInteger(anio));
                selectedPeriodo.setEstadodel("A");
                if (selectedPeriodo.getEstadoper() == null) {
                    selectedPeriodo.setEstadoper("ACTIVO");
                }
                
                selectedPeriodo.setFechainicio(fechainicio);
                selectedPeriodo.setNomper(nomper);
                selectedPeriodo.setFechafin(" ");

            }

        } catch (Exception e) {
            System.out.println("com.votoseguro.controller.PeriodoController.setValores()");
            e.printStackTrace();

        }

        return flag;
    }

    public void insert() {

        if (selectedPeriodo == null || selectedPeriodo.getIdperiodo() == null) {
            if (setValores()) {
                if (pf.revisarActivo() == 0) {
                    pf.create(selectedPeriodo);
                    vb.lanzarMensaje("info", "lblMantPer", "lblAgregarSuccess");
                    listaPeriodo = pf.obtenerPeriodos();
                    limpiar();
                } else {
                    vb.lanzarMensaje("warn", "lblMantPer", "lblPerActivo");
                }

            }
        } else {

            vb.lanzarMensaje("error", "lblMantPer", "lblPerReqLimp");
        }
    }

    public void modificar() {

        pf.edit(selectedPeriodo);
        listaPeriodo = pf.obtenerPeriodos();
        limpiar();
        vb.lanzarMensaje("info", "lblMantPer", "lblbtnModifiarSucces");

    }

    public void eliminar() {
        selectedPeriodo.setEstadodel("I");
        pf.edit(selectedPeriodo);
        listaPeriodo = pf.obtenerPeriodos();
        limpiar();
        vb.lanzarMensaje("info", "lblMantPer", "lblEliminarSuccess");
    }

    public void cerrarDialogo() {
        limpiar();
        vb.ejecutarJavascript("$('.modalPseudoClass').modal('hide'); ");
    }

    public void cerrarDialogo2() {
        limpiar();
        listaPeriodo = pf.obtenerPeriodos();
        vb.ejecutarJavascript("$('.modalPseudoClass2').modal('hide'); ");
    }

    public void cerrarDialogo3() {
        limpiar();
        vb.ejecutarJavascript("$('.modalPseudoClass3').modal('hide'); ");
    }

    public void validarEliminar() {
        if (selectedPeriodo != null && selectedPeriodo.getIdperiodo() != null) {
            vb.ejecutarJavascript("$('.modalPseudoClass').modal('show');");

        } else {
            vb.lanzarMensaje("error", "lblMantPer", "lblPerReqMod");
        }

    }

    public void validarModificar() {
        if (selectedPeriodo != null && selectedPeriodo.getIdperiodo() != null) {
            if (setValores()) {
                vb.ejecutarJavascript("$('.modalPseudoClass2').modal('show');");
            }

        } else {
            vb.lanzarMensaje("error", "lblMantPer", "lblPerReqMod");
        }

    }

    public void terminar() {
        selectedPeriodo.setEstadoper("TERMINADO");
        pf.edit(selectedPeriodo);
        limpiar();
        vb.lanzarMensaje("info", "lblMantPer", "lblPerTerSuccess");
        listaPeriodo = pf.obtenerPeriodos();
    }

    public void terminarPer() {
        if (selectedPeriodo != null && selectedPeriodo.getIdperiodo() != null) {
            if (selectedPeriodo.getEstadoper().equals("ACTIVO")) {

                vb.ejecutarJavascript("$('.modalPseudoClass3').modal('show');");
            } else {
                vb.lanzarMensaje("error", "lblMantPer", "lblPerReqActivo");
            }

        } else {
            vb.lanzarMensaje("error", "lblMantPer", "lblPerReqMod");
        }

    }

}
