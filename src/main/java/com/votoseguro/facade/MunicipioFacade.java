/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.votoseguro.facade;
import com.votoseguro.entity.Tbldepartamento;
import com.votoseguro.entity.Tblmunicipio;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
/**
 *
 * @author Luis Eduardo Valdez
 */
@Stateless
public class MunicipioFacade extends AbstractFacade<Tblmunicipio>{
    @PersistenceContext(unitName = "votoseguroPU")
    private EntityManager em;
    
    public MunicipioFacade(){
    super(Tblmunicipio.class);
    }
    
    @Override
    protected EntityManager getEntityManager(){
      return em;
    }
    
    public List<Tblmunicipio> obtenerMunicipios(){
     Query q = getEntityManager().createNativeQuery("select * from Tblmunicipio", Tblmunicipio.class);
     List<Tblmunicipio> listaEntity;
        try {
            listaEntity = q.getResultList();
            if (listaEntity.isEmpty()) {
                listaEntity = new ArrayList<Tblmunicipio>();
            }
        } catch (Exception e) {
            listaEntity = new ArrayList<Tblmunicipio>();
        }
        return listaEntity;
    }
    
    public List<Tblmunicipio> obtenerMunicipios(String iddepto){
        String sql = "select * from Tblmunicipio where iddepto = " + iddepto;
     Query q = getEntityManager().createNativeQuery(sql, Tblmunicipio.class);
     List<Tblmunicipio> listaEntity;
        try {
            listaEntity = q.getResultList();
            if (listaEntity.isEmpty()) {
                listaEntity = new ArrayList<Tblmunicipio>();
            }
        } catch (Exception e) {
            listaEntity = new ArrayList<Tblmunicipio>();
        }
        return listaEntity;
    }
    
}
