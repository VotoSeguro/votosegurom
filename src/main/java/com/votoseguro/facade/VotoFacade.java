/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.votoseguro.facade;
import com.votoseguro.entity.Tblvoto;
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
public class VotoFacade extends AbstractFacade<Tblvoto>{
   @PersistenceContext(unitName = "votoseguroPU")
     private EntityManager em;   
    
    public VotoFacade(){
    super(Tblvoto.class);
    }
    
    @Override
    protected EntityManager getEntityManager(){
    return em;
    } 
    
    /*hacer insert que jale el id de periodo
      id de candidato  
      1 / numero de candiatos para valor
      select * from tblperiodo where estadoper = 'PROCESO'
     jalar id del votante
*/
}
