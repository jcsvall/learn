/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jcsvall.app.entities;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author scjuan
 */
@Entity
@Table(name = "frases")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Frases.findAll", query = "SELECT f FROM Frases f")})
public class Frases implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @Column(name = "frase")
    private String frase;
    @Column(name = "pronunciacion")
    private String pronunciacion;
    @Column(name = "estado")
    private String estado;
    @Basic(optional = false)
    @Column(name = "fecha_ingreso")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaIngreso;
    @Basic(optional = false)
    @Column(name = "fecha_update")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaUpdate;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idFrases", fetch = FetchType.LAZY)
    private List<Traducciones> traduccionesList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idFrases", fetch = FetchType.LAZY)
    private List<Usos> usosList;
    @JoinColumn(name = "id_usuarios", referencedColumnName = "id")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Usuarios idUsuarios;
    @JoinColumn(name = "id_categorias", referencedColumnName = "id")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Categorias idCategorias;

    public Frases() {
    }

    public Frases(Integer id) {
        this.id = id;
    }

    public Frases(Integer id, String frase, Date fechaIngreso, Date fechaUpdate) {
        this.id = id;
        this.frase = frase;
        this.fechaIngreso = fechaIngreso;
        this.fechaUpdate = fechaUpdate;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFrase() {
        return frase;
    }

    public void setFrase(String frase) {
        this.frase = frase;
    }

    public String getPronunciacion() {
        return pronunciacion;
    }

    public void setPronunciacion(String pronunciacion) {
        this.pronunciacion = pronunciacion;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public Date getFechaIngreso() {
        return fechaIngreso;
    }

    public void setFechaIngreso(Date fechaIngreso) {
        this.fechaIngreso = fechaIngreso;
    }

    public Date getFechaUpdate() {
        return fechaUpdate;
    }

    public void setFechaUpdate(Date fechaUpdate) {
        this.fechaUpdate = fechaUpdate;
    }

    @XmlTransient
    public List<Traducciones> getTraduccionesList() {
        return traduccionesList;
    }

    public void setTraduccionesList(List<Traducciones> traduccionesList) {
        this.traduccionesList = traduccionesList;
    }

    @XmlTransient
    public List<Usos> getUsosList() {
        return usosList;
    }

    public void setUsosList(List<Usos> usosList) {
        this.usosList = usosList;
    }

    public Usuarios getIdUsuarios() {
        return idUsuarios;
    }

    public void setIdUsuarios(Usuarios idUsuarios) {
        this.idUsuarios = idUsuarios;
    }

    public Categorias getIdCategorias() {
        return idCategorias;
    }

    public void setIdCategorias(Categorias idCategorias) {
        this.idCategorias = idCategorias;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Frases)) {
            return false;
        }
        Frases other = (Frases) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.jcsvall.app.entities.Frases[ id=" + id + " ]";
    }
    
}
