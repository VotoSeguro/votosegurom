/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.votoseguro.facade;
import com.votoseguro.entity.Tblcandidato;
import com.votoseguro.entity.Tblperiodo;
import com.votoseguro.entity.Tblvotante;
import com.votoseguro.entity.Tblvoto;
import java.math.BigDecimal;
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
    
    public void votar(List<Tblcandidato> selectedCandidatos,Tblperiodo periodo, Tblvotante votante){
        int c = 0;
        boolean flag = false;
        List<Tblvoto> voto = new ArrayList<>();
        String valor = String.valueOf(1/selectedCandidatos.size());
        for (Tblcandidato candidato : selectedCandidatos) {
            Tblvoto v = new Tblvoto();
            v.setIdcandidato(candidato);
            v.setIdperiodo(periodo);
            v.setIdvotante(votante);
            v.setValor(new BigDecimal(valor));
            voto.add(v);
        }
        for (Tblvoto tblvoto : voto) {
            try {
               create(tblvoto);
               c++;
            } catch (Exception e) {
                System.out.println("com.votoseguro.facade.VotoFacade.votar()");
                e.printStackTrace();
            }
            
        }
        if (c == selectedCandidatos.size()) {
            flag = true;
            System.out.println("se insertaron todos");
        }
    
    }
}
