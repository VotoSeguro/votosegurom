/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.votoseguro.facade;

import com.votoseguro.entity.Tblusuario;
import com.votoseguro.entity.Tblvotante;
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
    
    
    
    public void enviarEmail(String receptor,String asunto, String mensaje){
    
        try {
            Connection cn = em.unwrap(java.sql.Connection.class);
            CallableStatement cs = cn.prepareCall("{call SYS.PR_ENVIAR_EMAIL('hayteguachoprueba@gmail.com',?,?,?)}");
            cs.setString(1, receptor);
            cs.setString(2, asunto);
            cs.setString(3, mensaje);
            cs.execute();
        } catch (Exception e) {
            System.out.println("com.votoseguro.facade.VotanteFacade.enviarEmail()");
            e.printStackTrace();
        }
    
    }
}
