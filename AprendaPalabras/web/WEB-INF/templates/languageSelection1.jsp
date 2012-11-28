<div
    <c:if test="${!empty param.error}">
        class="fieldError"
    </c:if>
    >

    <jsp:include page="languageSelector.jsp">
        <jsp:param name="name" value="${param.selectorName}"/>
        <jsp:param name="value" value="${param.selectorValue}"/>
    </jsp:include>

    <jsp:include page="textInput_withPrompt_undived.jsp">
        <jsp:param name="name" value="${param.tfName}"/>
        <jsp:param name="value" value="${param.tfValue}"/>
        <jsp:param name="promptMsg" value="${typedLang_pmsg}"/>
    </jsp:include>

    <jsp:include page="fieldErrorMsg.jsp">
        <jsp:param name="error" value="${param.error}"/>
    </jsp:include>
</div>