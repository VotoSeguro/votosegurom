/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.votoseguro.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Luis Eduardo Valdez
 */
@Entity
@Table(name = "TBLROLXPERMISO")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Tblrolxpermiso.findAll", query = "SELECT t FROM Tblrolxpermiso t")
    , @NamedQuery(name = "Tblrolxpermiso.findByIdrolxpermiso", query = "SELECT t FROM Tblrolxpermiso t WHERE t.idrolxpermiso = :idrolxpermiso")
    , @NamedQuery(name = "Tblrolxpermiso.findByNivelpermiso", query = "SELECT t FROM Tblrolxpermiso t WHERE t.nivelpermiso = :nivelpermiso")})
public class Tblrolxpermiso implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "IDROLXPERMISO")
    private BigDecimal idrolxpermiso;
    @Basic(optional = false)
    @NotNull
    @Column(name = "NIVELPERMISO")
    private BigInteger nivelpermiso;
    @JoinColumn(name = "IDPERMISO", referencedColumnName = "IDPERMISO")
    @ManyToOne(optional = false)
    private Tblpermiso idpermiso;
    @JoinColumn(name = "IDROL", referencedColumnName = "IDROL")
    @ManyToOne(optional = false)
    private Tblrol idrol;

    public Tblrolxpermiso() {
    }

    public Tblrolxpermiso(BigDecimal idrolxpermiso) {
        this.idrolxpermiso = idrolxpermiso;
    }

    public Tblrolxpermiso(BigDecimal idrolxpermiso, BigInteger nivelpermiso) {
        this.idrolxpermiso = idrolxpermiso;
        this.nivelpermiso = nivelpermiso;
    }

    public BigDecimal getIdrolxpermiso() {
        return idrolxpermiso;
    }

    public void setIdrolxpermiso(BigDecimal idrolxpermiso) {
        this.idrolxpermiso = idrolxpermiso;
    }

    public BigInteger getNivelpermiso() {
        return nivelpermiso;
    }

    public void setNivelpermiso(BigInteger nivelpermiso) {
        this.nivelpermiso = nivelpermiso;
    }

    public Tblpermiso getIdpermiso() {
        return idpermiso;
    }

    public void setIdpermiso(Tblpermiso idpermiso) {
        this.idpermiso = idpermiso;
    }

    public Tblrol getIdrol() {
        return idrol;
    }

    public void setIdrol(Tblrol idrol) {
        this.idrol = idrol;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idrolxpermiso != null ? idrolxpermiso.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Tblrolxpermiso)) {
            return false;
        }
        Tblrolxpermiso other = (Tblrolxpermiso) object;
        if ((this.idrolxpermiso == null && other.idrolxpermiso != null) || (this.idrolxpermiso != null && !this.idrolxpermiso.equals(other.idrolxpermiso))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.votoseguro.entity.Tblrolxpermiso[ idrolxpermiso=" + idrolxpermiso + " ]";
    }
    
}
