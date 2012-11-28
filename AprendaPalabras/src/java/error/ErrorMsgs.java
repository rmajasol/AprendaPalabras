/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package error;

import org.apache.commons.lang3.StringEscapeUtils;

/**
 *
 * @author yomac
 */
public class ErrorMsgs {

    /**
     * LOGIC ERRORS
     *
     */
    public static final String PASSWORD_ENCRIPT_ERROR =
            "Ha habido un error al encriptar la contraseña";
    public static final String FAILURE_SENDING_EMAIL =
            "No se ha podido enviar el email a la dirección indicada.";
    public static final String LANGNAME_ENC_ERROR = "Error en la codificación del idioma";
    public static final String INVALID_ACCESS_KEY = "Clave de acceso incorrecta";
    public static final String ACCESS_KEY_ALREADY_CREATED = 
            "La dirección indicada ya dispone del enlace para cambiar la contraseña.<br>";
    public static final String LOGIN_INCORRECT = "Datos de acceso incorrectos<br>"
            + "<a href=\"changePassword.htm\">¿Olvidó su contraseña?</a>";
    public static final String PASSWORD_INCORRECT = "Contraseña incorrecta<br>"
            + "<a href=\"changePassword.htm\">¿Olvidó su contraseña?</a>";
    public static final String USER_NOT_VALIDATED =
            "Esta cuenta está correctamente registrada pero para acceder debe antes "
            + "pulsar el link de activación enviado a su correo electrónico.";
    public static final String USER_NOT_FOUND = "Usuario no encontrado";
    public static final String WORD_NOT_FOUND = "Palabra no encontrada";
    public static final String TRANSLATION_NOT_FOUND = "Traducción no encontrada";
    public static final String UNSUPPORTED_LANG_ENCODING = "Error en la codificación del lenguaje";
    public static final String REDIRECT_ERROR = "Error al redirigir a la nueva página";
    public static final String USER_HAS_TRANSLATION = 
            "No se ha podido eliminar la traducción debido a que algún usuario la agregó"
            + " a su lista.";
    public static final String TRANSLATION_NOT_CREATED_BY = 
            "La traducción a eliminar no ha sido creada por usted.";
    public static final String TRANSLATION_NOT_EXISTS = 
            "La traducción a eliminar no existe.";
    public static final String CONTEXTS_ADDED_BY_OTHERS = 
            "No se ha podido eliminar la traducción debido a que algún usuario distinto "
            + "a usted le agregó frases de contexto.";
    /**
     * FIELD ERRORS
     * 
     */
    /**
     * COMMON
     */
    public static final String FIELD_EMPTY = "Este campo no puede estar vacío";
    public static final String FIELD_WHITESPACES = "El campo no puede tener espacios en blanco";
    public static final String FIELD_NON_ALPHANUMERIC =
            "El campo sólo admite caracteres alfanuméricos (números y/o letras)";
    public static final String FIELDS_NON_REPEATED = "Estos campos deben ir repetidos";
    public static final String FIELDS_DOESNT_MATCH =
            "Los valores introducidos en estos campos no coinciden";
    /**
     * USERNAME
     */
    public static final String OVERSIZED_USERNAME =
            "El nombre de usuario supera el límite de 20 caracteres";
    public static final String UNDERSIZED_USERNAME =
            "El nombre de usuario debe tener un mínimo de 4 caracteres";
    public static final String USERNAME_EXISTS = "Nombre de usuario ocupado";
    public static final String USERNAME_DOESNT_EXISTS = "No existe el usuario indicado";
    /**
     * PASSWORD
     */
    public static final String OVERSIZED_PASSWORD =
            "La contraseña supera el límite de 20 caracteres";
    public static final String UNDERSIZED_PASSWORD =
            "La contraseña debe tener un mínimo de 6 caracteres";
    /**
     * EMAIL
     */
    public static final String OVERSIZED_EMAIL = "El email supera los 254 caracteres";
    public static final String INCORRECT_SINTAX_EMAIL = "El email descrito es incorrecto";
    public static final String EMAIL_EXISTS = "Ya hay un usuario con ese email";
    public static final String EMAIL_DOESNT_EXISTS = "No hay ningún usuario registrado con ese email";
    /**
     * LANGUAGE
     */
    public static final String LANG_NOT_SELECTED = "No se ha seleccionado un idioma";
    public static final String SAME_LANG_SELECTION = "Los idiomas elegidos son los mismos";
    public static final String LANG_FROM_NOT_SELECTED = LANG_NOT_SELECTED + " origen";
    public static final String LANG_TO_NOT_SELECTED = LANG_NOT_SELECTED + " destino";
    public static final String OVERSIZED_TYPED_LANG =
            "El nombre para el idioma no puede superar los 45 caracteres";
    /**
     * RECAPTCHA
     */
    public static final String EMPTY_CAPTCHA = "Debe completar el captcha";
    public static final String WRONG_CAPTCHA = "Captcha incorrecto";
    /**
     * WORD
     */
    public static final String OVERSIZED_WORD =
            "Ha superado el límite de 140 caracteres para la palabra o expresión";
    public static final String DOUBLE_WHITESPACED =
            "El texto introducido contiene dobles espacios";
    public static String TRANSLATION_REPORTED = 
            "Esta traducción ha sido reportada, por lo que no es posible "
            + "editarla ni eliminada hasta la supervisión del administrador.";
}
