/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.votoseguro.facade;

import com.votoseguro.entity.Tblpermiso;
import com.votoseguro.entity.Tblvoto;
import java.io.File;
import java.io.InputStream;
import java.sql.Connection;
import javax.ejb.Stateless;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import net.sf.jasperreports.engine.JRExporter;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.export.JRPdfExporter;

/**
 *
 * @author Luis Eduardo Valdez
 */
@Stateless
public class ReportesFacade extends AbstractFacade<Tblvoto> {

    @PersistenceContext(unitName = "votoseguroPU")
    private EntityManager em;

    public ReportesFacade() {
        super(Tblvoto.class);
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public void pruebaReport() {
        try {

            Connection cn = em.unwrap(java.sql.Connection.class);
            String dir = "pages\\reportes\\report1.jrxml";
            String destino = System.getProperty("user.dir") + File.separator + "pdf\\prueba1.pdf";
            File file = new File(dir);
          
            
            try {
                
                ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
                String realPath = ec.getRealPath("/");
                System.out.println(realPath+dir);
                JasperReport report = JasperCompileManager.compileReport(realPath+dir);
                
                
                String canonicalPath = file.getCanonicalPath();
                JasperPrint print = JasperFillManager.fillReport(report, null, cn);
                JRExporter exporter = new JRPdfExporter();
                
                
                exporter.setParameter(JRExporterParameter.OUTPUT_FILE_NAME, destino);
                exporter.setParameter(JRExporterParameter.JASPER_PRINT, print);
                exporter.exportReport();
                System.out.println("File Created: " + destino);
            } catch (Exception e) {
                System.out.println("com.votoseguro.facade.ReportesFacade.pruebaReport()");
                e.printStackTrace();
            }
            System.out.println("exito");
        } catch (Exception e) {
            System.out.println("com.votoseguro.facade.ReportesFacade.pruebaReport()");
            e.printStackTrace();
        }

    }
}
