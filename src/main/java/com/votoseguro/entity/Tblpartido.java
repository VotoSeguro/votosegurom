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
@Table(name = "TBLPARTIDO")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Tblpartido.findAll", query = "SELECT t FROM Tblpartido t")
    , @NamedQuery(name = "Tblpartido.findByIdpartido", query = "SELECT t FROM Tblpartido t WHERE t.idpartido = :idpartido")
    , @NamedQuery(name = "Tblpartido.findByNompartido", query = "SELECT t FROM Tblpartido t WHERE t.nompartido = :nompartido")
    , @NamedQuery(name = "Tblpartido.findByLogopartido", query = "SELECT t FROM Tblpartido t WHERE t.logopartido = :logopartido")
    , @NamedQuery(name = "Tblpartido.findByBanderapartido", query = "SELECT t FROM Tblpartido t WHERE t.banderapartido = :banderapartido")
    , @NamedQuery(name = "Tblpartido.findByEstadodel", query = "SELECT t FROM Tblpartido t WHERE t.estadodel = :estadodel")})
public class Tblpartido implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "IDPARTIDO")
    @GeneratedValue (strategy = GenerationType.SEQUENCE, generator = "PARTIDO_SQE")
    @SequenceGenerator(name = "PARTIDO_SQE", sequenceName = "SQE_PARTIDO", allocationSize = 1)
    private BigDecimal idpartido;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "NOMPARTIDO")
    private String nompartido;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "LOGOPARTIDO")
    private String logopartido;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "BANDERAPARTIDO")
    private String banderapartido;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 10)
    @Column(name = "ESTADODEL")
    private String estadodel;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idpartido")
    private List<Tblcandidato> tblcandidatoList;

    public Tblpartido() {
    }

    public Tblpartido(BigDecimal idpartido) {
        this.idpartido = idpartido;
    }

    public Tblpartido(BigDecimal idpartido, String nompartido, String logopartido, String banderapartido, String estadodel) {
        this.idpartido = idpartido;
        this.nompartido = nompartido;
        this.logopartido = logopartido;
        this.banderapartido = banderapartido;
        this.estadodel = estadodel;
    }

    public BigDecimal getIdpartido() {
        return idpartido;
    }

    public void setIdpartido(BigDecimal idpartido) {
        this.idpartido = idpartido;
    }

    public String getNompartido() {
        return nompartido;
    }

    public void setNompartido(String nompartido) {
        this.nompartido = nompartido;
    }

    public String getLogopartido() {
        return logopartido;
    }

    public void setLogopartido(String logopartido) {
        this.logopartido = logopartido;
    }

    public String getBanderapartido() {
        return banderapartido;
    }

    public void setBanderapartido(String banderapartido) {
        this.banderapartido = banderapartido;
    }

    public String getEstadodel() {
        return estadodel;
    }

    public void setEstadodel(String estadodel) {
        this.estadodel = estadodel;
    }

    @XmlTransient
    public List<Tblcandidato> getTblcandidatoList() {
        return tblcandidatoList;
    }

    public void setTblcandidatoList(List<Tblcandidato> tblcandidatoList) {
        this.tblcandidatoList = tblcandidatoList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idpartido != null ? idpartido.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Tblpartido)) {
            return false;
        }
        Tblpartido other = (Tblpartido) object;
        if ((this.idpartido == null && other.idpartido != null) || (this.idpartido != null && !this.idpartido.equals(other.idpartido))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.votoseguro.entity.Tblpartido[ idpartido=" + idpartido + " ]";
    }
    
}
