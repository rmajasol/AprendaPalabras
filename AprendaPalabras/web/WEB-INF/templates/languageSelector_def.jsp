<c:if test="${!empty param.label}">
    <label>${param.label}</label>
</c:if>
<select id="${param.selectorName}" name="${param.selectorName}">

    <c:if test="${empty param.value}">
        <option value="${selected_lang_pmsg}">${selected_lang_pmsg}</option>
    </c:if>
    <c:if test="${!empty param.value}">
        <option value="none">Ninguno</option>
    </c:if>

    <option disabled>----------</option>

    <c:forEach var="l" items="${langList}">
        <option 
            <c:if test="${param.value == l}">
                selected="selected"
            </c:if>
            value="${l}"
            >
            ${l}
        </option>
    </c:forEach>

</select>