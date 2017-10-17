/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.votoseguro.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Luis Eduardo Valdez
 */
@Entity
@Table(name = "TBLVOTO")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Tblvoto.findAll", query = "SELECT t FROM Tblvoto t")
    , @NamedQuery(name = "Tblvoto.findByIdvoto", query = "SELECT t FROM Tblvoto t WHERE t.idvoto = :idvoto")
    , @NamedQuery(name = "Tblvoto.findByValor", query = "SELECT t FROM Tblvoto t WHERE t.valor = :valor")})
public class Tblvoto implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "IDVOTO")
    @GeneratedValue (strategy = GenerationType.SEQUENCE, generator = "VOTO_SQE")
    @SequenceGenerator(name = "VOTO_SQE", sequenceName = "SQE_VOTO", allocationSize = 1)
    private BigDecimal idvoto;
    @Basic(optional = false)
    @NotNull
    @Column(name = "VALOR")
    private BigDecimal valor;
    @JoinColumn(name = "IDCANDIDATO", referencedColumnName = "IDCANDIDATO")
    @ManyToOne(optional = false)
    private Tblcandidato idcandidato;
    @JoinColumn(name = "IDPERIODO", referencedColumnName = "IDPERIODO")
    @ManyToOne(optional = false)
    private Tblperiodo idperiodo;
    @JoinColumn(name = "IDVOTANTE", referencedColumnName = "IDVOTANTE")
    @ManyToOne(optional = false)
    private Tblvotante idvotante;

    public Tblvoto() {
    }

    public Tblvoto(BigDecimal idvoto) {
        this.idvoto = idvoto;
    }

    public Tblvoto(BigDecimal idvoto, BigDecimal valor) {
        this.idvoto = idvoto;
        this.valor = valor;
    }

    public BigDecimal getIdvoto() {
        return idvoto;
    }

    public void setIdvoto(BigDecimal idvoto) {
        this.idvoto = idvoto;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }

    public Tblcandidato getIdcandidato() {
        return idcandidato;
    }

    public void setIdcandidato(Tblcandidato idcandidato) {
        this.idcandidato = idcandidato;
    }

    public Tblperiodo getIdperiodo() {
        return idperiodo;
    }

    public void setIdperiodo(Tblperiodo idperiodo) {
        this.idperiodo = idperiodo;
    }

    public Tblvotante getIdvotante() {
        return idvotante;
    }

    public void setIdvotante(Tblvotante idvotante) {
        this.idvotante = idvotante;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idvoto != null ? idvoto.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Tblvoto)) {
            return false;
        }
        Tblvoto other = (Tblvoto) object;
        if ((this.idvoto == null && other.idvoto != null) || (this.idvoto != null && !this.idvoto.equals(other.idvoto))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.votoseguro.entity.Tblvoto[ idvoto=" + idvoto + " ]";
    }
    
}
