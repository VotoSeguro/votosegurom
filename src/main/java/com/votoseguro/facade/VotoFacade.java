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
        Double valor = 1/Double.valueOf(selectedCandidatos.size());
        String valorS = String.valueOf(valor);
        for (Tblcandidato candidato : selectedCandidatos) {
            Tblvoto v = new Tblvoto();
            v.setIdcandidato(candidato);
            v.setIdperiodo(periodo);
            v.setIdvotante(votante);
            v.setValor(new BigDecimal(valorS));
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
    
    
    public boolean yaVoto(String idvotante, String idperiodo){
    boolean flag = true;
    List<Tblvoto> votos = new ArrayList<>();
        try {
            Query q = em.createNativeQuery("select * from tblvoto where idvotante = ? and idperiodo = ?",Tblvoto.class);
            q.setParameter(1, idvotante);
            q.setParameter(2, idperiodo);
            votos = q.getResultList();
            if (votos.isEmpty()) {
                flag = false;
            }
            
        } catch (Exception e) {
            System.out.println("com.votoseguro.facade.VotoFacade.yaVoto()");
            e.printStackTrace();
        }
    
    return flag;
    
    }
}
