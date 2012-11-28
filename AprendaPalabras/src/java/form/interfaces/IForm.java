package form.interfaces;

import error.Errors;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author yomac
 */
public interface IForm {

    void checkForm(HttpServletRequest hsr, Errors errors) throws Exception;
}
