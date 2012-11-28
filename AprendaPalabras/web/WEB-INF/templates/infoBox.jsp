<c:choose>

    <c:when test="${param.type == 'result_fail'}">
        <div class="infoBox result_fail">${param.text}</div>
    </c:when>

    <c:when test="${param.type == 'result_ok'}">
        <div class="infoBox result_ok">${param.text}</div>
    </c:when>

    <c:when test="${param.type == 'info'}">
        <div class="infoBox info">${param.text}</div>
    </c:when>

    <c:when test="${param.type == 'description'}">
        <div class="infoBox description">${param.text}</div>
    </c:when>

    <c:when test="${param.type ne 'description'}">
        <jsp:include page="../return.jsp"/>
    </c:when>

</c:choose>