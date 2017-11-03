/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.votoseguro.facade;

import com.votoseguro.entity.Tblperiodo;
import com.votoseguro.entity.Tblvoto;
import com.votoseguro.report.VotantesDepto;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.sql.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
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
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;

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
                System.out.println(realPath + dir);
                JasperReport report = JasperCompileManager.compileReport(realPath + dir);
                
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
    
    public List<Integer> obtenerCantidadGenero(int idperiodo) {
        
        List<Integer> lista = new ArrayList<>();
        try {
            Connection cn = em.unwrap(java.sql.Connection.class);
            PreparedStatement ps = cn.prepareStatement("select sum(males) as \"Hombres\", sum(females) as \"Mujeres\"\n"
                    + "from\n"
                    + "(select count(*) as Males, 0 as Females from (SELECT DISTINCT v.idvotante FROM tblvoto v inner join tblvotante vo on vo.IDVOTANTE = v.IDVOTANTE where vo.GENERO = 'M' and v.IDPERIODO = ?)\n"
                    + "union all\n"
                    + "select 0 as males, count(*) as Females\n"
                    + "from (SELECT DISTINCT v.idvotante FROM tblvoto v inner join tblvotante vo on vo.IDVOTANTE = v.IDVOTANTE where vo.GENERO = 'F' and v.IDPERIODO = ?))");
            ps.setInt(1, idperiodo);
            ps.setInt(2, idperiodo);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                lista.add(rs.getInt("Hombres"));
                lista.add(rs.getInt("Mujeres"));
            }
        } catch (Exception e) {
            System.out.println("com.votoseguro.facade.ReportesFacade.obtenerPorcentajeGenero()");
            e.printStackTrace();
        }
        
        return lista;
    }
    
    public List<String> obtenerCantidadVotaron() {
        
        List<String> lista = new ArrayList<>();
        try {
            Connection cn = em.unwrap(java.sql.Connection.class);
            PreparedStatement ps = cn.prepareStatement("select  TO_CHAR(fn_obtener_porcentaje(1),'99.99') as \"Porcentaje que Votaron\",TO_CHAR(fn_obtener_porcentaje(2),'99.99') as \"Porcentaje que No Votaron\"  from dual");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                lista.add(rs.getString("Porcentaje que Votaron"));
                lista.add(rs.getString("Porcentaje que No Votaron"));
            }
        } catch (Exception e) {
            System.out.println("com.votoseguro.facade.ReportesFacade.obtenerPorcentajeGenero()");
            e.printStackTrace();
        }
        
        return lista;
    }
    
    public BufferedImage GraficoCantidadGenero(int idperiodo) {
        DefaultPieDataset pd = new DefaultPieDataset();
        List<Integer> lista = new ArrayList<>();
        JFreeChart jc = null;
        try {
            lista = obtenerCantidadGenero(idperiodo);
            pd.setValue("Hombres", lista.get(0));
            pd.setValue("Mujeres", lista.get(1));
            jc = ChartFactory.createPieChart("Cantidad de hombres y mujeres que votaron", pd, true, true, Locale.US);
        } catch (Exception e) {
            System.out.println("com.votoseguro.facade.ReportesFacade.GraficoCantidadGenero()");
            e.printStackTrace();
        }
        return jc.createBufferedImage(500, 500);
        
    }
    
    public BufferedImage GraficoCantidadVotaron() {
        DefaultPieDataset pd = new DefaultPieDataset();
        List<String> lista = new ArrayList<>();
        JFreeChart jc = null;
        try {
            lista = obtenerCantidadVotaron();
            pd.setValue("Ya Votaron", Double.parseDouble(lista.get(0)));
            pd.setValue("No Votaron", Double.parseDouble(lista.get(1)));
            jc = ChartFactory.createPieChart("Cantidad de Personas que votaron y que no votaron", pd, true, true, Locale.US);
        } catch (Exception e) {
            System.out.println("com.votoseguro.facade.ReportesFacade.GraficoCantidadGenero()");
            e.printStackTrace();
        }
        return jc.createBufferedImage(500, 500);
        
    }
    
    public void reporteCantidadGenero(Tblperiodo periodo) {
        try {
            
            Connection cn = em.unwrap(java.sql.Connection.class);
            String dir = "pages\\reportes\\CantidadGenero.jrxml";
            String destino = System.getProperty("user.dir") + File.separator + "pdf\\CantidadGenero.pdf";
            File file = new File(dir);
            Map parametersMap = new HashMap();
            parametersMap.put("grafico", GraficoCantidadGenero(Integer.parseInt(String.valueOf(periodo.getIdperiodo()))));
            parametersMap.put("idperiodo", Integer.parseInt(String.valueOf(periodo.getIdperiodo())));
            parametersMap.put("anio", String.valueOf(periodo.getAnio()));
            try {
                
                ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
                String realPath = ec.getRealPath("/");
                System.out.println(realPath + dir);
                JasperReport report = JasperCompileManager.compileReport(realPath + dir);

                //String canonicalPath = file.getCanonicalPath();
                JasperPrint print = JasperFillManager.fillReport(report, parametersMap, cn);
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
    
    public void reporteCantidadVotaron() {
        try {
            
            Connection cn = em.unwrap(java.sql.Connection.class);
            String dir = "pages\\reportes\\votaronynovotaron.jrxml";
            String destino = System.getProperty("user.dir") + File.separator + "pdf\\votaronynovotaron.pdf";
            File file = new File(dir);
            Map parametersMap = new HashMap();
            parametersMap.put("grafico", GraficoCantidadVotaron());
            
            try {
                
                ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
                String realPath = ec.getRealPath("/");
                System.out.println(realPath + dir);
                JasperReport report = JasperCompileManager.compileReport(realPath + dir);

                //String canonicalPath = file.getCanonicalPath();
                JasperPrint print = JasperFillManager.fillReport(report, parametersMap, cn);
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
    
    public List<VotantesDepto> obtenerCantidadPorDepto(Tblperiodo periodo) {
        
        List<VotantesDepto> lista = new ArrayList<>();
        
        try {
            Connection cn = em.unwrap(java.sql.Connection.class);
            PreparedStatement ps = cn.prepareStatement("select count(distinct v.IDVOTANTE) as total,d.nomdepto from tbldepartamento d\n"
                    + "inner join tblmunicipio m on m.iddepto = d.iddepto \n"
                    + "inner join tblvotante v on v.idmuni = m.IDMUNI \n"
                    + "inner join tblvoto vo on vo.idvotante = v.IDVOTANTE\n"
                    + "where idperiodo = ?\n"
                    + "group by d.nomdepto");
            ps.setInt(1, Integer.parseInt(String.valueOf(periodo.getIdperiodo())));
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                lista.add(new VotantesDepto(rs.getString("NOMDEPTO"), rs.getInt("TOTAL")));
            }
        } catch (Exception e) {
            System.out.println("com.votoseguro.facade.ReportesFacade.obtenerCantidadPorDepto()");
            e.printStackTrace();
        }
        
        return lista;
    }
    
    public void reporteCantidadDepto(Tblperiodo periodo) {
        try {
            
            Connection cn = em.unwrap(java.sql.Connection.class);
            String dir = "pages\\reportes\\cantidadDepto.jrxml";
            String destino = System.getProperty("user.dir") + File.separator + "pdf\\cantidadDepto.pdf";
            File file = new File(dir);
            Map parametersMap = new HashMap();
            parametersMap.put("idperiodo", Integer.parseInt(String.valueOf(periodo.getIdperiodo())));
            parametersMap.put("grafico", graficoBarraCantDepto(periodo));
            parametersMap.put("anio", String.valueOf(periodo.getAnio()));
            try {
                
                ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
                String realPath = ec.getRealPath("/");
                System.out.println(realPath + dir);
                JasperReport report = JasperCompileManager.compileReport(realPath + dir);

                //String canonicalPath = file.getCanonicalPath();
                JasperPrint print = JasperFillManager.fillReport(report, parametersMap, cn);
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
    
    public BufferedImage graficoBarraCantDepto(Tblperiodo periodo) {
        DefaultCategoryDataset dc = new DefaultCategoryDataset();
        JFreeChart jc = null;
        try {
            Connection cn = em.unwrap(java.sql.Connection.class);
            PreparedStatement ps = cn.prepareStatement("select count(distinct v.IDVOTANTE) as total,d.nomdepto from tbldepartamento d\n"
                    + "inner join tblmunicipio m on m.iddepto = d.iddepto \n"
                    + "inner join tblvotante v on v.idmuni = m.IDMUNI \n"
                    + "inner join tblvoto vo on vo.idvotante = v.IDVOTANTE\n"
                    + "where idperiodo = ? \n"
                    + "group by d.nomdepto");
            ps.setInt(1, Integer.parseInt(String.valueOf(periodo.getIdperiodo())));
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {                
                
                dc.addValue(rs.getInt("TOTAL"), rs.getString("NOMDEPTO"), rs.getString("NOMDEPTO"));
            }
            jc = ChartFactory.createBarChart("Total votantes por departamento elecciones diputados " + String.valueOf(periodo.getAnio()) , "Departamentos", "Votantes", dc, PlotOrientation.HORIZONTAL, true, true, true);
            CategoryPlot plot = jc.getCategoryPlot();
            BarRenderer renderer = (BarRenderer) plot.getRenderer();
            //renderer.setSeriesPaint(0, Color.green);
            //renderer.setSeriesPaint(1, Color.blue);
            renderer.setMaximumBarWidth(.70);
            
        } catch (Exception e) {
            System.out.println("com.votoseguro.facade.ReportesFacade.graficoBarraCantDepto()");
            e.printStackTrace();
        }
        
        return jc.createBufferedImage(700, 500);
        
    }
    
}
