<%-- SI SE INDICARON NUEVOS LENGUAJES --%>

<c:out value="${newLangDialog_msg}"/>

<form action="search.htm" method="POST">
    <jsp:include page="../../templates/submitButton.jsp">
        <jsp:param name="name" value="addLang_button_yes"/>
        <jsp:param name="value" value="SI"/>
    </jsp:include>
    <jsp:include page="../../templates/submitButton.jsp">
        <jsp:param name="name" value="addLang_button_no"/>
        <jsp:param name="value" value="NO"/>
    </jsp:include>
    <input type="hidden" name="word_from" value="${word_from}"/>
    <input type="hidden" name="selected_from" value="${selected_from}"/>
    <input type="hidden" name="selected_to" value="${selected_to}"/>
    <input type="hidden" name="typed_from" value="${typed_from}"/>
    <input type="hidden" name="typed_to" value="${typed_to}"/>
    <input type="hidden" name="word_to" value="${word_to}"/>
    <input type="hidden" name="acceptation" value="${acceptation}"/>
</form>