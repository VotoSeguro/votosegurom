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
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
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
@Table(name = "TBLPERMISO")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Tblpermiso.findAll", query = "SELECT t FROM Tblpermiso t")
    , @NamedQuery(name = "Tblpermiso.findByIdpermiso", query = "SELECT t FROM Tblpermiso t WHERE t.idpermiso = :idpermiso")
    , @NamedQuery(name = "Tblpermiso.findByUrlpermiso", query = "SELECT t FROM Tblpermiso t WHERE t.urlpermiso = :urlpermiso")
    , @NamedQuery(name = "Tblpermiso.findByNombrepermiso", query = "SELECT t FROM Tblpermiso t WHERE t.nombrepermiso = :nombrepermiso")
    , @NamedQuery(name = "Tblpermiso.findByEstadodel", query = "SELECT t FROM Tblpermiso t WHERE t.estadodel = :estadodel")})
public class Tblpermiso implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "IDPERMISO")
    private BigDecimal idpermiso;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "URLPERMISO")
    private String urlpermiso;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "NOMBREPERMISO")
    private String nombrepermiso;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 10)
    @Column(name = "ESTADODEL")
    private String estadodel;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idpermiso")
    private List<Tblrolxpermiso> tblrolxpermisoList;

    public Tblpermiso() {
    }

    public Tblpermiso(BigDecimal idpermiso) {
        this.idpermiso = idpermiso;
    }

    public Tblpermiso(BigDecimal idpermiso, String urlpermiso, String nombrepermiso, String estadodel) {
        this.idpermiso = idpermiso;
        this.urlpermiso = urlpermiso;
        this.nombrepermiso = nombrepermiso;
        this.estadodel = estadodel;
    }

    public BigDecimal getIdpermiso() {
        return idpermiso;
    }

    public void setIdpermiso(BigDecimal idpermiso) {
        this.idpermiso = idpermiso;
    }

    public String getUrlpermiso() {
        return urlpermiso;
    }

    public void setUrlpermiso(String urlpermiso) {
        this.urlpermiso = urlpermiso;
    }

    public String getNombrepermiso() {
        return nombrepermiso;
    }

    public void setNombrepermiso(String nombrepermiso) {
        this.nombrepermiso = nombrepermiso;
    }

    public String getEstadodel() {
        return estadodel;
    }

    public void setEstadodel(String estadodel) {
        this.estadodel = estadodel;
    }

    @XmlTransient
    public List<Tblrolxpermiso> getTblrolxpermisoList() {
        return tblrolxpermisoList;
    }

    public void setTblrolxpermisoList(List<Tblrolxpermiso> tblrolxpermisoList) {
        this.tblrolxpermisoList = tblrolxpermisoList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idpermiso != null ? idpermiso.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Tblpermiso)) {
            return false;
        }
        Tblpermiso other = (Tblpermiso) object;
        if ((this.idpermiso == null && other.idpermiso != null) || (this.idpermiso != null && !this.idpermiso.equals(other.idpermiso))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.votoseguro.entity.Tblpermiso[ idpermiso=" + idpermiso + " ]";
    }
    
}
