/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.votoseguro.util;

import com.votoseguro.report.DeptosGanados;
import com.votoseguro.report.RangoEdad;
import java.io.File;
import java.io.FileWriter;
import java.util.List;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.eclipse.persistence.internal.oxm.schema.model.Element;
import org.w3c.dom.Document;

/**
 *
 * @author Luis Eduardo Valdez
 */
public class WriteXMLFile {

    public void crearXML(List<DeptosGanados> list) {
        try {
            DocumentBuilderFactory dFact = DocumentBuilderFactory.newInstance();
            DocumentBuilder build = dFact.newDocumentBuilder();
            Document doc = build.newDocument();

            org.w3c.dom.Element root = doc.createElement("ganados");
            doc.appendChild(root);
            

            for (DeptosGanados dg : list) {
                org.w3c.dom.Element Details = doc.createElement("fila");
                root.appendChild(Details);
                org.w3c.dom.Element total = doc.createElement("total");
                total.appendChild(doc.createTextNode(String.valueOf(dg.getTotal())));
                Details.appendChild(total);

                org.w3c.dom.Element nomdepto = doc.createElement("nomdepto");
                nomdepto.appendChild(doc.createTextNode(String.valueOf(dg.getNomdepto())));
                Details.appendChild(nomdepto);

                org.w3c.dom.Element nompartido = doc.createElement("nompartido");
                nompartido.appendChild(doc.createTextNode(String.valueOf(dg.getNompartido())));
                Details.appendChild(nompartido);
            }

            TransformerFactory tranFactory = TransformerFactory.newInstance();
            Transformer aTransformer = tranFactory.newTransformer();

            aTransformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
            aTransformer.setOutputProperty(
                    "{http://xml.apache.org/xslt}indent-amount", "4");
            aTransformer.setOutputProperty(OutputKeys.INDENT, "yes");
              
            DOMSource source = new DOMSource(doc);
            try {
                FileWriter file = new FileWriter("xml/deptoPartido.xml");
                StreamResult result = new StreamResult(file);
                aTransformer.transform(source, result);
                
            } catch (Exception e) {
                System.out.println("com.votoseguro.util.WriteXMLFile.crearXML()");
                e.printStackTrace();
            }
            
        } catch (Exception e) {
            System.out.println("com.votoseguro.util.WriteXMLFile.crearXML()");
            e.printStackTrace();
        }

    }
    
    public void crearXMLEdad(List<RangoEdad> list) {
        try {
            DocumentBuilderFactory dFact = DocumentBuilderFactory.newInstance();
            DocumentBuilder build = dFact.newDocumentBuilder();
            Document doc = build.newDocument();

            org.w3c.dom.Element root = doc.createElement("rango");
            doc.appendChild(root);
            

            for (RangoEdad dg : list) {
                org.w3c.dom.Element Details = doc.createElement("fila");
                root.appendChild(Details);
                org.w3c.dom.Element total = doc.createElement("titulo");
                total.appendChild(doc.createTextNode(String.valueOf(dg.getTitulo())));
                Details.appendChild(total);

                org.w3c.dom.Element nomdepto = doc.createElement("cantidad");
                nomdepto.appendChild(doc.createTextNode(String.valueOf(dg.getCantidad())));
                Details.appendChild(nomdepto);

                /*org.w3c.dom.Element nompartido = doc.createElement("nompartido");
                nompartido.appendChild(doc.createTextNode(String.valueOf(dg.getNompartido())));
                Details.appendChild(nompartido);*/
            }

            TransformerFactory tranFactory = TransformerFactory.newInstance();
            Transformer aTransformer = tranFactory.newTransformer();

            aTransformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
            aTransformer.setOutputProperty(
                    "{http://xml.apache.org/xslt}indent-amount", "4");
            aTransformer.setOutputProperty(OutputKeys.INDENT, "yes");
              
            DOMSource source = new DOMSource(doc);
            try {
                FileWriter file = new FileWriter("xml/RangoEdad.xml");
                StreamResult result = new StreamResult(file);
                aTransformer.transform(source, result);
                
            } catch (Exception e) {
                System.out.println("com.votoseguro.util.WriteXMLFile.crearXML()");
                e.printStackTrace();
            }
            
        } catch (Exception e) {
            System.out.println("com.votoseguro.util.WriteXMLFile.crearXML()");
            e.printStackTrace();
        }

    }

}
