/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package form.singleFields;

import error.ErrorMsgs;
import error.Errors;
import javax.servlet.http.HttpServletRequest;
import net.tanesha.recaptcha.ReCaptchaImpl;
import net.tanesha.recaptcha.ReCaptchaResponse;
import exceptions.WrongFieldException;
import form.Form;
import form.interfaces.IFieldCheck;

/**
 * Comprueba si se introdujo bien la respuesta al captcha.
 * @author yomac
 */
public class FieldRecaptcha extends Form implements IFieldCheck {

    HttpServletRequest hsr;
    public static final String FIELD_NAME = "recaptcha";
//    public static final String RECAPTCHA_PUBLIC_KEY = "6Lcq384SAAAAAJNbXJODHihOZ9viik5OL6e_b2ra";
    
    public FieldRecaptcha(HttpServletRequest hsr) {
        this.hsr = hsr;
    }

    public void check(HttpServletRequest hsr, Errors errors) {
        try {
            String remoteAddr = hsr.getRemoteAddr();
            ReCaptchaImpl reCaptcha = new ReCaptchaImpl();
            reCaptcha.setPrivateKey("6Lcq384SAAAAAGDj1aGlUrEZf-rC9OsBnFNTWlyM");
            ReCaptchaResponse reCaptchaResponse = reCaptcha.checkAnswer(
                    remoteAddr, hsr.getParameter("recaptcha_challenge_field"),
                    hsr.getParameter("recaptcha_response_field"));
            if ("".equals(hsr.getParameter("recaptcha_response_field"))) {
                throw new WrongFieldException(ErrorMsgs.EMPTY_CAPTCHA);
            } else if (!reCaptchaResponse.isValid()) {
                throw new WrongFieldException(ErrorMsgs.WRONG_CAPTCHA);
            }
        } catch (WrongFieldException wfE) {
            errors.putWrongField(FIELD_NAME, wfE.getMessage());
        }
    }
}
