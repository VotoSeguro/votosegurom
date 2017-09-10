/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.votoseguro.controller;

import com.votoseguro.entity.Tblpartido;
import com.votoseguro.entity.Tblrol;
import com.votoseguro.facade.PartidoFacade;
import com.votoseguro.util.ValidationBean;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import lombok.Getter;
import lombok.Setter;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;

/**
 *
 * @author Luis Eduardo Valdez
 */
@ViewScoped
@ManagedBean(name = "partidoController")
public class PartidoController {

    @EJB
    PartidoFacade pf;

    @EJB
    ValidationBean vb;

    private String destino = "C:\\Users\\luis\\Desktop\\tareas\\multi\\SIS13B_VOTACIONES_LUIS_EDUARDO_VALDEZ_CISNEROS\\sisVotoBSFaces\\votoseguroM\\votoseguroM\\src\\main\\webapp\\resources\\images\\";

    private @Getter
    @Setter
    List<Tblpartido> listaPartidos = new ArrayList<>();
    private @Setter
    @Getter
    Tblpartido selectedPartido = new Tblpartido();
    //private @Setter @Getter Tblpartido partido = new Tblpartido();
    private @Setter
    @Getter
    String nomPartido = "";
    private @Setter
    @Getter
    String logoPartido = "";
    private @Setter
    @Getter
    UploadedFile logo;
    private @Setter
    @Getter
    String banderaPartido = "";
    private @Setter
    @Getter
    UploadedFile bandera;

    private @Getter
    @Setter
    String msgFileLogo;
    private @Getter
    @Setter
    String msgFileBandera;

    @PostConstruct
    public void init() {

        listaPartidos = pf.obtenerPartidos();

    }

    public void handleFileUploadLogo(FileUploadEvent event) {
        try {
            if (logo == null) {
                logo = event.getFile();
                vb.copyFile(event.getFile().getFileName(), destino, event.getFile().getInputstream());
                msgFileLogo = vb.getMsgBundle("lblFileSuccess");
                vb.updateComponent("partidoForm:msgFileLogo");
                logoPartido = event.getFile().getFileName();
            } else {
                if (vb.deleteFile(destino + logo.getFileName())) {
                    logo = event.getFile();
                    vb.copyFile(event.getFile().getFileName(), destino, event.getFile().getInputstream());
                    msgFileLogo = vb.getMsgBundle("lblFileSuccess");
                    vb.updateComponent("partidoForm:msgFileLogo");
                    logoPartido = event.getFile().getFileName();
                }
            }
        } catch (IOException e) {
            msgFileLogo = vb.getMsgBundle("lblFileUploadError");
            vb.updateComponent("partidoForm:msgFileLogo");
            if (logo != null) {
                if (vb.deleteFile(destino + logo.getFileName())) {
                    logo = null;
                }
            }
            logoPartido = "";
            e.printStackTrace();
        }
    }

    public void handleFileUploadBandera(FileUploadEvent event) {
        try {
            if (bandera == null) {
                bandera = event.getFile();
                vb.copyFile(event.getFile().getFileName(), destino, event.getFile().getInputstream());
                msgFileBandera = vb.getMsgBundle("lblFileSuccess");
                vb.updateComponent("partidoForm:msgFileBandera");
                banderaPartido = event.getFile().getFileName();
            } else {
                if (vb.deleteFile(destino + bandera.getFileName())) {
                    bandera = event.getFile();
                    vb.copyFile(event.getFile().getFileName(), destino, event.getFile().getInputstream());
                    msgFileBandera = vb.getMsgBundle("lblFileSuccess");
                    vb.updateComponent("partidoForm:msgFileBandera");
                    banderaPartido = event.getFile().getFileName();
                }
            }
        } catch (IOException e) {
            msgFileBandera = vb.getMsgBundle("lblFileUploadError");
            vb.updateComponent("partidoForm:msgFileBandera");
            if (bandera != null) {
                if (vb.deleteFile(destino + bandera.getFileName())) {
                    bandera = null;
                }
            }
            banderaPartido = "";
            e.printStackTrace();
        }
    }

    public void insert() {
        if (setValores()) {
            pf.create(selectedPartido);
            limpiar();
            listaPartidos = pf.obtenerPartidos();
        }
    }

    public void modificar() {
    }

    public void validarEliminar() {
    }

    public void eliminar() {
    }

    public void limpiar() {
        selectedPartido = new Tblpartido();
        nomPartido = "";
        banderaPartido = "";
        logoPartido = "";
    }

    public void cerrarDialogo() {
        limpiar();
        vb.ejecutarJavascript("$('.modalPseudoClass').modal('hide'); ");
    }

    public boolean setValores() {
        boolean flag = false;
        if (vb.validarCampoVacio(nomPartido, "warn", "lblMantPart", "lblNomReqPart")
                && vb.validarSoloLetras(nomPartido, "warn", "lblMantPart", "lblNomPartLetras")
                && vb.validarLongitudCampo(nomPartido, 4, 30, "warn", "lblMantPart", "lblLongNomPart")
                && vb.validarCampoVacio(logoPartido, "warn", "lblMantPart", "lblLogoReqPart")
                && vb.validarCampoVacio(banderaPartido, "warn", "lblMantPart", "lblBandReqPart")) {
            flag = true;
            selectedPartido.setNompartido(nomPartido);
            selectedPartido.setLogopartido(logoPartido);
            selectedPartido.setBanderapartido(banderaPartido);
            selectedPartido.setEstadodel("A");
        }

        return flag;
    }

    public void onSelect(Tblpartido part) {
        selectedPartido = part;
        nomPartido = selectedPartido.getNompartido();
        banderaPartido = selectedPartido.getBanderapartido();
        logoPartido = selectedPartido.getLogopartido();
    }

    public void deSelect() {
        limpiar();
    }
}
