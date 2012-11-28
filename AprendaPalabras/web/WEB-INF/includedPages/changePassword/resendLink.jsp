<c:set var="resendLinkOk" 
           value="${!empty param.resend_link_button && noErrors}"/>

<c:choose>
    <c:when test="${resendLinkOk}">
        <div class="infoBox info">
            Se ha enviado de nuevo el enlace para establecer una nueva contraseña.
        </div>
        <a href="index.htm">Volver</a>
    </c:when>
    <c:otherwise>
        <form action="changePassword.htm?action=resend" method="post" id="form">
            <jsp:include page="../../templates/recaptchaBox.jsp">
                <jsp:param name="error" value="${validator.errors.formErrors.recaptcha}"/>
            </jsp:include>
            <input type="hidden" name="email" value="${param.email}"/>
            <div class="submitButton">
                <input type="submit" name="resend_link_button" value="Reenviar enlace"/>
            </div>
        </form>    
    </c:otherwise>
</c:choose>