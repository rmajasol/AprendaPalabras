<c:if test="${isCreatingAccep}">
    <form action="search.htm" method="POST" id="form">
        <c:if test="${create_accep_from}">
            <jsp:include page="../../templates/textInput_withPrompt.jsp">
                <jsp:param name="type" value="text"/>
                <jsp:param name="name" value="accep_from"/>
                <jsp:param name="value" value="${accep_from}"/>
                <jsp:param name="promptMsg" value="${accepFrom_pmsg}"/>
                <jsp:param name="error" value="${formErrors.accepFrom}"/>
            </jsp:include>
        </c:if>
        <c:if test="${create_accep_to}">
            <jsp:include page="../../templates/textInput_withPrompt.jsp">
                <jsp:param name="type" value="text"/>
                <jsp:param name="name" value="accep_to"/>
                <jsp:param name="value" value="${accep_to}"/>
                <jsp:param name="promptMsg" value="${accepTo_pmsg}"/>
                <jsp:param name="error" value="${formErrors.accepTo}"/>
            </jsp:include>
        </c:if>
        <input type="hidden" name="accep_from" value="${accep_from}"/>
        <input type="hidden" name="accep_to" value="${accep_to}"/>
        <input type="hidden" name="create_accep_from" value="${create_accep_from}"/>
        <input type="hidden" name="create_accep_to" value="${create_accep_to}"/>
        <input type="hidden" name="tr" value="${param.tr}"/>
        <input type="hidden" name="word_from" value="${word_from}"/>
        <input type="hidden" name="selected_from" value="${selected_from}"/>
        <input type="submit" onclick="saveScroll();"
               name="create_accep_button" value="Guardar"/>
    </form>
</c:if>