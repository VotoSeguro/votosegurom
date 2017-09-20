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
@Table(name = "TBLPERIODO")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Tblperiodo.findAll", query = "SELECT t FROM Tblperiodo t")
    , @NamedQuery(name = "Tblperiodo.findByIdperiodo", query = "SELECT t FROM Tblperiodo t WHERE t.idperiodo = :idperiodo")
    , @NamedQuery(name = "Tblperiodo.findByAnio", query = "SELECT t FROM Tblperiodo t WHERE t.anio = :anio")
    , @NamedQuery(name = "Tblperiodo.findByNomper", query = "SELECT t FROM Tblperiodo t WHERE t.nomper = :nomper")
    , @NamedQuery(name = "Tblperiodo.findByEstadoper", query = "SELECT t FROM Tblperiodo t WHERE t.estadoper = :estadoper")
    , @NamedQuery(name = "Tblperiodo.findByFechainicio", query = "SELECT t FROM Tblperiodo t WHERE t.fechainicio = :fechainicio")
    , @NamedQuery(name = "Tblperiodo.findByFechafin", query = "SELECT t FROM Tblperiodo t WHERE t.fechafin = :fechafin")
    , @NamedQuery(name = "Tblperiodo.findByEstadodel", query = "SELECT t FROM Tblperiodo t WHERE t.estadodel = :estadodel")})
public class Tblperiodo implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "IDPERIODO")
    @GeneratedValue (strategy = GenerationType.SEQUENCE, generator = "PERIODO_SQE")
    @SequenceGenerator(name = "PERIODO_SQE", sequenceName = "SQE_PERIODO", allocationSize = 1)
    private BigDecimal idperiodo;
    @Basic(optional = false)
    @NotNull
    @Column(name = "ANIO")
    private BigInteger anio;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "NOMPER")
    private String nomper;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "ESTADOPER")
    private String estadoper;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 15)
    @Column(name = "FECHAINICIO")
    private String fechainicio;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 15)
    @Column(name = "FECHAFIN")
    private String fechafin;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 10)
    @Column(name = "ESTADODEL")
    private String estadodel;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idperiodo")
    private List<Tblvoto> tblvotoList;

    public Tblperiodo() {
    }

    public Tblperiodo(BigDecimal idperiodo) {
        this.idperiodo = idperiodo;
    }

    public Tblperiodo(BigDecimal idperiodo, BigInteger anio, String nomper, String estadoper, String fechainicio, String fechafin, String estadodel) {
        this.idperiodo = idperiodo;
        this.anio = anio;
        this.nomper = nomper;
        this.estadoper = estadoper;
        this.fechainicio = fechainicio;
        this.fechafin = fechafin;
        this.estadodel = estadodel;
    }

    public BigDecimal getIdperiodo() {
        return idperiodo;
    }

    public void setIdperiodo(BigDecimal idperiodo) {
        this.idperiodo = idperiodo;
    }

    public BigInteger getAnio() {
        return anio;
    }

    public void setAnio(BigInteger anio) {
        this.anio = anio;
    }

    public String getNomper() {
        return nomper;
    }

    public void setNomper(String nomper) {
        this.nomper = nomper;
    }

    public String getEstadoper() {
        return estadoper;
    }

    public void setEstadoper(String estadoper) {
        this.estadoper = estadoper;
    }

    public String getFechainicio() {
        return fechainicio;
    }

    public void setFechainicio(String fechainicio) {
        this.fechainicio = fechainicio;
    }

    public String getFechafin() {
        return fechafin;
    }

    public void setFechafin(String fechafin) {
        this.fechafin = fechafin;
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

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idperiodo != null ? idperiodo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Tblperiodo)) {
            return false;
        }
        Tblperiodo other = (Tblperiodo) object;
        if ((this.idperiodo == null && other.idperiodo != null) || (this.idperiodo != null && !this.idperiodo.equals(other.idperiodo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.votoseguro.entity.Tblperiodo[ idperiodo=" + idperiodo + " ]";
    }
    
}
