<%--si se viene de darse de baja --%>
<c:if test="${param.unsuscr eq 'yes'}">
    <jsp:include page="../includedPages/logo.jsp"/>
    <c:set var="msg" value="Baja confirmada."/>
    <c:if test="${param.f eq 'yes'}">
        <c:set var="msg" value="Baja confirmada, ¡gracias por enviar su opinión!"/>
    </c:if>

    <jsp:include page="../templates/infoBox.jsp">
        <jsp:param name="type" value="info"/>
        <jsp:param name="text" value="${msg}"/>
    </jsp:include>
</c:if>


<%-- si no está autenticado se muestra formulario de login --%>
<c:if test="${empty sessionUser}">
    <c:redirect url="login.htm"/>
</c:if>


<c:if test="${!empty sessionUser}">
    <c:redirect url="search.htm"/>
</c:if>