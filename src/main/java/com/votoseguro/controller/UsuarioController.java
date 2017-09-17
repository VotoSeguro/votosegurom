/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.votoseguro.controller;

import com.votoseguro.entity.Tblrol;
import com.votoseguro.entity.Tblusuario;
import com.votoseguro.facade.UsuarioFacade;
import com.votoseguro.util.ValidationBean;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author Luis Eduardo Valdez
 */
@ViewScoped
@ManagedBean(name = "usuarioController")
public class UsuarioController {
    
    @EJB
    UsuarioFacade uf;
    @EJB
    ValidationBean vb;
    
    private @Getter @Setter List<Tblusuario> listaUsuarios= new ArrayList<>();
    private @Setter @Getter Tblusuario selectedUsuario = new Tblusuario();
    private @Setter @Getter String idRol = "";
    private @Setter @Getter String userName = "";
    private @Setter @Getter String passUser = "";
    private @Setter @Getter String nombreUser = "";
    private @Setter @Getter String apellidoUser = "";
    private @Setter @Getter String passRe = "";
    
    
    
}
