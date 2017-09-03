/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.votoseguro.facade;

import com.votoseguro.entity.Tbldepartamento;
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
public class DepartamentoFacade extends AbstractFacade<Tbldepartamento>{
    
    @PersistenceContext(unitName = "votoseguroPU")
    private EntityManager em;
    
    public DepartamentoFacade(){
    super(Tbldepartamento.class);
    }
    
    @Override
    protected EntityManager getEntityManager(){
      return em;
    }
    
    public List<Tbldepartamento> obtenerDeptos(){
     Query q = getEntityManager().createNativeQuery("select * from tbldepartamento", Tbldepartamento.class);
     List<Tbldepartamento> listaEntity;
        try {
            listaEntity = q.getResultList();
            if (listaEntity.isEmpty()) {
                listaEntity = new ArrayList<Tbldepartamento>();
            }
        } catch (Exception e) {
            listaEntity = new ArrayList<Tbldepartamento>();
        }
        return listaEntity;
    }
    
    public String actualizarCargo(){
    String flag = "";
        try {
            Connection cn = em.unwrap(java.sql.Connection.class);
            CallableStatement cs;
        } catch (Exception e) {
        }
    return flag;
    }
    
    public void insert(Tbldepartamento d){
        create(d);
    }
}
