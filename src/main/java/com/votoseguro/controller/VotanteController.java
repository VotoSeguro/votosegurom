/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.votoseguro.controller;

import com.votoseguro.entity.Tblmunicipio;
import com.votoseguro.entity.Tblusuario;
import com.votoseguro.entity.Tblvotante;
import com.votoseguro.facade.MunicipioFacade;
import com.votoseguro.facade.UsuarioFacade;
import com.votoseguro.facade.VotanteFacade;
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
@ManagedBean(name = "votanteController")
public class VotanteController {

    @EJB
    ValidationBean vb;
    @EJB
    MunicipioFacade mf;
    @EJB
    VotanteFacade vf;

    private @Getter
    @Setter
    List<Tblvotante> listaVotantes = new ArrayList<>();

    private @Getter
    @Setter
    List<Tblmunicipio> listaMunicipios = new ArrayList<>();

    private @Setter
    @Getter
    Tblvotante selectedVotante = new Tblvotante();

    private @Setter
    @Getter
    String nombrev = "";
    private @Setter
    @Getter
    String apellidov = "";
    private @Setter
    @Getter
    String dui = "";
    private @Setter
    @Getter
    String pregunta = "";
    private @Setter
    @Getter
    String respuesta = "";
    private @Setter
    @Getter
    String passvotante = "";
    private @Setter
    @Getter
    String passvotantere = "";
    private @Setter
    @Getter
    String genero = "";
    private @Setter
    @Getter
    String fnac = "";
    private @Setter
    @Getter
    String idMuni = "";

    @PostConstruct
    public void init() {

        listaMunicipios = mf.obtenerMunicipios();
        listaVotantes = vf.obtenerVotantes(String.valueOf(listaMunicipios.get(0).getIdmuni()));

    }

    public void onSelect(Tblvotante v) {

        selectedVotante = v;
        nombrev = selectedVotante.getNombrev();
        apellidov = selectedVotante.getApellidov();
        passvotante = selectedVotante.getPassvotante();
        passvotantere = selectedVotante.getPassvotante();
        dui = selectedVotante.getDui();
        genero = selectedVotante.getGenero();
        fnac = selectedVotante.getFnac();
        pregunta = selectedVotante.getPregunta();
        respuesta = selectedVotante.getRespuesta();
        idMuni = String.valueOf(selectedVotante.getIdmuni().getIdmuni());

    }

    public void onChange() {
        listaVotantes = vf.obtenerVotantes(idMuni);
        limpiar();

    }

    public void deSelect() {
        limpiar();
    }

    public void limpiar() {
        selectedVotante = new Tblvotante();
        nombrev = "";
        apellidov = "";
        passvotante = "";
        passvotantere = "";
        dui = "";
        genero = "";
        fnac = "";
        pregunta = "";
        respuesta = "";

    }

    public void insert() {

        if (selectedVotante == null || selectedVotante.getIdvotante() == null) {
            if (setValores()) {
                vf.create(selectedVotante);
                listaVotantes = vf.obtenerVotantes(idMuni);
                limpiar();

                vb.lanzarMensaje("info", "lblMantVot", "lblAgregarSuccess");

            }
        } else {
            vb.lanzarMensaje("warn", "lblMantVot", "lblLimpVot");
        }
    }

    public void modificar() {

        vf.edit(selectedVotante);
        listaVotantes = vf.obtenerVotantes(idMuni);
        limpiar();
        vb.lanzarMensaje("info", "lblMantVot", "lblbtnModifiarSucces");

    }

    public void eliminar() {
        selectedVotante.setEstadodel("I");
        vf.edit(selectedVotante);
        listaVotantes = vf.obtenerVotantes(idMuni);
        limpiar();
        vb.lanzarMensaje("info", "lblMantVot", "lblEliminarSuccess");
    }
     public void cerrarDialogo() {
        limpiar();
        vb.ejecutarJavascript("$('.modalPseudoClass').modal('hide'); ");
    }

    public void cerrarDialogo2() {
        limpiar();
        listaVotantes = vf.obtenerVotantes(idMuni);
        vb.ejecutarJavascript("$('.modalPseudoClass2').modal('hide'); ");
    }

    public void validarEliminar() {
        if (selectedVotante!= null && selectedVotante.getIdvotante() != null) {
            vb.ejecutarJavascript("$('.modalPseudoClass').modal('show');");

        } else {
            vb.lanzarMensaje("error", "lblMantVot", "lblVotReqMod");
        }

    }

    public void validarModificar() {
        if (selectedVotante!= null && selectedVotante.getIdvotante() != null) {
            if (setValores()) {
                vb.ejecutarJavascript("$('.modalPseudoClass2').modal('show');");
            }

        } else {
            vb.lanzarMensaje("error", "lblMantVot", "lblVotReqMod");
        }

    }
    
    public boolean setValores() {
        boolean flag = false;
        try {
            if (vb.validarCampoVacio(nombrev.trim(), "warn", "lblMantVot", "lblNomReqVot")
                    && vb.validarLongitudCampo(nombrev, 4, 50, "warn", "lblMantVot", "lblLongVotName")
                    && vb.validarSoloLetras(nombrev,  "warn", "lblMantVot", "lblLetrasVotName")
                    && vb.validarCampoVacio(apellidov.trim(), "warn", "lblMantVot", "lblApeReqVot")
                    && vb.validarLongitudCampo(apellidov, 4, 50, "warn", "lblMantVot", "lblLongVotApe")
                    && vb.validarSoloLetras(apellidov,  "warn", "lblMantVot", "lblLetrasVotApe")
                    && vb.validarLongitudCampo(dui.trim(), 10, 10, "warn", "lblMantVot", "lblLongVotDui")
                    && vb.validarCampoVacio(dui.trim(), "warn", "lblMantVot", "lblDuiReqVot")
                    && vb.validarCampoVacio(respuesta.trim(), "warn", "lblMantVot", "lblRespReqVot")
                    && vb.validarLongitudCampo(respuesta, 4, 50, "warn", "lblMantVot", "lblLongVotResp")
                    && vb.validarSoloLetras(respuesta,  "warn", "lblMantVot", "lblLetrasVotResp")
                    && vb.validarCampoVacio(fnac, "warn", "lblMantVot", "lblFnacReqVot")
                    && vb.validarLongitudCampo(fnac, 10, 10, "warn", "lblMantVot", "lblLongVotFnac")
                    && vb.validarCampoVacio(genero, "warn", "lblMantVot", "lblGenReqVot")
                    && vb.validarCampoVacio(passvotante, "warn", "lblMantVot", "lblPassReqVot")
                    && vb.validarLongitudCampo(passvotante, 4, 8, "warn", "lblMantVot", "lblLongVotPassre")
                    && vb.validarCampoVacio(passvotantere, "warn", "lblMantVot", "lblPassreReqVot")
                    && vb.validarLongitudCampo(passvotantere, 4, 8, "warn", "lblMantVot", "lblLongVotPassre")
                    ) {
                                 if (passvotante.equals(passvotantere)) {
                     flag = true;
                      selectedVotante.setNombrev(nombrev);
                      selectedVotante.setApellidov(apellidov);
                      selectedVotante.setDui(dui);
                      selectedVotante.setPregunta(pregunta);
                      selectedVotante.setRespuesta(respuesta);
                      selectedVotante.setPassvotante(passvotante);
                      selectedVotante.setGenero(genero);
                      selectedVotante.setFnac(fnac);
                      selectedVotante.setEstadodel("A");
                        /*selectedUsuario.setUsername(userName);
                        selectedUsuario.setNombreuser(nombreUser.toUpperCase());
                        selectedUsuario.setApellidouser(apellidoUser.toUpperCase());
                        selectedUsuario.setPassuser(passUser);
                        selectedUsuario.setEstadodel("A");
                        selectedUsuario.setIdrol(rf.find(new BigDecimal(idRol)));
                   */
                }else{
                                     vb.lanzarMensaje("warn", "lblMantVot", "lblPasEqualsVot");
                                 }
               

                    
                       
               

            }

        } catch (Exception e) {
            System.out.println("com.votoseguro.controller.UsuarioController.setValores()");
            e.printStackTrace();
        }

        return flag;

    }
}
