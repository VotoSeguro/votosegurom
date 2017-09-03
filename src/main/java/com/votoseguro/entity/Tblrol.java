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
@Table(name = "TBLROL")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Tblrol.findAll", query = "SELECT t FROM Tblrol t")
    , @NamedQuery(name = "Tblrol.findByIdrol", query = "SELECT t FROM Tblrol t WHERE t.idrol = :idrol")
    , @NamedQuery(name = "Tblrol.findByNomrol", query = "SELECT t FROM Tblrol t WHERE t.nomrol = :nomrol")
    , @NamedQuery(name = "Tblrol.findByEstadodel", query = "SELECT t FROM Tblrol t WHERE t.estadodel = :estadodel")})
public class Tblrol implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "IDROL")
    private BigDecimal idrol;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "NOMROL")
    private String nomrol;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 10)
    @Column(name = "ESTADODEL")
    private String estadodel;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idrol")
    private List<Tblusuario> tblusuarioList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idrol")
    private List<Tblrolxpermiso> tblrolxpermisoList;

    public Tblrol() {
    }

    public Tblrol(BigDecimal idrol) {
        this.idrol = idrol;
    }

    public Tblrol(BigDecimal idrol, String nomrol, String estadodel) {
        this.idrol = idrol;
        this.nomrol = nomrol;
        this.estadodel = estadodel;
    }

    public BigDecimal getIdrol() {
        return idrol;
    }

    public void setIdrol(BigDecimal idrol) {
        this.idrol = idrol;
    }

    public String getNomrol() {
        return nomrol;
    }

    public void setNomrol(String nomrol) {
        this.nomrol = nomrol;
    }

    public String getEstadodel() {
        return estadodel;
    }

    public void setEstadodel(String estadodel) {
        this.estadodel = estadodel;
    }

    @XmlTransient
    public List<Tblusuario> getTblusuarioList() {
        return tblusuarioList;
    }

    public void setTblusuarioList(List<Tblusuario> tblusuarioList) {
        this.tblusuarioList = tblusuarioList;
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
        hash += (idrol != null ? idrol.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Tblrol)) {
            return false;
        }
        Tblrol other = (Tblrol) object;
        if ((this.idrol == null && other.idrol != null) || (this.idrol != null && !this.idrol.equals(other.idrol))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.votoseguro.entity.Tblrol[ idrol=" + idrol + " ]";
    }
    
}
