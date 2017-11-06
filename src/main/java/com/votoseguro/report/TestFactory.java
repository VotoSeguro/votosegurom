/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.votoseguro.report;

import java.util.ArrayList;

/**
 *
 * @author Luis Eduardo Valdez
 */
public class TestFactory {
    
    public static java.util.List generateList(){
    java.util.List<DeptosGanados> lista = new ArrayList<>();
    
    lista.add(new DeptosGanados(50, "AHUACHAPAN", "ARENA"));
    lista.add(new DeptosGanados(30, "SAN SALVADOR", "FMLN"));
    lista.add(new DeptosGanados(40, "CHALATENANGO", "ARENA"));
    lista.add(new DeptosGanados(20, "LA PAZ", "GANA"));
    
    return lista;
    }
}
