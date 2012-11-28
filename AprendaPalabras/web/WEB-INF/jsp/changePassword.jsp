    <jsp:include page="../templates/logicErrorBox.jsp"/>

    <c:choose>

        <%-- para cambiar la contraseña --%>
        <c:when test="${param.action eq 'changePass'}">
            <jsp:include page="../includedPages/changePassword/changePass.jsp"/>
        </c:when>

        <%-- para reenviar el link --%>
        <c:when test="${param.action eq 'resend'}">
            <jsp:include page="../includedPages/changePassword/resendLink.jsp" />
        </c:when>

        <%-- para enviar el link --%>
        <c:otherwise>
            <jsp:include page="../includedPages/changePassword/sendLink.jsp" />
        </c:otherwise>

    </c:choose>  
</div>