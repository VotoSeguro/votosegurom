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
import java.math.BigDecimal;
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

    private @Getter
    @Setter
    List<Tblusuario> listaUsuarios = new ArrayList<>();
    private @Setter
    @Getter
    Tblusuario selectedUsuario = new Tblusuario();
    private @Getter
    @Setter
    List<Tblrol> listaRoles = new ArrayList<>();
    private @Setter
    @Getter
    String idRol = "";
    private @Setter
    @Getter
    String userName = "";
    private @Setter
    @Getter
    String passUser = "";
    private @Setter
    @Getter
    String nombreUser = "";
    private @Setter
    @Getter
    String apellidoUser = "";
    private @Setter
    @Getter
    String passRe = "";

    @PostConstruct
    public void init() {

        listaRoles = rf.obtenerRoles();
        listaUsuarios = uf.obtenerUsuarios(String.valueOf(listaRoles.get(0).getIdrol()));

    }

    public void onSelect(Tblusuario usuario) {

        selectedUsuario = usuario;
        userName = selectedUsuario.getUsername();
        nombreUser = selectedUsuario.getNombreuser();
        passUser = selectedUsuario.getPassuser();
        passRe = selectedUsuario.getPassuser();
        apellidoUser = selectedUsuario.getApellidouser();
        idRol = String.valueOf(selectedUsuario.getIdrol().getIdrol());

    }

    public void onChange() {
        listaUsuarios = uf.obtenerUsuarios(idRol);
        limpiar();

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
        apellidoUser = "";

    }

    public void insert() {

        if (selectedUsuario == null || selectedUsuario.getIduser() == null) {
            if (setValores("0")) {
                uf.create(selectedUsuario);
                listaUsuarios = uf.obtenerUsuarios(idRol);
                limpiar();

                vb.lanzarMensaje("info", "lblMantUser", "lblAgregarSuccess");

            }
        } else {
            vb.lanzarMensaje("warn", "lblMantUser", "lblLimpUser");
        }
    }

    public void modificar() {

        uf.edit(selectedUsuario);
        listaUsuarios = uf.obtenerUsuarios(idRol);
        limpiar();
        vb.lanzarMensaje("info", "lblMantUser", "lblbtnModifiarSucces");

    }

    public void eliminar() {
        selectedUsuario.setEstadodel("I");
        uf.edit(selectedUsuario);
        listaUsuarios = uf.obtenerUsuarios(idRol);
        limpiar();
        vb.lanzarMensaje("info", "lblMantUser", "lblEliminarSuccess");
    }

    public void cerrarDialogo() {
        limpiar();
        vb.ejecutarJavascript("$('.modalPseudoClass').modal('hide'); ");
    }

    public void cerrarDialogo2() {
        limpiar();
        listaUsuarios = uf.obtenerUsuarios(idRol);
        vb.ejecutarJavascript("$('.modalPseudoClass2').modal('hide'); ");
    }

    public void validarEliminar() {
        if (selectedUsuario != null && selectedUsuario.getIduser() != null) {
            vb.ejecutarJavascript("$('.modalPseudoClass').modal('show');");

        } else {
            vb.lanzarMensaje("error", "lblMantUser", "lblUserReqMod");
        }

    }

    public void validarModificar() {
        if (selectedUsuario != null && selectedUsuario.getIduser() != null) {
            if (setValores(String.valueOf(selectedUsuario.getIduser()))) {
                vb.ejecutarJavascript("$('.modalPseudoClass2').modal('show');");
            }

        } else {
            vb.lanzarMensaje("error", "lblMantUser", "lblUserReqMod");
        }

    }

    public boolean setValores(String op) {
        boolean flag = false;
        try {
            if (vb.validarCampoVacio(userName.trim(), "warn", "lblMantUser", "lblNomReqUser")
                    && vb.validarLongitudCampo(userName, 4, 10, "warn", "lblMantUser", "lblLongUserName")
                    && vb.validarCampoVacio(nombreUser.trim(), "warn", "lblMantUser", "lblNombreUsuarioReq")
                    && vb.validarSoloLetras(nombreUser.trim(), "warn", "lblMantUser", "lblNombreSoloLetras")
                    && vb.validarLongitudCampo(nombreUser, 4, 30, "warn", "lblMantUser", "lblLongNombreUsuario")
                    && vb.validarCampoVacio(apellidoUser.trim(), "warn", "lblMantUser", "lblApellidoUsuarioReq")
                    && vb.validarSoloLetras(apellidoUser.trim(), "warn", "lblMantUser", "lblApellidoSoloLetras")
                    && vb.validarLongitudCampo(apellidoUser, 4, 30, "warn", "lblMantUser", "lblLongApellidoUsuario")
                    && vb.validarCampoVacio(passUser.trim(), "warn", "lblMantUser", "lblPassUsuarioReq")
                    && vb.validarLongitudCampo(passUser, 4, 10, "warn", "lblMantUser", "lblLongPass")
                    && vb.validarCampoVacio(passRe.trim(), "warn", "lblMantUser", "lblPassReUsuarioReq")
                    && vb.validarLongitudCampo(passRe, 4, 10, "warn", "lblMantUser", "lblLongPass")) {

                if (passUser.equals(passRe)) {

                    if (uf.revisarUsername(userName, op) == 0) {
                        flag = true;
                        selectedUsuario.setUsername(userName);
                        selectedUsuario.setNombreuser(nombreUser.toUpperCase());
                        selectedUsuario.setApellidouser(apellidoUser.toUpperCase());
                        selectedUsuario.setPassuser(passUser);
                        selectedUsuario.setEstadodel("A");
                        selectedUsuario.setIdrol(rf.find(new BigDecimal(idRol)));
                    } else {
                        vb.lanzarMensaje("warn", "lblMantUser", "lblUserRepetido");
                    }
                } else {
                    vb.lanzarMensaje("warn", "lblMantUser", "lblEqualPass");
                }

            }

        } catch (Exception e) {
            System.out.println("com.votoseguro.controller.UsuarioController.setValores()");
            e.printStackTrace();
        }

        return flag;

    }

}
