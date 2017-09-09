/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.votoseguro.facade;

import com.votoseguro.entity.Tblmunicipio;
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
public class RolFacade extends AbstractFacade<Tblrol>{
   @PersistenceContext(unitName = "votoseguroPU")
    private EntityManager em;
    
    public RolFacade (){
    super(Tblrol.class);
    }
    
    @Override
    protected EntityManager getEntityManager(){
      return em;
    } 
    
     public List<Tblrol> obtenerRoles(){
     Query q = getEntityManager().createNativeQuery("select * from Tblrol where estadodel = 'A'", Tblrol.class);
     List<Tblrol> listaEntity;
        try {
            listaEntity = q.getResultList();
            if (listaEntity.isEmpty()) {
                listaEntity = new ArrayList<Tblrol>();
            }
        } catch (Exception e) {
            listaEntity = new ArrayList<Tblrol>();
        }
        return listaEntity;
    }
     
     public String contarRoles(String id){
         try {
             String sql = "select count(*) from tblusuario where idrol = " + id;
     Query q = getEntityManager().createNativeQuery(sql);
     List lista = q.getResultList();
     String result = String.valueOf(lista.get(0));
      return result;  
         } catch (Exception e) {
             System.out.println("com.votoseguro.facade.RolFacade.contarRoles()");
             e.printStackTrace();
         }
        return "";
     }
}
