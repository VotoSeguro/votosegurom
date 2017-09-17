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
@Table(name = "TBLUSUARIO")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Tblusuario.findAll", query = "SELECT t FROM Tblusuario t")
    , @NamedQuery(name = "Tblusuario.findByIduser", query = "SELECT t FROM Tblusuario t WHERE t.iduser = :iduser")
    , @NamedQuery(name = "Tblusuario.findByUsername", query = "SELECT t FROM Tblusuario t WHERE t.username = :username")
    , @NamedQuery(name = "Tblusuario.findByPassuser", query = "SELECT t FROM Tblusuario t WHERE t.passuser = :passuser")
    , @NamedQuery(name = "Tblusuario.findByNombreuser", query = "SELECT t FROM Tblusuario t WHERE t.nombreuser = :nombreuser")
    , @NamedQuery(name = "Tblusuario.findByApellidouser", query = "SELECT t FROM Tblusuario t WHERE t.apellidouser = :apellidouser")
    , @NamedQuery(name = "Tblusuario.findByEstadodel", query = "SELECT t FROM Tblusuario t WHERE t.estadodel = :estadodel")})
public class Tblusuario implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "IDUSER")
    @GeneratedValue (strategy = GenerationType.SEQUENCE, generator = "USUARIO_SQE")
    @SequenceGenerator(name = "USUARIO_SQE", sequenceName = "SQE_USUARIO", allocationSize = 1)
    private BigDecimal iduser;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "USERNAME")
    private String username;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "PASSUSER")
    private String passuser;
    @Size(max = 50)
    @Column(name = "NOMBREUSER")
    private String nombreuser;
    @Size(max = 50)
    @Column(name = "APELLIDOUSER")
    private String apellidouser;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 10)
    @Column(name = "ESTADODEL")
    private String estadodel;
    @JoinColumn(name = "IDROL", referencedColumnName = "IDROL")
    @ManyToOne(optional = false)
    private Tblrol idrol;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "iduser")
    private List<Tblvotante> tblvotanteList;

    public Tblusuario() {
    }

    public Tblusuario(BigDecimal iduser) {
        this.iduser = iduser;
    }

    public Tblusuario(BigDecimal iduser, String username, String passuser, String estadodel) {
        this.iduser = iduser;
        this.username = username;
        this.passuser = passuser;
        this.estadodel = estadodel;
    }

    public BigDecimal getIduser() {
        return iduser;
    }

    public void setIduser(BigDecimal iduser) {
        this.iduser = iduser;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassuser() {
        return passuser;
    }

    public void setPassuser(String passuser) {
        this.passuser = passuser;
    }

    public String getNombreuser() {
        return nombreuser;
    }

    public void setNombreuser(String nombreuser) {
        this.nombreuser = nombreuser;
    }

    public String getApellidouser() {
        return apellidouser;
    }

    public void setApellidouser(String apellidouser) {
        this.apellidouser = apellidouser;
    }

    public String getEstadodel() {
        return estadodel;
    }

    public void setEstadodel(String estadodel) {
        this.estadodel = estadodel;
    }

    public Tblrol getIdrol() {
        return idrol;
    }

    public void setIdrol(Tblrol idrol) {
        this.idrol = idrol;
    }

    @XmlTransient
    public List<Tblvotante> getTblvotanteList() {
        return tblvotanteList;
    }

    public void setTblvotanteList(List<Tblvotante> tblvotanteList) {
        this.tblvotanteList = tblvotanteList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (iduser != null ? iduser.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Tblusuario)) {
            return false;
        }
        Tblusuario other = (Tblusuario) object;
        if ((this.iduser == null && other.iduser != null) || (this.iduser != null && !this.iduser.equals(other.iduser))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.votoseguro.entity.Tblusuario[ iduser=" + iduser + " ]";
    }
    
}
