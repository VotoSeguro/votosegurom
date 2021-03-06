/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.votoseguro.facade;

import com.votoseguro.entity.Tblcandidato;

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
public class CandidatoFacade extends AbstractFacade<Tblcandidato>{
    @PersistenceContext(unitName = "votoseguroPU")
    private EntityManager em;
    
    public CandidatoFacade(){
    super(Tblcandidato.class);
    }
    
    @Override
    protected EntityManager getEntityManager(){
      return em;
    }
    
    public List<Tblcandidato> obtenerCandidatos(String idpart){
        String sql = "select * from Tblcandidato where estadodel = 'A' and idpartido = " + idpart;
     Query q = getEntityManager().createNativeQuery(sql, Tblcandidato.class);
     List<Tblcandidato> listaEntity;
        try {
            listaEntity = q.getResultList();
            if (listaEntity.isEmpty()) {
                listaEntity = new ArrayList<Tblcandidato>();
            }
        } catch (Exception e) {
            listaEntity = new ArrayList<Tblcandidato>();
        }
        return listaEntity;
    }
    
    public List<Tblcandidato> obtenerCandidatos(String idpart, String iddepto){
        String sql = "select * from Tblcandidato where estadodel = 'A' and idpartido = ? and iddepto = ?";
        
     Query q = getEntityManager().createNativeQuery(sql, Tblcandidato.class);
     q.setParameter(1, idpart);
     q.setParameter(2, iddepto);
     List<Tblcandidato> listaEntity;
        try {
            listaEntity = q.getResultList();
            if (listaEntity.isEmpty()) {
                listaEntity = new ArrayList<Tblcandidato>();
            }
        } catch (Exception e) {
            listaEntity = new ArrayList<Tblcandidato>();
        }
        return listaEntity;
    }
}
