<div>
    <form action="search.htm" method="POST" id="form">
        <jsp:include page="../../templates/textAreaField_withPrompt.jsp">
            <jsp:param name="id" value="context_from"/>
            <jsp:param name="name" value="context_from"/>
            <jsp:param name="text" value="${context_from}"/>
            <jsp:param name="promptMsg" value="${contextFrom_pmsg}"/>
            <jsp:param name="error" value="${formErrors.context_from}"/>
        </jsp:include>
        <jsp:include page="../../templates/textAreaField_withPrompt.jsp">
            <jsp:param name="id" value="context_to"/>
            <jsp:param name="name" value="context_to"/>
            <jsp:param name="text" value="${context_to}"/>
            <jsp:param name="promptMsg" value="${contextTo_pmsg}"/>
            <jsp:param name="error" value="${formErrors.context_to}"/>
        </jsp:include>
        <input type="hidden" name="c" value="${c.id}"/>
        <input type="hidden" name="tr" value="${tr.trId}"/>
        <input type="hidden" name="word_from" value="${word_from}"/>
        <input type="hidden" name="selected_from" value="${selected_from}"/>
        <input type="submit" onclick="saveScroll()"
               name="edit_context_button" value="Editar contexto"/>
    </form>
</div>