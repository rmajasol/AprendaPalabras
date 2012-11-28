<c:if test="${!empty param.label}">
    <label>${param.label}</label>
</c:if>
    
<select id="${param.selectorName}" name="${param.selectorName}">

    <c:if test="${empty param.value}">
        <option value="${selected_lang_pmsg}">${selected_lang_pmsg}</option>
        <option disabled>----------</option>
    </c:if>

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