<form id="form" action="${pageURL}" method="POST">
    
    <div class="floating">
        <jsp:include page="../../templates/languageSelector.jsp">
            <jsp:param name="label" value="De:"/>
            <jsp:param name="selectorName" value="selected_from"/>
            <jsp:param name="value" value="${selected_from}"/>
        </jsp:include>
        <jsp:include page="../../templates/textField_withPrompt.jsp">
            <jsp:param name="type" value="text"/>
            <jsp:param name="name" value="word_from"/>
            <jsp:param name="value" value="${word_from}"/>
            <jsp:param name="promptMsg" value="${wordFrom_pmsg}"/>
            <jsp:param name="error" value="${formErrors.word_from}"/>
        </jsp:include>
        <jsp:include page="../../templates/textField_withPrompt.jsp">
            <jsp:param name="label" value="Para la acepción:"/>
            <jsp:param name="type" value="text"/>
            <jsp:param name="name" value="accep_from"/>
            <jsp:param name="value" value="${accep_from}"/>
            <jsp:param name="promptMsg" value="${accepFrom_pmsg}"/>
            <jsp:param name="error" value="${formErrors.accep_from}"/>
        </jsp:include>
    </div>

    <div class="floating">
        <jsp:include page="../../templates/languageSelector.jsp">
            <jsp:param name="label" value="A:"/>
            <jsp:param name="selectorName" value="selected_to"/>
            <jsp:param name="value" value="${selected_to}"/>
        </jsp:include>
        <jsp:include page="../../templates/textField_withPrompt.jsp">
            <jsp:param name="type" value="text"/>
            <jsp:param name="name" value="word_to"/>
            <jsp:param name="value" value="${word_to}"/>
            <jsp:param name="promptMsg" value="${wordTo_pmsg}"/>
            <jsp:param name="error" value="${formErrors.word_to}"/>
        </jsp:include>
        <jsp:include page="../../templates/textField_withPrompt.jsp">
            <jsp:param name="label" value="Para la acepción:"/>
            <jsp:param name="type" value="text"/>
            <jsp:param name="name" value="accep_to"/>
            <jsp:param name="value" value="${accep_to}"/>
            <jsp:param name="promptMsg" value="${accepTo_pmsg}"/>
            <jsp:param name="error" value="${formErrors.accep_to}"/>
        </jsp:include>
    </div>
    
    <input type="hidden" name="action" value="edit"/>
    <input type="hidden" name="tr" value="${tr.trId}"/>
    
    <jsp:include page="../../templates/submitButton.jsp">
        <jsp:param name="name" value="edit_tr_button"/>
        <jsp:param name="value" value="Editar"/>
    </jsp:include>
</form>