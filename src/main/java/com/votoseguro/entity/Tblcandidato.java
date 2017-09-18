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
@Table(name = "TBLCANDIDATO")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Tblcandidato.findAll", query = "SELECT t FROM Tblcandidato t")
    , @NamedQuery(name = "Tblcandidato.findByIdcandidato", query = "SELECT t FROM Tblcandidato t WHERE t.idcandidato = :idcandidato")
    , @NamedQuery(name = "Tblcandidato.findByNomcand", query = "SELECT t FROM Tblcandidato t WHERE t.nomcand = :nomcand")
    , @NamedQuery(name = "Tblcandidato.findByApecand", query = "SELECT t FROM Tblcandidato t WHERE t.apecand = :apecand")
    , @NamedQuery(name = "Tblcandidato.findByFotourl", query = "SELECT t FROM Tblcandidato t WHERE t.fotourl = :fotourl")
    , @NamedQuery(name = "Tblcandidato.findByFnaccand", query = "SELECT t FROM Tblcandidato t WHERE t.fnaccand = :fnaccand")
    , @NamedQuery(name = "Tblcandidato.findByEstadodel", query = "SELECT t FROM Tblcandidato t WHERE t.estadodel = :estadodel")})
public class Tblcandidato implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "IDCANDIDATO")
    @GeneratedValue (strategy = GenerationType.SEQUENCE, generator = "CANDIDATO_SQE")
    @SequenceGenerator(name = "CANDIDATO_SQE", sequenceName = "SQE_CANDIDATO", allocationSize = 1)
    private BigDecimal idcandidato;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "NOMCAND")
    private String nomcand;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "APECAND")
    private String apecand;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "FOTOURL")
    private String fotourl;
    @Size(max = 15)
    @Column(name = "FNACCAND")
    private String fnaccand;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 10)
    @Column(name = "ESTADODEL")
    private String estadodel;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idcandidato")
    private List<Tblvoto> tblvotoList;
    @JoinColumn(name = "IDDEPTO", referencedColumnName = "IDDEPTO")
    @ManyToOne(optional = false)
    private Tbldepartamento iddepto;
    @JoinColumn(name = "IDPARTIDO", referencedColumnName = "IDPARTIDO")
    @ManyToOne(optional = false)
    private Tblpartido idpartido;

    public Tblcandidato() {
    }

    public Tblcandidato(BigDecimal idcandidato) {
        this.idcandidato = idcandidato;
    }

    public Tblcandidato(BigDecimal idcandidato, String nomcand, String apecand, String fotourl, String estadodel) {
        this.idcandidato = idcandidato;
        this.nomcand = nomcand;
        this.apecand = apecand;
        this.fotourl = fotourl;
        this.estadodel = estadodel;
    }

    public BigDecimal getIdcandidato() {
        return idcandidato;
    }

    public void setIdcandidato(BigDecimal idcandidato) {
        this.idcandidato = idcandidato;
    }

    public String getNomcand() {
        return nomcand;
    }

    public void setNomcand(String nomcand) {
        this.nomcand = nomcand;
    }

    public String getApecand() {
        return apecand;
    }

    public void setApecand(String apecand) {
        this.apecand = apecand;
    }

    public String getFotourl() {
        return fotourl;
    }

    public void setFotourl(String fotourl) {
        this.fotourl = fotourl;
    }

    public String getFnaccand() {
        return fnaccand;
    }

    public void setFnaccand(String fnaccand) {
        this.fnaccand = fnaccand;
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

    public Tbldepartamento getIddepto() {
        return iddepto;
    }

    public void setIddepto(Tbldepartamento iddepto) {
        this.iddepto = iddepto;
    }

    public Tblpartido getIdpartido() {
        return idpartido;
    }

    public void setIdpartido(Tblpartido idpartido) {
        this.idpartido = idpartido;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idcandidato != null ? idcandidato.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Tblcandidato)) {
            return false;
        }
        Tblcandidato other = (Tblcandidato) object;
        if ((this.idcandidato == null && other.idcandidato != null) || (this.idcandidato != null && !this.idcandidato.equals(other.idcandidato))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.votoseguro.entity.Tblcandidato[ idcandidato=" + idcandidato + " ]";
    }
    
}
