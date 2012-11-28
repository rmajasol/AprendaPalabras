/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import utils.maxmindGeoIP.*;

/**
 *
 * @author yomac
 */
public final class Locator {

    private String countryCode;
    private String region;

    public Locator(HttpServletRequest hsr) {
        HttpSession session = hsr.getSession();
        if (session.getAttribute("countryCode") == null
                || session.getAttribute("region") == null) {
            String ip = hsr.getRemoteAddr();
            Location location = getLocation(ip);
            countryCode = location.countryCode;
            region = location.region;
            session.setAttribute("countryCode", countryCode);
            session.setAttribute("region", region);
        }
    }

    public Location getLocation(String ip) {
        try {
            LookupService cl = new LookupService(Common.GEOIP_DB_PATH,
                    LookupService.GEOIP_STANDARD);//lee directamente desde el binario, sin mirar cache ya que daba error al ejecutarlo desde Tomcat con la opción GEOIP_MEMORY_CACHE

//            Location l1 = cl.getLocation("213.52.50.8");
            Location l = cl.getLocation(ip);
//            Location l2 = cl.getLocation("2.136.131.125");
//            System.out.println("countryCode: " + l2.countryCode
//                    + "\n countryName: " + l2.countryName
//                    + "\n region: " + l2.region
//                    + "\n regionName: " + regionName.regionNameByCode(l2.countryCode, l2.region)
//                    + "\n city: " + l2.city
//                    + "\n postalCode: " + l2.postalCode
//                    + "\n latitude: " + l2.latitude
//                    + "\n longitude: " + l2.longitude
//                    + "\n distance: " + l2.distance(l1)
//                    + "\n distance: " + l1.distance(l2)
//                    + "\n metro code: " + l2.metro_code
//                    + "\n area code: " + l2.area_code
//                    + "\n timezone: " + timeZone.timeZoneByCountryAndRegion(l2.countryCode, l2.region));

            cl.close();
            return l;
        } catch (IOException e) {
            System.out.println("IO Exception");
            return null;
        }
    }

    public String getCountryCode() {
        return countryCode;
    }

    public String getRegion() {
        return region;
    }
}
