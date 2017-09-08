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
@Table(name = "TBLMUNICIPIO")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Tblmunicipio.findAll", query = "SELECT t FROM Tblmunicipio t")
    , @NamedQuery(name = "Tblmunicipio.findByIdmuni", query = "SELECT t FROM Tblmunicipio t WHERE t.idmuni = :idmuni")
    , @NamedQuery(name = "Tblmunicipio.findByNommuni", query = "SELECT t FROM Tblmunicipio t WHERE t.nommuni = :nommuni")})
public class Tblmunicipio implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "IDMUNI")
    @GeneratedValue (strategy = GenerationType.SEQUENCE, generator = "MUNICIPIO_SQE")
    @SequenceGenerator(name = "MUNICIPIO_SQE", sequenceName = "SQE_MUNICIPIO", allocationSize = 1)
    private BigDecimal idmuni;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "NOMMUNI")
    private String nommuni;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idmuni")
    private List<Tblvotante> tblvotanteList;
    @JoinColumn(name = "IDDEPTO", referencedColumnName = "IDDEPTO")
    @ManyToOne(optional = false)
    private Tbldepartamento iddepto;

    public Tblmunicipio() {
    }

    public Tblmunicipio(BigDecimal idmuni) {
        this.idmuni = idmuni;
    }

    public Tblmunicipio(BigDecimal idmuni, String nommuni) {
        this.idmuni = idmuni;
        this.nommuni = nommuni;
    }

    public BigDecimal getIdmuni() {
        return idmuni;
    }

    public void setIdmuni(BigDecimal idmuni) {
        this.idmuni = idmuni;
    }

    public String getNommuni() {
        return nommuni;
    }

    public void setNommuni(String nommuni) {
        this.nommuni = nommuni;
    }

    @XmlTransient
    public List<Tblvotante> getTblvotanteList() {
        return tblvotanteList;
    }

    public void setTblvotanteList(List<Tblvotante> tblvotanteList) {
        this.tblvotanteList = tblvotanteList;
    }

    public Tbldepartamento getIddepto() {
        return iddepto;
    }

    public void setIddepto(Tbldepartamento iddepto) {
        this.iddepto = iddepto;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idmuni != null ? idmuni.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Tblmunicipio)) {
            return false;
        }
        Tblmunicipio other = (Tblmunicipio) object;
        if ((this.idmuni == null && other.idmuni != null) || (this.idmuni != null && !this.idmuni.equals(other.idmuni))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.votoseguro.entity.Tblmunicipio[ idmuni=" + idmuni + " ]";
    }
    
}
