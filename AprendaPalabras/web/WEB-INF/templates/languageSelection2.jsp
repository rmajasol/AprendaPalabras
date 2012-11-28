<div
    <c:choose>
        <c:when test="${!empty param.error}">
            class="languageSelection fieldError"
        </c:when>
        <c:otherwise>
            class="languageSelection"
        </c:otherwise>
    </c:choose>
    >

    <jsp:include page="languageSelector.jsp">
        <jsp:param name="id" value="${param.selectorName}"/>
        <jsp:param name="name" value="${param.selectorName}"/>
        <jsp:param name="value" value="${param.selectorValue}"/>
    </jsp:include>

    <jsp:include page="textInput_withPrompt.jsp">
        <jsp:param name="id" value="${param.tfName}"/>
        <jsp:param name="name" value="${param.tfName}"/>
        <jsp:param name="value" value="${param.tfValue}"/>
        <jsp:param name="promptMsg" value="${typedLang_pmsg}"/>
    </jsp:include>

    <jsp:include page="fieldErrorMsg.jsp">
        <jsp:param name="error" value="${param.error}"/>
    </jsp:include>
</div>