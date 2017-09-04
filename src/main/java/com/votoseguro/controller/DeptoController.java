/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.votoseguro.controller;

import com.votoseguro.entity.Tbldepartamento;
import com.votoseguro.facade.DepartamentoFacade;
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

/**
 *
 * @author Luis Eduardo Valdez
 */
@ViewScoped
@ManagedBean(name = "deptoController")
public class DeptoController {

    @EJB
    DepartamentoFacade df;
    @EJB
    ValidationBean vb;

    private @Getter
    @Setter
    List<Tbldepartamento> listaDeptos = new ArrayList<>();
    private @Getter @Setter Tbldepartamento selectedDepto = new Tbldepartamento();
    private @Getter
    @Setter
    String nomDepto;
    private @Getter
    @Setter
    String maxCand;

    @PostConstruct
    public void init() {
        listaDeptos = df.obtenerDeptos();

    }

    public void insert() {
     
         if (selectedDepto == null || selectedDepto.getIddepto() == null) {
          Tbldepartamento depto = new Tbldepartamento();
          if (setValores(depto)) {
           
                
                df.insert(depto);
                vb.lanzarMensaje("info", "lblMantDepto", "lblAgregarSuccess");
                limpiar();
                listaDeptos = df.obtenerDeptos();
            

        }
         }else{
         vb.lanzarMensaje("error", "lblMantDepto", "lblDeptoReqLimp");
         }
        
        
     
       
    }
    
    public void modificar(){
       
        if (selectedDepto != null ) {
            if (setValores(selectedDepto)) {
                df.edit(selectedDepto);
                vb.lanzarMensaje("info", "lblMantDepto", "lblbtnModifiarSucces");
                limpiar();
                listaDeptos = df.obtenerDeptos();
            }
        }else{
        vb.lanzarMensaje("error", "lblMantDepto", "lblDeptoReqMod");
        }
    
    }
    
    public void validarEliminar(){
        if (selectedDepto != null) {
          
                
                
                vb.ejecutarJavascript("$('.modalPseudoClass').modal('show');");
                
          
        }else{
        vb.lanzarMensaje("error", "lblMantDepto", "lblDeptoReqMod");
        }
    }
    public void eliminar(){
                df.remove(selectedDepto);
                vb.lanzarMensaje("info", "lblMantDepto", "lblbtnModifiarSucces");
                limpiar();
                listaDeptos = df.obtenerDeptos();
    } 
    public void cerrarDialogo(){
        limpiar();
        vb.ejecutarJavascript("$('.modalPseudoClass').modal('hide'); ");
    }
    
    
    
    public boolean setValores(Tbldepartamento depto) {
        boolean flag = false;
        try {
            if (vb.validarCampoVacio(nomDepto, "warn", "lblMantDepto", "lblNomReqDepto")
                    && vb.validarSoloLetras(nomDepto, "warn", "lblMantDepto", "lblNomDeptoLetras")
                    && vb.validarLongitudCampo(nomDepto, 4, 30, "warn", "lblMantDepto", "lblLongNomDepto")
                    && vb.validarCampoVacio(maxCand, "warn", "lblMantDepto", "lblNumCDeptoReq")
                    && vb.validarSoloNumeros(maxCand, "warn", "lblMantDepto", "lblNumCDeptoNum")
                    && vb.validarLongitudCampo(maxCand, 1, 2, "warn", "lblMantDepto", "lblNumCDeptoLong")) {
                flag = true;
                depto.setNomdepto(nomDepto);
                depto.setMaxcand(new BigInteger(maxCand));
            }
        } catch (Exception e) {
            System.out.println("com.votoseguro.controller.DeptoController.setValores()");
            e.printStackTrace();
        }
        return flag;
    }

    public void limpiar() {
        selectedDepto = new Tbldepartamento();
        maxCand = "";
        nomDepto = "";
    }

    public void onSelect() {
        nomDepto = selectedDepto.getNomdepto();
        maxCand = String.valueOf(selectedDepto.getMaxcand());
        System.out.println("com.votoseguro.controller.DeptoController.onSelect()");
    }

    public void unSelect() {
        limpiar();
        System.out.println("com.votoseguro.controller.DeptoController.unSelect()");
    }

}
