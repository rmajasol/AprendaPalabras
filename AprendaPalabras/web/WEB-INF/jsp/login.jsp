<%@include  file="../includedPages/logo.jsp"%>

<form id="form" class="login" action="login.htm" method="post">

    <jsp:include page="../templates/logicErrorBox.jsp"/>

    <%--Poner flush="true" indica que muestre antes esta página que la del include--%>
    <jsp:include page="../templates/textField.jsp">
        <jsp:param name="label" value="Usuario:"/>
        <jsp:param name="type" value="text"/>
        <jsp:param name="name" value="username"/>
        <jsp:param name="id" value="username"/>
    </jsp:include>

    <jsp:include page="../templates/textField.jsp">
        <jsp:param name="label" value="Contraseña:"/>
        <jsp:param name="type" value="password"/>
        <jsp:param name="name" value="password"/>
    </jsp:include>

    <div>
        <input type="checkbox" name="dont_close" value="on" CHECKED/>
        No cerrar sesión
        <jsp:include page="../templates/infoIcon.jsp">
            <jsp:param name="altText" 
                       value="Al marcar esta casilla su cuenta seguirá abierta aunque cierre el navegador"/>
        </jsp:include>
    </div>

    <div>
        <a href="registration.htm">Registrarse</a>
        <input type="submit" name="login_button" value="Identificarse"/>
    </div>    

</form>
