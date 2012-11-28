<%-- si se ha pulsado el botón de cambiar contraseña y no hay errores--%>
    <c:set var="changePasswordOk" 
           value="${!empty param.change_pass_button && noErrors}"/>
    
<c:choose>
    <c:when test="${changePasswordOk}">
        <div class="infoBox result_ok">
            Se ha cambiado la contraseña correctamente.
        </div>
        <a href="index.htm">Volver</a>
    </c:when>
    <c:otherwise>
        <form action="changePassword.htm?action=changePass" method="post" id="form">
            <jsp:include page="../../templates/textFieldRep.jsp">
                <jsp:param name="label" value="Nueva contraseña:"/>
                <jsp:param name="type" value="password"/>
                <jsp:param name="name" value="password"/>
                <jsp:param name="value" value="${password}"/>
                <jsp:param name="promptMsg" value="${password_pmsg}"/>
                <jsp:param name="error" value="${formErrors.password}"/>
            </jsp:include>
            <div>
                <input type="checkbox" name="send_data_checkbox" value="ON"
                   /> Enviarme datos de acceso.
                <jsp:include page="../../templates/infoIcon.jsp">
                    <jsp:param name="altText" 
                               value="Marcando esta casilla se enviará su nombre de usuario y contraseña al email indicado"/>
                </jsp:include>
            </div>
            <jsp:include page="../../templates/recaptchaBox.jsp">
                <jsp:param name="error" value="${validator.errors.formErrors.recaptcha}"/>
            </jsp:include>
            <input type="hidden" name="key" value="${param.key}"/>
            <div class="submitButton">
                <input type="submit" name="change_pass_button" value="Cambiar contraseña"/>
            </div>
        </form> 
    </c:otherwise>
</c:choose>
