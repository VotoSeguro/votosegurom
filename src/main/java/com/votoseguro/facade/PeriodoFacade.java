/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.votoseguro.facade;

import com.votoseguro.entity.Tblcandidato;
import com.votoseguro.entity.Tblperiodo;
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
public class PeriodoFacade extends AbstractFacade<Tblperiodo>{
     @PersistenceContext(unitName = "votoseguroPU")
    private EntityManager em;
    
    public PeriodoFacade(){
    super(Tblperiodo.class);
    }
    
    @Override
    protected EntityManager getEntityManager(){
      return em;
    }
    
    public List<Tblperiodo> obtenerPeriodos(){
        String sql = "select * from Tblperiodo where estadodel = 'A'";
     Query q = getEntityManager().createNativeQuery(sql, Tblperiodo.class);
     List<Tblperiodo> listaEntity;
        try {
            listaEntity = q.getResultList();
            if (listaEntity.isEmpty()) {
                listaEntity = new ArrayList<Tblperiodo>();
            }
        } catch (Exception e) {
            listaEntity = new ArrayList<Tblperiodo>();
        }
        return listaEntity;
    }
    
    
}
