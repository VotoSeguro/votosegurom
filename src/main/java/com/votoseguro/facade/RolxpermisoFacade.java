/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.votoseguro.facade;
import com.votoseguro.entity.Tblrolxpermiso;
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
public class RolxpermisoFacade extends AbstractFacade<Tblrolxpermiso>{
    @PersistenceContext(unitName = "votoseguroPU")
     private EntityManager em;   
    
    public RolxpermisoFacade(){
    super(Tblrolxpermiso.class);
    }
    
    @Override
    protected EntityManager getEntityManager(){
    return em;
    }
    
    public List<Tblrolxpermiso> obtenerPermisosxrol(String idRol){
     Query q = getEntityManager().createNativeQuery("select * from Tblrolxpermiso where idrol = " + idRol, Tblrolxpermiso.class);
     List<Tblrolxpermiso> listaEntity;
        try {
            listaEntity = q.getResultList();
            if (listaEntity.isEmpty()) {
                listaEntity = new ArrayList<Tblrolxpermiso>();
            }
        } catch (Exception e) {
            listaEntity = new ArrayList<Tblrolxpermiso>();
        }
        return listaEntity;
    }
    
     public int revisarRepetido(String idrol, String idperm){
         String sql = "select * from Tblrolxpermiso where idrol = " + idrol + " and idpermiso = " + idperm;
     Query q = getEntityManager().createNativeQuery(sql, Tblrolxpermiso.class);
     List<Tblrolxpermiso> listaEntity;
        try {
            listaEntity = q.getResultList();
            if (listaEntity.isEmpty()) {
                listaEntity = new ArrayList<Tblrolxpermiso>();
            }
        } catch (Exception e) {
            listaEntity = new ArrayList<Tblrolxpermiso>();
        }
        return listaEntity.size();
    }
    
     public int revisarRepetido(String idrol, String idperm , String idrolxperm){
         String sql = "select * from Tblrolxpermiso where idrol = " + idrol 
                 + " and idpermiso = " + idperm +" and idrolxpermiso != " + idrolxperm;
     Query q = getEntityManager().createNativeQuery(sql, Tblrolxpermiso.class);
     List<Tblrolxpermiso> listaEntity;
        try {
            listaEntity = q.getResultList();
            if (listaEntity.isEmpty()) {
                listaEntity = new ArrayList<Tblrolxpermiso>();
            }
        } catch (Exception e) {
            listaEntity = new ArrayList<Tblrolxpermiso>();
        }
        return listaEntity.size();
    }
}
