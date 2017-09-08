/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.votoseguro.controller;

import com.votoseguro.entity.Tbldepartamento;
import com.votoseguro.util.ValidationBean;
import java.io.Serializable;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import lombok.Getter;
import lombok.Setter;
import com.votoseguro.entity.Tblmunicipio;
import com.votoseguro.facade.DepartamentoFacade;
import com.votoseguro.facade.MunicipioFacade;
import java.math.BigDecimal;

/**
 *
 * @author Luis Eduardo Valdez
 */
@ViewScoped
@ManagedBean(name = "municipioController")
public class MunicipioController {

    @EJB
    MunicipioFacade mf;
    @EJB
    DepartamentoFacade df;
    @EJB
    ValidationBean vb;
    private @Getter
    @Setter
    List<Tbldepartamento> listaDeptos = new ArrayList<>();
    private @Getter
    @Setter
    List<Tblmunicipio> listaMunis = new ArrayList<>();
    private @Getter
    @Setter
    Tblmunicipio selectedMuni = new Tblmunicipio();
    private @Getter
    @Setter
    String nomMuni = "";
    private @Getter
    @Setter
    String idDepto = "";

    @PostConstruct
    public void init() {
        listaDeptos = df.obtenerDeptos();
        listaMunis = mf.obtenerMunicipios(String.valueOf(listaDeptos.get(0).getIddepto()));

    }

    public void onSelect(Tblmunicipio muni) {

        selectedMuni = muni;
        nomMuni = selectedMuni.getNommuni();
        idDepto = String.valueOf(selectedMuni.getIddepto().getIddepto());
    }

    public void deSelect() {
        limpiar();
    }

    public void onChange() {
        limpiar();
        listaMunis = mf.obtenerMunicipios(idDepto);
    }

    public void limpiar() {
        selectedMuni = new Tblmunicipio();
        nomMuni = "";

    }

    public void insert() {
        Tblmunicipio muni = new Tblmunicipio();
        if (selectedMuni == null || selectedMuni.getIdmuni() == null) {
              if (setValores(muni)) {
            muni.setIddepto(df.find(new BigDecimal(idDepto)));
            mf.create(muni);
            vb.lanzarMensaje("info", "lblMantMuni","lblAgregarSuccess" );
            listaMunis = mf.obtenerMunicipios(idDepto);
            limpiar();
        }
        }else{
        
                vb.lanzarMensaje("error", "lblMantMuni", "lblMuniReqLimp");
        }

    }
    
    public void modificar(){
        if (selectedMuni != null && selectedMuni.getIddepto() != null) {
            if (setValores(selectedMuni)) {
                mf.edit(selectedMuni);
                vb.lanzarMensaje("info", "lblMantMuni","lblbtnModifiarSucces" );
                listaMunis = mf.obtenerMunicipios(idDepto);
                limpiar();
            }
            
        }else{
        vb.lanzarMensaje("error", "lblMantMuni", "lblMuniReqMod");
        }
    
    }
    
    public void eliminar(){
       mf.remove(selectedMuni);
       vb.lanzarMensaje("info", "lblMantMuni", "lblEliminarSuccess");
       listaMunis = mf.obtenerMunicipios(idDepto);
       limpiar();
       
    }
      public void cerrarDialogo(){
        limpiar();
        vb.ejecutarJavascript("$('.modalPseudoClass').modal('hide'); ");
    }
    public void validarEliminar(){
        if (selectedMuni != null && selectedMuni.getIdmuni() != null) {
          
                
                
                vb.ejecutarJavascript("$('.modalPseudoClass').modal('show');");
                
          
        }else{
        vb.lanzarMensaje("error", "lblMantMuni", "lblMuniReqMod");
        }
    }

    public boolean setValores(Tblmunicipio muni) {
        boolean flag = false;
        try {
            if (vb.validarCampoVacio(nomMuni, "warn", "lblMantMuni", "lblNomReqMuni")
                    && vb.validarSoloLetras(nomMuni, "warn", "lblMantMuni", "lblNomDMuniLetras")
                    && vb.validarLongitudCampo(nomMuni, 4, 30, "warn", "lblMantMuni", "lblLongNomMuni")) {
                flag = true;
                muni.setNommuni(nomMuni);
            }
        } catch (Exception e) {
            System.out.println("com.votoseguro.controller.DeptoController.setValores()");
            e.printStackTrace();
        }
        return flag;
    }
}
