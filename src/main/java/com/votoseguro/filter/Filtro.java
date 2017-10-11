/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.votoseguro.filter;

import com.votoseguro.controller.VotLoginController;
import java.io.IOException;
import javax.inject.Inject;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Luis Eduardo Valdez
 */
public class Filtro implements Filter{
    
    @Inject
    VotLoginController login;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        String url= ((HttpServletRequest)request).getRequestURI().toString();
        System.err.println(url);
        //System.err.println(url[1]);//votante/Votar.xhtml
        String contextPath = ((HttpServletRequest)request).getContextPath(); //votosegurom
        System.out.println("com.votoseguro.filter.Filtro.doFilter()");
            //String contextPath = ((HttpServletRequest)request).getContextPath();
            if(url.contains("loginMantenimiento.xhtml")){
                if (!login.isAccesoMant()) {
                    ((HttpServletResponse)response).sendRedirect(contextPath + "/advertencia.xhtml");
                }
                
                
            }
        
      
         
        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {
       
    }
    
}
