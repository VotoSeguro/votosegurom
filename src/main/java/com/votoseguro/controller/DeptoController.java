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
    private @Getter
    @Setter
    Tbldepartamento depto = new Tbldepartamento();

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
        //df.insert(depto);
        /* System.out.println(depto);
    
      depto.setNomdepto(nomDepto);
      depto.setMaxcand(new BigInteger(maxCand));
        System.out.println(depto);
         */ 
        if (depto == null) {
            depto = new Tbldepartamento();
      
        }else{
         if (depto.getIddepto() == null) {
         
          if (setValores()) {
           
                
                df.insert(depto);
                vb.lanzarMensaje("info", "lblMantDepto", "lblAgregarSuccess");
                limpiar();
                listaDeptos = df.obtenerDeptos();
            

        }
         }else{
         vb.lanzarMensaje("error", "lblMantDepto", "lblDeptoReqLimp");
         }
        }
        
        if (depto.getIddepto() == null) {
         
          if (setValores()) {
           
                
                df.insert(depto);
                vb.lanzarMensaje("info", "lblMantDepto", "lblAgregarSuccess");
                limpiar();
                listaDeptos = df.obtenerDeptos();
            

        }
         }else{
         vb.lanzarMensaje("error", "lblMantDepto", "lblDeptoReqLimp");
         }
       
    }

    public boolean setValores() {
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
        depto = new Tbldepartamento();
        depto.setIddepto(null);
        depto.setMaxcand(null);
        depto.setNomdepto("");
        maxCand = "";
        nomDepto = "";
    }

    public void onSelect(Tbldepartamento d) {
        depto.setIddepto(d.getIddepto());
        depto.setNomdepto(d.getNomdepto());
        depto.setMaxcand(d.getMaxcand());
        System.out.println("com.votoseguro.controller.DeptoController.onSelect()");
    }

    public void unSelect() {
        limpiar();
        System.out.println("com.votoseguro.controller.DeptoController.unSelect()");
    }

}
