<c:set var="sendLinkOk" 
           value="${!empty param.send_link_button && noErrors}"/>

<c:choose>
    <c:when test="${sendLinkOk}">
        <div class="infoBox info">
            Se ha enviado a su correo electrónico un enlace para 
            establecer una nueva contraseña.
        </div> 
        <a href="index.htm">Volver</a>
    </c:when>
    <c:otherwise>
        <div class="infoBox description">
            Se enviará un enlace para cambiar la contraseña al email con el que
            se registró en la aplicación.
        </div>
        <form action="changePassword.htm" method="post" id="form">
            <jsp:include page="../../templates/textField.jsp">
                <jsp:param name="label" value="Email:"/>
                <jsp:param name="type" value="text"/>
                <jsp:param name="name" value="email"/>
                <jsp:param name="value" value="${validator.fieldsOk.email}"/>
                <jsp:param name="error" value="${validator.errors.formErrors.email}"/>
            </jsp:include>
            <jsp:include page="../../templates/recaptchaBox.jsp">
                <jsp:param name="error" value="${validator.errors.formErrors.recaptcha}"/>
            </jsp:include>
            <div class="submitButton">
                <input type="submit" name="send_link_button" value="Enviar enlace"/>
            </div>
        </form>   
    </c:otherwise>
</c:choose>