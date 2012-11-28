<div
    <c:if test="${!empty param.error}">
        class="fieldError"
    </c:if>
    >

    <c:if test="${!empty param.label}">
        <label>${param.label}</label>
    </c:if>
        
    <input type="${param.type}"
           name="${param.name}"
           value="${param.value}"
           />
    <jsp:include page="textInput_withPrompt.jsp">
        <jsp:param name="type" value="${param.type}"/>
        <jsp:param name="name" value="${param.name}2"/>
        <jsp:param name="value" value="${param.value}"/>
        <jsp:param name="text" value="${param.text}"/>
        <jsp:param name="below" value="${param.below}"/>
    </jsp:include>
    <jsp:include page="fieldErrorMsg.jsp">
        <jsp:param name="error" value="${param.error}"/>
    </jsp:include>
</div>