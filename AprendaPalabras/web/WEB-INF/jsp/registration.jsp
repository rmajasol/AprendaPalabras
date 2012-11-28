<form action="registration.htm" method="post" id="form">
    <jsp:include page="../templates/logicErrorBox.jsp"/>
    <jsp:include page="../templates/textField.jsp">
        <jsp:param name="label" value="Nombre de usuario:"/>
        <jsp:param name="type" value="text"/>
        <jsp:param name="name" value="username"/>
        <jsp:param name="value" value="${username}"/>
        <jsp:param name="error" value="${formErrors.username}"/>
    </jsp:include>
    <jsp:include page="../templates/textFieldRep.jsp">
        <jsp:param name="label" value="Contraseña:"/>
        <jsp:param name="promptMsg" value="${password_pmsg}"/>
        <jsp:param name="type" value="password"/>
        <jsp:param name="name" value="password"/>
        <jsp:param name="value" value="${password}"/>
        <jsp:param name="error" value="${formErrors.password}"/>
    </jsp:include>
    <jsp:include page="../templates/textFieldRep.jsp">
        <jsp:param name="label" value="Email:"/>
        <jsp:param name="name" value="email"/>
        <jsp:param name="promptMsg" value="${email_pmsg}"/>
        <jsp:param name="type" value="text"/>
        <jsp:param name="value" value="${email}"/>
        <jsp:param name="error" value="${formErrors.email}"/>
        <jsp:param name="below" value="yes"/>
    </jsp:include>
    <jsp:include page="../templates/languageSelection1.jsp">
        <jsp:param name="selectorName" value="selected_lang"/>
        <jsp:param name="selectorValue" value="${selected_lang}"/>
        <jsp:param name="tfName" value="typed_lang"/>
        <jsp:param name="tfValue" value="${typed_lang}"/>
        <jsp:param name="error" value="${formErrors.typed_lang}"/>
    </jsp:include>
    <jsp:include page="../templates/recaptchaBox.jsp"/>
    <jsp:include page="../templates/submitButton.jsp">
        <jsp:param name="name" value="register_button"/>
        <jsp:param name="value" value="Registrarse"/>
    </jsp:include>
</form> 