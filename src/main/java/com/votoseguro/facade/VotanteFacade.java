/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.votoseguro.facade;

import com.votoseguro.entity.Tblusuario;
import com.votoseguro.entity.Tblvotante;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author Luis Eduardo Valdez
 */
public class VotanteFacade extends AbstractFacade<Tblvotante>{
    @PersistenceContext(unitName = "votoseguroPU")
     private EntityManager em;   
    
    public VotanteFacade(){
    super(Tblvotante.class);
    }
    
    @Override
    protected EntityManager getEntityManager(){
    return em;
    }
    
    public List<Tblvotante> obtenerVotantes(String idmuni){
     Query q = getEntityManager().createNativeQuery("select * from Tblvotante where estadodel = 'A' and idmuni = " + idmuni, Tblvotante.class);
     List<Tblvotante> listaEntity;
        try {
            listaEntity = q.getResultList();
            if (listaEntity.isEmpty()) {
                listaEntity = new ArrayList<Tblvotante>();
            }
        } catch (Exception e) {
            listaEntity = new ArrayList<Tblvotante>();
        }
        return listaEntity;
    }
}
