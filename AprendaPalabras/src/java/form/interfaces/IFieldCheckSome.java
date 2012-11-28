/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package form.interfaces;

import exceptions.WrongFieldException;

/**
 *
 * @author yomac
 */
public interface IFieldCheckSome {
    
    /**
     * Comprueba algunas cosas propias para el campo y también comunes a otros. 
     * Por ejemplo una comprobación propia para el usuario es mirar que no exceda 
     * de 20 caracteres y que no contenga símbolos como la @. Para el email 
     * que no exceda de 254 caracteres y que la dirección sea válida. 
     * Una comprobación común es por ejemplo ver si el campo está vacío o tiene
     * espacios en blanco, aunque no con todos los campos se comprueba esto debido
     * a que pueden ser de carácter opcional o bien permitir introducir texto
     * con espacios (frases, comentarios, etc).
     * @throws WrongFieldException 
     */
    void checkSome() throws WrongFieldException;
}
