/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package form.interfaces;

import error.Errors;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author yomac
 */
public interface IFieldCheck {
    
    public void check(HttpServletRequest hsr, Errors e);
}
