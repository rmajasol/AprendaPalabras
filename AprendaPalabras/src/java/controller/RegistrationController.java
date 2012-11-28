package controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;
import service.RegistrationService;
import utils.words.Views;

/**
 * Clase controladora encargada de manejar toda petición HTTP cuya URI
 * corresponda con {@code registration.htm}
 * @author rmajasol
 */
public class RegistrationController extends ControllerHelper implements Controller {

    /**
     * <p>Método ejecutado por la clase controladora cada vez que se realiza una
     * petición HTTP sobre el recurso {@code registration.htm}, tal y como
     * indicamos en el archivo {@code WEB-INF/dispatcher-servlet.xml}, el cual
     * configura el servlet usado por Spring MVC.
     * 
     * <p>Si se ha pulsado el botón de registro se mostrará la vista devuelta
     * por {@link RegistrationService#registerUser() }. Tanto si se ha pulsado
     * como si no, se muestra la vista {@code registration.jsp}.
     * @param hsr Contiene los valores de cada variable que se envía al servidor con la petición
     * @param hsr1
     * @return El archivo .jsp (la vista) a mostrar en el navegador.
     * @throws Exception 
     */
    public ModelAndView handleRequest(HttpServletRequest hsr, HttpServletResponse hsr1) throws Exception {

        RegistrationService rService = new RegistrationService(hsr, hsr1);

        if (isPressedButton(hsr, "register_button")) {
            return rService.registerUser();
        } else if (withURLParam(hsr, "key")) {
            rService.validateUser();
        }
        return new ModelAndView(Views.REGISTRATION);
    }
}
