/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.votoseguro.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Luis Eduardo Valdez
 */
@Entity
@Table(name = "TBLVOTANTE")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Tblvotante.findAll", query = "SELECT t FROM Tblvotante t")
    , @NamedQuery(name = "Tblvotante.findByIdvotante", query = "SELECT t FROM Tblvotante t WHERE t.idvotante = :idvotante")
    , @NamedQuery(name = "Tblvotante.findByNombrev", query = "SELECT t FROM Tblvotante t WHERE t.nombrev = :nombrev")
    , @NamedQuery(name = "Tblvotante.findByApellidov", query = "SELECT t FROM Tblvotante t WHERE t.apellidov = :apellidov")
    , @NamedQuery(name = "Tblvotante.findByDui", query = "SELECT t FROM Tblvotante t WHERE t.dui = :dui")
    , @NamedQuery(name = "Tblvotante.findByPregunta", query = "SELECT t FROM Tblvotante t WHERE t.pregunta = :pregunta")
    , @NamedQuery(name = "Tblvotante.findByRespuesta", query = "SELECT t FROM Tblvotante t WHERE t.respuesta = :respuesta")
    , @NamedQuery(name = "Tblvotante.findByPassvotante", query = "SELECT t FROM Tblvotante t WHERE t.passvotante = :passvotante")
    , @NamedQuery(name = "Tblvotante.findByGenero", query = "SELECT t FROM Tblvotante t WHERE t.genero = :genero")
    , @NamedQuery(name = "Tblvotante.findByFnac", query = "SELECT t FROM Tblvotante t WHERE t.fnac = :fnac")
    , @NamedQuery(name = "Tblvotante.findByEstadodel", query = "SELECT t FROM Tblvotante t WHERE t.estadodel = :estadodel")
    , @NamedQuery(name = "Tblvotante.Login", query= "SELECT t FROM Tblvotante t WHERE t.dui = :duiv and t.passvotante = :passv")})
public class Tblvotante implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "IDVOTANTE")
    @GeneratedValue (strategy = GenerationType.SEQUENCE, generator = "VOTANTE_SQE")
    @SequenceGenerator(name = "VOTANTE_SQE", sequenceName = "SQE_VOTANTE", allocationSize = 1)
    private BigDecimal idvotante;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "NOMBREV")
    private String nombrev;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "APELLIDOV")
    private String apellidov;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 10)
    @Column(name = "DUI")
    private String dui;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "PREGUNTA")
    private String pregunta;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 250)
    @Column(name = "RESPUESTA")
    private String respuesta;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "PASSVOTANTE")
    private String passvotante;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 5)
    @Column(name = "GENERO")
    private String genero;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 15)
    @Column(name = "FNAC")
    private String fnac;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 10)
    @Column(name = "ESTADODEL")
    private String estadodel;
    @Column(name = "NACIONALIDAD")
    private String nacionalidad;
    @Column(name = "LUGARNAC")
    private String lugarnac;
    @Column(name = "FECHAEXP")
    private String fechaexp;
    @Column(name = "LUGAREXP")
    private String lugarexp;
    @Column(name = "FECHAVENC")
    private String fechavenc;
    @Column(name = "DIRECCION")
    private String direccion;
    @Column(name = "TRAMITE")
    private String tramite;
    @Column(name = "NIT")
    private String nit;
    @Column(name = "CODZONA")
    private String codzona;
    @Column(name = "NOMMADRE")
    private String nommadre;
    @Column(name = "NOMPADRE")
    private String nompadre;
    @Column(name = "ESTADOCIVIL")
    private String estadocivil;
    @Column(name = "NOMCONYUGE")
    private String nomconyuge;
    @Column(name = "TIPOSANGRE")
    private String tiposangre;
    @Column(name = "PROFESION")
    private String profesion;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idvotante")
    private List<Tblvoto> tblvotoList;
    @JoinColumn(name = "IDMUNI", referencedColumnName = "IDMUNI")
    @ManyToOne(optional = false)
    private Tblmunicipio idmuni;
    @JoinColumn(name = "IDUSER", referencedColumnName = "IDUSER")
    @ManyToOne(optional = false)
    private Tblusuario iduser;

    public Tblvotante() {
    }

    public Tblvotante(BigDecimal idvotante) {
        this.idvotante = idvotante;
    }

    public Tblvotante(BigDecimal idvotante, String nombrev, String apellidov, String dui, String pregunta, String respuesta, String passvotante, String genero, String fnac, String estadodel) {
        this.idvotante = idvotante;
        this.nombrev = nombrev;
        this.apellidov = apellidov;
        this.dui = dui;
        this.pregunta = pregunta;
        this.respuesta = respuesta;
        this.passvotante = passvotante;
        this.genero = genero;
        this.fnac = fnac;
        this.estadodel = estadodel;
    }

    public BigDecimal getIdvotante() {
        return idvotante;
    }

    public void setIdvotante(BigDecimal idvotante) {
        this.idvotante = idvotante;
    }

    public String getNombrev() {
        return nombrev;
    }

    public void setNombrev(String nombrev) {
        this.nombrev = nombrev;
    }

    public String getApellidov() {
        return apellidov;
    }

    public void setApellidov(String apellidov) {
        this.apellidov = apellidov;
    }

    public String getDui() {
        return dui;
    }

    public void setDui(String dui) {
        this.dui = dui;
    }

    public String getPregunta() {
        return pregunta;
    }

    public String getNacionalidad() {
        return nacionalidad;
    }

    public void setNacionalidad(String nacionalidad) {
        this.nacionalidad = nacionalidad;
    }

    public String getLugarnac() {
        return lugarnac;
    }

    public void setLugarnac(String lugarnac) {
        this.lugarnac = lugarnac;
    }

    public String getFechaexp() {
        return fechaexp;
    }

    public void setFechaexp(String fechaexp) {
        this.fechaexp = fechaexp;
    }

    public String getLugarexp() {
        return lugarexp;
    }

    public void setLugarexp(String lugarexp) {
        this.lugarexp = lugarexp;
    }

    public String getFechavenc() {
        return fechavenc;
    }

    public void setFechavenc(String fechavenc) {
        this.fechavenc = fechavenc;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getTramite() {
        return tramite;
    }

    public void setTramite(String tramite) {
        this.tramite = tramite;
    }

    public String getNit() {
        return nit;
    }

    public void setNit(String nit) {
        this.nit = nit;
    }

    public String getCodzona() {
        return codzona;
    }

    public void setCodzona(String codzona) {
        this.codzona = codzona;
    }

    public String getNommadre() {
        return nommadre;
    }

    public void setNommadre(String nommadre) {
        this.nommadre = nommadre;
    }

    public String getNompadre() {
        return nompadre;
    }

    public void setNompadre(String nompadre) {
        this.nompadre = nompadre;
    }

    public String getEstadocivil() {
        return estadocivil;
    }

    public void setEstadocivil(String estadocivil) {
        this.estadocivil = estadocivil;
    }

    public String getNomconyuge() {
        return nomconyuge;
    }

    public void setNomconyuge(String nomconyuge) {
        this.nomconyuge = nomconyuge;
    }

    public String getTiposangre() {
        return tiposangre;
    }

    public void setTiposangre(String tiposangre) {
        this.tiposangre = tiposangre;
    }

    public String getProfesion() {
        return profesion;
    }

    public void setProfesion(String profesion) {
        this.profesion = profesion;
    }

    public void setPregunta(String pregunta) {
        this.pregunta = pregunta;
    }

    public String getRespuesta() {
        return respuesta;
    }

    public void setRespuesta(String respuesta) {
        this.respuesta = respuesta;
    }

    public String getPassvotante() {
        return passvotante;
    }

    public void setPassvotante(String passvotante) {
        this.passvotante = passvotante;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public String getFnac() {
        return fnac;
    }

    public void setFnac(String fnac) {
        this.fnac = fnac;
    }

    public String getEstadodel() {
        return estadodel;
    }

    public void setEstadodel(String estadodel) {
        this.estadodel = estadodel;
    }

    @XmlTransient
    public List<Tblvoto> getTblvotoList() {
        return tblvotoList;
    }

    public void setTblvotoList(List<Tblvoto> tblvotoList) {
        this.tblvotoList = tblvotoList;
    }

    public Tblmunicipio getIdmuni() {
        return idmuni;
    }

    public void setIdmuni(Tblmunicipio idmuni) {
        this.idmuni = idmuni;
    }

    public Tblusuario getIduser() {
        return iduser;
    }

    public void setIduser(Tblusuario iduser) {
        this.iduser = iduser;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idvotante != null ? idvotante.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Tblvotante)) {
            return false;
        }
        Tblvotante other = (Tblvotante) object;
        if ((this.idvotante == null && other.idvotante != null) || (this.idvotante != null && !this.idvotante.equals(other.idvotante))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.votoseguro.entity.Tblvotante[ idvotante=" + idvotante + " ]";
    }
    
}
