/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.votoseguro.facade;

import com.votoseguro.entity.Tblpartido;
import com.votoseguro.entity.Tblrol;
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
public class PartidoFacade extends AbstractFacade<Tblpartido>{
    @PersistenceContext(unitName = "votoseguroPU")
    private EntityManager em;
    
    public PartidoFacade(){
    super(Tblpartido.class);
    }
    
    @Override
    protected EntityManager getEntityManager(){
      return em;
    } 
    
    public List<Tblpartido> obtenerPartidos(){
     Query q = getEntityManager().createNativeQuery("select * from Tblpartido where estadodel = 'A'", Tblpartido.class);
     List<Tblpartido> listaEntity;
        try {
            listaEntity = q.getResultList();
            if (listaEntity.isEmpty()) {
                listaEntity = new ArrayList<Tblpartido>();
            }
        } catch (Exception e) {
            listaEntity = new ArrayList<Tblpartido>();
        }
        return listaEntity;
    }
}
