/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.votoseguro.facade;
import com.votoseguro.entity.Tblpermiso;

import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
/**
 *
 * @author dell
 */
@Stateless
public class PermisoFacade extends AbstractFacade<Tblpermiso>{
   @PersistenceContext(unitName = "votoseguroPU")
     private EntityManager em;   
    
    public PermisoFacade(){
    super(Tblpermiso.class);
    }
    
    @Override
    protected EntityManager getEntityManager(){
    return em;
    } 
    
     public List<Tblpermiso> obtenerPermisos(){
     Query q = getEntityManager().createNativeQuery("select * from Tblpermiso where estadodel = 'A'", Tblpermiso.class);
     List<Tblpermiso> listaEntity;
        try {
            listaEntity = q.getResultList();
            if (listaEntity.isEmpty()) {
                listaEntity = new ArrayList<Tblpermiso>();
            }
        } catch (Exception e) {
            listaEntity = new ArrayList<Tblpermiso>();
        }
        return listaEntity;
    }
}
