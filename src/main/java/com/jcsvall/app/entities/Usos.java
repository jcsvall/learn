/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jcsvall.app.entities;

import java.io.Serializable;
import javax.persistence.Basic;
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
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 *
 * @author scjuan
 */
@Entity
@Table(name = "usos")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Usos.findAll", query = "SELECT u FROM Usos u")})
public class Usos implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @Column(name = "frase_ejemplo")
    private String fraseEjemplo;
    @JsonIgnore
    @JoinColumn(name = "id_frases", referencedColumnName = "id")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Frases idFrases;

    public Usos() {
    }

    public Usos(Integer id) {
        this.id = id;
    }

    public Usos(Integer id, String fraseEjemplo) {
        this.id = id;
        this.fraseEjemplo = fraseEjemplo;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFraseEjemplo() {
        return fraseEjemplo;
    }

    public void setFraseEjemplo(String fraseEjemplo) {
        this.fraseEjemplo = fraseEjemplo;
    }

    public Frases getIdFrases() {
        return idFrases;
    }

    public void setIdFrases(Frases idFrases) {
        this.idFrases = idFrases;
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
        if (!(object instanceof Usos)) {
            return false;
        }
        Usos other = (Usos) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.jcsvall.app.entities.Usos[ id=" + id + " ]";
    }
    
}
