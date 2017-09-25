/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.votoseguro.controller;

import com.votoseguro.entity.Tblcandidato;
import com.votoseguro.entity.Tbldepartamento;
import com.votoseguro.entity.Tblpartido;
import com.votoseguro.entity.Tblrolxpermiso;
import com.votoseguro.facade.CandidatoFacade;
import com.votoseguro.facade.DepartamentoFacade;
import com.votoseguro.facade.PartidoFacade;
import com.votoseguro.util.ValidationBean;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import lombok.Getter;
import lombok.Setter;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;

/**
 *
 * @author Luis Eduardo Valdez
 */
@ViewScoped
@ManagedBean(name = "candidatoController")
public class CandidatoController {

    @EJB
    CandidatoFacade cf;
    @EJB
    ValidationBean vb;
    @EJB
    DepartamentoFacade df;
    @EJB
    PartidoFacade pf;

    private @Getter
    @Setter
    List<Tbldepartamento> listaDeptos = new ArrayList<>();

    private @Getter
    @Setter
    List<Tblcandidato> listaCandidatos = new ArrayList<>();

    private @Getter
    @Setter
    List<Tblpartido> listaPartidos = new ArrayList<>();

    private @Getter
    @Setter
    Tblcandidato selectedCand = new Tblcandidato();

    private @Getter
    @Setter
    String iddepto = "";
    private @Getter
    @Setter
    String idpartido = "";
    private @Getter
    @Setter
    String nomcand = "";
    private @Getter
    @Setter
    String apecand = "";
    private @Getter
    @Setter
    String fotourl = "";
    private @Getter
    @Setter
    UploadedFile foto;
    private @Getter
    @Setter
    String fnaccand = "";
    private @Getter
    @Setter
    String msgFileFoto = "";

    private static final String SAVE_DIR = "uploads\\";

    // gets absolute path of the web application
    String appPath = System.getProperty("user.dir");

    // constructs path of the directory to save uploaded file
    String destino = appPath + File.separator + SAVE_DIR;

    @ManagedProperty(value = "#{loginMant}")
    private @Getter
    @Setter
    LoginMantController login;

    private @Getter
    @Setter
    String nivelPermiso = "";

    @PostConstruct
    public void init() {
        listaDeptos = df.obtenerDeptos();
        listaPartidos = pf.obtenerPartidos();
        listaCandidatos = cf.obtenerCandidatos(String.valueOf(listaPartidos.get(0).getIdpartido()));

        nivelPermiso = asignarNivel("mantcandidato.xhtml");

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

    public void limpiar() {
        selectedCand = new Tblcandidato();
        nomcand = "";
        apecand = "";
        fotourl = "";
        foto = null;
        fnaccand = "";
        msgFileFoto = "";
    }

    public void onSelect(Tblcandidato cand) {
        selectedCand = cand;
        nomcand = selectedCand.getNomcand();
        apecand = selectedCand.getApecand();
        fotourl = selectedCand.getFotourl();
        fnaccand = selectedCand.getFnaccand();
        iddepto = String.valueOf(selectedCand.getIddepto().getIddepto());
        idpartido = String.valueOf(selectedCand.getIdpartido().getIdpartido());
    }

    public void deSelect() {
        limpiar();
    }

    public void onChange() {
        limpiar();
        listaCandidatos = cf.obtenerCandidatos(idpartido);
    }

    public void insert() {
        if (selectedCand == null || selectedCand.getIdcandidato() == null) {
            if (setValores()) {
                cf.create(selectedCand);
                limpiar();
                vb.lanzarMensaje("info", "lblMantCand", "lblAgregarSuccess");
                listaCandidatos = cf.obtenerCandidatos(String.valueOf(idpartido));
            }
        } else {
            vb.lanzarMensaje("warn", "lblMantCand", "lblLimpCand");
        }
    }

    public void modificar() {

        cf.edit(selectedCand);
        limpiar();
        vb.lanzarMensaje("info", "lblMantCand", "lblbtnModifiarSucces");
        listaCandidatos = cf.obtenerCandidatos(String.valueOf(idpartido));
    }

    public void eliminar() {
        selectedCand.setEstadodel("I");
        cf.edit(selectedCand);
        limpiar();
        vb.lanzarMensaje("info", "lblMantCand", "lblEliminarSuccess");
        listaCandidatos = cf.obtenerCandidatos(String.valueOf(listaPartidos.get(0).getIdpartido()));

    }

    public void cerrarDialogo() {
        limpiar();
        vb.ejecutarJavascript("$('.modalPseudoClass').modal('hide'); ");
    }

    public void cerrarDialogo2() {
        limpiar();
        listaCandidatos = cf.obtenerCandidatos(idpartido);
        vb.ejecutarJavascript("$('.modalPseudoClass2').modal('hide'); ");
    }

    public void validarEliminar() {
        if (selectedCand != null || selectedCand.getIdcandidato() != null) {
            vb.ejecutarJavascript("$('.modalPseudoClass').modal('show'); ");
        } else {
            vb.lanzarMensaje("error", "lblMantCand", "lblSelectCand");
        }

    }

    public void validarModificar() {
        if (selectedCand != null || selectedCand.getIdcandidato() != null) {
            if (setValores()) {
                vb.ejecutarJavascript("$('.modalPseudoClass2').modal('show'); ");
            }
        } else {
            vb.lanzarMensaje("error", "lblMantCand", "lblSelectCand");
        }
    }

    public boolean setValores() {
        boolean flag = false;
        if (vb.validarCampoVacio(nomcand.trim(), "warn", "lblMantCand", "lblNombreCandReq")
                && vb.validarSoloLetras(nomcand, "warn", "lblMantCand", "lblNombreCandLetras")
                && vb.validarLongitudCampo(nomcand, 4, 30, "warn", "lblMantCand", "lblNombreCandLong")
                && vb.validarCampoVacio(apecand.trim(), "warn", "lblMantCand", "lblApellidoCandReq")
                && vb.validarSoloLetras(apecand, "warn", "lblMantCand", "lblApellidoCandLetras")
                && vb.validarLongitudCampo(apecand, 4, 30, "warn", "lblMantCand", "lblApellidoCandLong")
                && vb.validarCampoVacio(fnaccand, "warn", "lblMantCand", "lblFNacCandReq")
                && vb.validarCampoVacio(fotourl, "warn", "lblMantCand", "lblFotoReq")
                && vb.validarLongitudCampo(fnaccand, 10, 10, "warn", "lblMantCand", "lblFNacCandReq")
                && vb.validarFecha(fnaccand, "warn", "lblMantCand", "lblFechaInvalida")) {
            flag = true;
            selectedCand.setNomcand(nomcand.toUpperCase());
            selectedCand.setApecand(apecand.toUpperCase());
            selectedCand.setFnaccand(fnaccand);
            selectedCand.setFotourl(fotourl);
            selectedCand.setIddepto(df.find(new BigDecimal(iddepto)));
            selectedCand.setIdpartido(pf.find(new BigDecimal(idpartido)));
            selectedCand.setEstadodel("A");
        }

        return flag;
    }

    public void handleFileUploadFoto(FileUploadEvent event) {

        try {
            if (foto == null) {
                foto = event.getFile();
                vb.copyFile(event.getFile().getFileName(), destino, event.getFile().getInputstream());
                msgFileFoto = vb.getMsgBundle("lblFileSuccess");
                vb.updateComponent("candidatoForm:msgFileFoto");
                fotourl = "/" + SAVE_DIR.substring(0, 7) + "/" + event.getFile().getFileName();
            } else {
                if (vb.deleteFile(destino + foto.getFileName())) {
                    foto = event.getFile();
                    vb.copyFile(event.getFile().getFileName(), destino, event.getFile().getInputstream());
                    msgFileFoto = vb.getMsgBundle("lblFileSuccess");
                    vb.updateComponent("candidatoForm:msgFileFoto");
                    fotourl = "/" + SAVE_DIR.substring(0, 7) + "/" + event.getFile().getFileName();
                }
            }
        } catch (IOException e) {
            msgFileFoto = vb.getMsgBundle("lblFileUploadError");
            vb.updateComponent("candidatoForm:msgFileFoto");
            if (foto != null) {
                if (vb.deleteFile(destino + foto.getFileName())) {
                    foto = null;
                }
            }
            fotourl = "";
            e.printStackTrace();
        }
    }

}
