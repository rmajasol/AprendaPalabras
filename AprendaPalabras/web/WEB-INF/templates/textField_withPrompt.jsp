<div
    <c:if test="${!empty param.error}">
        class="fieldError"
    </c:if>
    >
    <c:if test="${!empty param.label}">
        <label><c:out value="${param.label}" escapeXml="false"/></label>
    </c:if>
    <jsp:include page="textInput_withPrompt.jsp">
        <jsp:param name="type" value="${param.type}"/>
        <jsp:param name="id" value="${param.name}"/>
        <jsp:param name="name" value="${param.name}"/>
        <jsp:param name="value" value="${param.value}"/>
        <jsp:param name="text" value="${param.text}"/>
    </jsp:include>
    <jsp:include page="fieldErrorMsg.jsp">
        <jsp:param name="error" value="${param.error}"/>
    </jsp:include>

    <c:if test="${!empty infoIcon}">
        <jsp:include page="../infoIcon.jsp">
            <jsp:param name="altText" value="${param.infoIcon}"/>
        </jsp:include>
    </c:if>
</div>