/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.votoseguro.filter;

import com.votoseguro.controller.LoginMantController;
import com.votoseguro.controller.VotLoginController;
import com.votoseguro.entity.Tblrolxpermiso;
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
public class FiltroPages implements Filter{
    
    @Inject
    VotLoginController login;
    @Inject
    LoginMantController loginMant;
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        String url= ((HttpServletRequest)request).getRequestURI().toString();
        System.err.println(url);
        //System.err.println(url[1]);//votante/Votar.xhtml
        String contextPath = ((HttpServletRequest)request).getContextPath(); //votosegurom
        System.out.println("com.votoseguro.filter.FiltroPages.doFilter()");
            //String contextPath = ((HttpServletRequest)request).getContextPath();
            if(url.contains("Votar.xhtml")){
                
                if (login.isVotLogged()) {
                     if (!login.mostrarVotar()) {
                    System.out.println("votarFILTER()");
                    ((HttpServletResponse)response).sendRedirect(contextPath + "/index.xhtml");
                }
                }else{
                ((HttpServletResponse)response).sendRedirect(contextPath + "/index.xhtml");
                }
               
                
                
                
            }else if(url.contains("confirmacion.xhtml")){
               
                if (login.isVotLogged()) {
                   if (login.mostrarVotar()) {
                    System.out.println("votarFILTER()");
                    ((HttpServletResponse)response).sendRedirect(contextPath + "/index.xhtml");
                } 
                }else{
                ((HttpServletResponse)response).sendRedirect(contextPath + "/index.xhtml");
                }
            
            }else{
                System.out.println("pages/");
                if (!loginMant.isLoggedIn()) {
                    System.out.println("MANTSFILTER()");
                    ((HttpServletResponse)response).sendRedirect(contextPath + "/index.xhtml");
                }else{
                    
                    if (url.contains("gestionar.xhtml")) {
                        if (!loginMant.isLoggedIn()) {
                            ((HttpServletResponse)response).sendRedirect(contextPath + "/index.xhtml");
                        }
                    }else{
                     int c = 0;
                    System.out.println("logeado");
                    String[] urlpieces = url.split("/pages/");
                    System.out.println("URL AUQI 0 " + urlpieces[0]);
                    System.out.println("URL AUQI 1 " + urlpieces[1]);
                    for (Tblrolxpermiso obj : loginMant.getLogedUser().getIdrol().getTblrolxpermisoList()) {
                        if (obj.getIdpermiso().getUrlpermiso().contains(urlpieces[1])) {
                            c++;
                        }
                    }
                    
                    if (c == 0) {
                        ((HttpServletResponse)response).sendRedirect(contextPath + "/index.xhtml");
                    }
                    
                    
                    
                    }
                    
                    
                   
                
                }
                
            
            }
        
      
         
        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {
       
    }
    
}
