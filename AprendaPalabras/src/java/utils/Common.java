/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

import data.dao.LanguageDAO;
import data.model.User;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.apache.commons.lang3.RandomStringUtils;

/**
 *
 * @author yomac
 */
public class Common {

    /** 
     * Patrón que deben seguir las cadenas de caracteres de tipo alfanumérico. 
     * Introducimos los espacios en blanco para que sólo salga el error de 
     * espacios en blanco en caso de introducir correctamente valores alfanuméricos.
     */
    public static final String ALPHANUMERIC_PATTERN = "[[ ]*[a-zA-z0-9]*[ ]*[a-zA-z0-9]*[ ]*]*";
    public static final int ACCESS_KEY_SIZE = 80;
    public static final String GEOIP_DB_PATH = "/Users/yomac/Drrweb/Utils/GeoLiteCity.dat";

    public static String generateKey() {
        return RandomStringUtils.randomAlphanumeric(ACCESS_KEY_SIZE);
    }

    /**
     * sea cual sea la configuración de Tomcat forzamos la codificación a UTF-8 
     * para mostrar ñ, acentos, etc
     * @param txt
     * @return
     * @throws UnsupportedEncodingException 
     */
    public static String forceUTF8(String txt) throws UnsupportedEncodingException {
        return new String(txt.getBytes("ISO-8859-1"), "UTF-8");
    }

    public static String encodeToURL(String txt) throws UnsupportedEncodingException {
        return URLEncoder.encode(txt, "UTF-8");
    }

    /**
     * pone la primera letra en mayúscula
     * @param txt
     * @return 
     */
    public static String firstUpperCase(String txt) {
        return txt.substring(0, 1).toUpperCase() + txt.substring(1, txt.length());
    }

    public static String firstLowerCase(String txt) {
        return txt.substring(0, 1).toLowerCase() + txt.substring(1, txt.length());
    }

    public static User getUserFromSession(HttpServletRequest hsr) {
        HttpSession session = hsr.getSession();
        return (User) session.getAttribute("sessionUser");
    }

    public static void loadLangList(HttpServletRequest hsr) {
        hsr.setAttribute("langList", new LanguageDAO().listAllLanguagesOrdered());
    }

    public static String getBaseUrl(HttpServletRequest request) {
        if ((request.getServerPort() == 80)
                || (request.getServerPort() == 443)) {
            return request.getScheme() + "://"
                    + request.getServerName()
                    + request.getContextPath();
        } else {
            return request.getScheme() + "://"
                    + request.getServerName() + ":" + request.getServerPort()
                    + request.getContextPath();
        }
    }

    /**
     * from http://www.exampledepot.com/egs/javax.servlet/GetReqUrl.html
     * @param hsr
     * @return 
     */
    public static String getFullUrl(HttpServletRequest hsr) {
        String reqUrl = hsr.getRequestURL().toString();
        String queryString = hsr.getQueryString();   
        if (queryString != null) {
            reqUrl += "?" + queryString;
        }
        return reqUrl;
    }
    
    public static void saveInSessionUser(HttpServletRequest hsr, String attribName, Object o) {
        HttpSession session = hsr.getSession();
        session.setAttribute(attribName, o);
    }
    
    public static void removeFromSession(HttpServletRequest hsr, String attribName) {
        HttpSession session = hsr.getSession();
        session.removeAttribute(attribName);
    }
    
    public static Object getFromSession(HttpServletRequest hsr, String attribName){
        HttpSession session = hsr.getSession();
        return session.getAttribute(attribName);
    }
}
