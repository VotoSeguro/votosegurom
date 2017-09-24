/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.votoseguro.util;

import com.votoseguro.controller.LoginMantController;
import com.votoseguro.entity.Tblrolxpermiso;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.MessageDigest;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import javax.ejb.Stateless;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedProperty;
import javax.faces.context.FacesContext;
import lombok.Getter;
import lombok.Setter;

import org.primefaces.context.RequestContext;

/**
 *
 * @author LAP
 */
@Stateless
public class ValidationBean {
    
    
    

    public boolean validarCampoVacio(String c, String tipoMsg, String tituloMsg, String descMsg) {
        boolean flag;
        if (c != null) {
            if ("".equals(c)) {
                flag = false;
                lanzarMensaje(tipoMsg, tituloMsg, descMsg);
            } else {
                flag = true;
            }
        } else {
            lanzarMensaje(tipoMsg, tituloMsg, descMsg);
            flag = false;
        }
        return flag;
    }
    
    public boolean validarFecha(String c, String tipoMsg, String tituloMsg, String descMsg){
      Pattern patron = Pattern.compile("^(?:(?:31(\\/|-|\\.)(?:0?[13578]|1[02]))\\1|(?:(?:29|30)(\\/|-|\\.)(?:0?[1,3-9]|1[0-2])\\2))(?:(?:1[6-9]|[2-9]\\d)?\\d{2})$|^(?:29(\\/|-|\\.)0?2\\3(?:(?:(?:1[6-9]|[2-9]\\d)?(?:0[48]|[2468][048]|[13579][26])|(?:(?:16|[2468][048]|[3579][26])00))))$|^(?:0?[1-9]|1\\d|2[0-8])(\\/|-|\\.)(?:(?:0?[1-9])|(?:1[0-2]))\\4(?:(?:1[6-9]|[2-9]\\d)?\\d{2})$");
        Matcher validar = patron.matcher(c);
        boolean flag;
        if (validar.find()) {
            flag = true;
            System.out.println("invalid");
            
        } else {
            flag = false;
            lanzarMensaje(tipoMsg, tituloMsg, descMsg);
        }System.out.println("valid");
        return flag;
    
    }

    public boolean validarSoloLetras(String c, String tipoMsg, String tituloMsg, String descMsg) {
        Pattern patron = Pattern.compile("[^A-Za-z-ZñÑáéíóúÁÉÍÓÚ ]");
        Matcher validar = patron.matcher(c);
        boolean flag;
        if (validar.find()) {
            flag = false;
            lanzarMensaje(tipoMsg, tituloMsg, descMsg);
        } else {
            flag = true;
        }
        return flag;
    }

    public boolean validarLongitudCampo(String c, int min, int max, String tipoMsg, String tituloMsg, String descMsg) {
        boolean flag;
        if (!(c != null && c.length() >= min && c.length() <= max)) {
            flag = false;
            lanzarMensaje(tipoMsg, tituloMsg, descMsg);
        } else {
            flag = true;
        }
        return flag;
    }

    public boolean validarEmail(String c, String tipoMsg, String tituloMsg, String descMsg) {
        Pattern patron = Pattern.compile("^[\\w-]+(\\.[\\w-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");
        Matcher validar = patron.matcher(c);
        boolean flag;
        if (!validar.find()) {
            flag = false;
            lanzarMensaje(tipoMsg, tituloMsg, descMsg);
        } else {
            flag = true;
        }
        return flag;
    }

    public boolean validarSoloNumeros(String c, String tipoMsg, String tituloMsg, String descMsg) {
        Pattern patron = Pattern.compile("[^0-9]");
        Matcher validar = patron.matcher(c);
        boolean flag;
        if (validar.find()) {
            flag = false;
            lanzarMensaje(tipoMsg, tituloMsg, descMsg);
        } else {
            flag = true;
        }
        return flag;
    }

    public void lanzarMensaje(String tipo, String titulo, String msg) {
        FacesMessage.Severity typeMessage;
        switch (tipo.toLowerCase()) {
            case "info":
                typeMessage = FacesMessage.SEVERITY_INFO;
                break;
            case "warn":
                typeMessage = FacesMessage.SEVERITY_WARN;
                break;
            case "fatal":
                typeMessage = FacesMessage.SEVERITY_FATAL;
                break;
            case "error":
                typeMessage = FacesMessage.SEVERITY_ERROR;
                break;
            default:
                typeMessage = FacesMessage.SEVERITY_INFO;
                break;
        }
        FacesContext context = FacesContext.getCurrentInstance();
        context.addMessage(null, new FacesMessage(typeMessage, getMsgBundle(titulo), getMsgBundle(msg)));
    }

    public String getMsgBundle(String key) {
        ResourceBundle bundle = ResourceBundle.getBundle("/Bundle", FacesContext.getCurrentInstance().getViewRoot().getLocale());
        String value = bundle.getString(key);
        return value;
    }
     public void ejecutarJavascript(String codigo){
        RequestContext requestContext = RequestContext.getCurrentInstance();  
        requestContext.execute(codigo);
    }

  /*  public String Encriptar(String texto) {

        String secretKey = "infomedicsolutions"; //llave para encriptar datos
        String base64EncryptedString = "";

        try {

            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] digestOfPassword = md.digest(secretKey.getBytes("utf-8"));
            byte[] keyBytes = Arrays.copyOf(digestOfPassword, 24);

            SecretKey key = new SecretKeySpec(keyBytes, "DESede");
            Cipher cipher = Cipher.getInstance("DESede");
            cipher.init(Cipher.ENCRYPT_MODE, key);

            byte[] plainTextBytes = texto.getBytes("utf-8");
            byte[] buf = cipher.doFinal(plainTextBytes);
            byte[] base64Bytes = Base64.encodeBase64(buf);
            base64EncryptedString = new String(base64Bytes);

        } catch (Exception ex) {
        }
        return base64EncryptedString;
    }

    public String Desencriptar(String textoEncriptado) throws Exception {

        String secretKey = "infomedicsolutions"; //llave para encriptar datos
        String base64EncryptedString = "";

        try {
            byte[] message = Base64.decodeBase64(textoEncriptado.getBytes("utf-8"));
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] digestOfPassword = md.digest(secretKey.getBytes("utf-8"));
            byte[] keyBytes = Arrays.copyOf(digestOfPassword, 24);
            SecretKey key = new SecretKeySpec(keyBytes, "DESede");

            Cipher decipher = Cipher.getInstance("DESede");
            decipher.init(Cipher.DECRYPT_MODE, key);

            byte[] plainText = decipher.doFinal(message);

            base64EncryptedString = new String(plainText, "UTF-8");

        } catch (Exception ex) {
        }
        return base64EncryptedString;
    }

    public String obtenerFechaActual() {
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        Date date = new Date();
        System.out.println(dateFormat.format(date)); //2016/11/16 12:08:43
        return dateFormat.format(date);
    }

    public boolean validarFecha(String fecha, String tipo, String titulo, String msg) {
        boolean flag = true;
        if (fecha != null && fecha.length() == 10) {
            String[] ftemp = fecha.split("/");
            Integer dias = new Integer(ftemp[0]);
            Integer mes = new Integer(ftemp[1]);
            Integer anio = new Integer(ftemp[2]);
            if (anio >= 1900 && anio <= 2100) {
                if (mes >= 1 && mes <= 12) {
                    if (mes == 2) {
                        if (!(dias >= 1 && dias <= 28)) {
                            flag = false;
                        }
                    } else if (!(dias >= 1 && dias <= 31)) {
                        flag = false;
                    }
                } else {
                    flag = false;
                }
            } else {
                flag = false;
            }
        } else {
            flag = false;
        }
        if (!flag) {
            lanzarMensaje(tipo, titulo, msg);
        }
        return flag;
    }*/
     
     

    public String formatearFecha(String fecha) {
        try {
            System.err.println("Fecha al formatear fecha:    "+ fecha);
            //String dateStr = "Mon Jun 18 00:00:00 IST 2012";
            DateFormat formatter = new SimpleDateFormat("E MMM dd HH:mm:ss Z yyyy", Locale.US);
            Date date = (Date) formatter.parse(fecha);
            System.out.println(date);
            
            Calendar cal = Calendar.getInstance();
            cal.setTime(date);
            String formatedDate = (cal.get(Calendar.DATE)<10?"0"+cal.get(Calendar.DATE):cal.get(Calendar.DATE)) + "/" + ((cal.get(Calendar.MONTH) + 1)<10?"0"+(cal.get(Calendar.MONTH) + 1):(cal.get(Calendar.MONTH) + 1)) + "/" + cal.get(Calendar.YEAR);
            System.out.println("formatedDate : " + formatedDate);
            fecha= formatedDate;
        } catch (ParseException ex) {
            System.err.println("Fecha al formatear fecha en exception:    "+ fecha);
            //fecha="";
        }
        return fecha;
    }
    
    public void updateComponent(String id){
        RequestContext.getCurrentInstance().update(id);
    }
    
    public boolean copyFile(String fileName,String destination, InputStream in) {
           boolean flag= false;
           try {
              
              
                // write the inputStream to a FileOutputStream
                OutputStream out = new FileOutputStream(new File(destination + fileName));
              
                int read = 0;
                byte[] bytes = new byte[1024];
              
                while ((read = in.read(bytes)) != -1) {
                    out.write(bytes, 0, read);
                }
              
                in.close();
                out.flush();
                out.close();
              
                System.out.println("New file created!");
                flag=true;
                } catch (IOException e) {
                System.out.println(e.getMessage());
                }
           return flag;
    }
    
    public boolean deleteFile(String file){
        boolean flag=false;
        File fichero = new File(file);
        if (fichero.delete()) {
            flag=true;
        }
        return flag;
    }
    
    
    
   
}
