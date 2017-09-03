/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.votoseguro.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
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
@Table(name = "TBLDEPARTAMENTO")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Tbldepartamento.findAll", query = "SELECT t FROM Tbldepartamento t")
    , @NamedQuery(name = "Tbldepartamento.findByIddepto", query = "SELECT t FROM Tbldepartamento t WHERE t.iddepto = :iddepto")
    , @NamedQuery(name = "Tbldepartamento.findByNomdepto", query = "SELECT t FROM Tbldepartamento t WHERE t.nomdepto = :nomdepto")
    , @NamedQuery(name = "Tbldepartamento.findByMaxcand", query = "SELECT t FROM Tbldepartamento t WHERE t.maxcand = :maxcand")})
public class Tbldepartamento implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "IDDEPTO")
    @GeneratedValue (strategy = GenerationType.SEQUENCE, generator = "DEPARTAMENTO_SQE")
    @SequenceGenerator(name = "DEPARTAMENTO_SQE", sequenceName = "SQE_DEPARTAMENTO", allocationSize = 1)
    private BigDecimal iddepto;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "NOMDEPTO")
    private String nomdepto;
    @Basic(optional = false)
    @NotNull
    @Column(name = "MAXCAND")
    private BigInteger maxcand;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "iddepto")
    private List<Tblcandidato> tblcandidatoList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "iddepto")
    private List<Tblmunicipio> tblmunicipioList;

    public Tbldepartamento() {
    }

    public Tbldepartamento(BigDecimal iddepto) {
        this.iddepto = iddepto;
    }

    public Tbldepartamento(BigDecimal iddepto, String nomdepto, BigInteger maxcand) {
        this.iddepto = iddepto;
        this.nomdepto = nomdepto;
        this.maxcand = maxcand;
    }

    public BigDecimal getIddepto() {
        return iddepto;
    }

    public void setIddepto(BigDecimal iddepto) {
        this.iddepto = iddepto;
    }

    public String getNomdepto() {
        return nomdepto;
    }

    public void setNomdepto(String nomdepto) {
        this.nomdepto = nomdepto;
    }

    public BigInteger getMaxcand() {
        return maxcand;
    }

    public void setMaxcand(BigInteger maxcand) {
        this.maxcand = maxcand;
    }

    @XmlTransient
    public List<Tblcandidato> getTblcandidatoList() {
        return tblcandidatoList;
    }

    public void setTblcandidatoList(List<Tblcandidato> tblcandidatoList) {
        this.tblcandidatoList = tblcandidatoList;
    }

    @XmlTransient
    public List<Tblmunicipio> getTblmunicipioList() {
        return tblmunicipioList;
    }

    public void setTblmunicipioList(List<Tblmunicipio> tblmunicipioList) {
        this.tblmunicipioList = tblmunicipioList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (iddepto != null ? iddepto.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Tbldepartamento)) {
            return false;
        }
        Tbldepartamento other = (Tbldepartamento) object;
        if ((this.iddepto == null && other.iddepto != null) || (this.iddepto != null && !this.iddepto.equals(other.iddepto))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Tbldepartamento{" + "iddepto=" + iddepto + ", nomdepto=" + nomdepto + ", maxcand=" + maxcand + ", tblcandidatoList=" + tblcandidatoList + ", tblmunicipioList=" + tblmunicipioList + '}';
    }

    
    
}
