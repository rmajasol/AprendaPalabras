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

           <c:if test="${!empty param.id}">
               id="${param.id}"
           </c:if>

           value="${param.value}"
           <c:if test="${!empty param.error}">
               class="error"
           </c:if>
           />
    <jsp:include page="fieldErrorMsg.jsp">
        <jsp:param name="error" value="${param.error}"/>
    </jsp:include>
</div>