<c:if test="${!empty newLangDialogResult_msg}">
    <div class="infoBox">
        <c:out value="${newLangDialogResult_msg}" escapeXml="false"/>
    </div>
</c:if>

<c:if test="${param.validated eq 'yes'}">
    <div class="infoBox result_ok">
        Bienvenid@ ${sessionUser.username}, ya puede comenzar a usar la aplicación.
    </div>
</c:if>

<form action="search.htm" method="post" id="form">

    <jsp:include page="../../templates/logicErrorBox.jsp"/>
    <div class="wordField_long">
        <jsp:include page="../../templates/textField_withPrompt.jsp">
            <jsp:param name="type" value="text"/>
            <jsp:param name="name" value="word_from"/>
            <jsp:param name="value" value="${word_from}"/>
            <jsp:param name="promptMsg" value="${wordFrom_pmsg}"/>
            <jsp:param name="error" value="${formErrors.word_from}"/>
            <jsp:param name="focus" value="yes"/>
        </jsp:include>
    </div>
    <div id="langSelectors"
         <c:if test="${!empty formErrors.translationSelection}">
             class="fieldError"
         </c:if>
         >
        <jsp:include page="../../templates/languageSelection2.jsp">
            <jsp:param name="id" value="langFrom"/>
            <jsp:param name="label" value="De:"/>
            <jsp:param name="selectorName" value="selected_from"/>
            <jsp:param name="selectorValue" value="${selected_from}"/>
            <jsp:param name="tfName" value="typed_from"/>
            <jsp:param name="tfValue" value="${typed_from}"/>
            <jsp:param name="tfError" value="${formErrors.typed_from}"/>
            <jsp:param name="error" value="${formErrors.languageFrom}"/>
            <jsp:param name="below" value="yes"/>
        </jsp:include>

        <input id="invertSelButton" type="button" name="invert" value="<->"
               onclick="invertSelection()"/>

        <jsp:include page="../../templates/languageSelection2.jsp">
            <jsp:param name="id" value="langTo"/>
            <jsp:param name="label" value="A:"/>
            <jsp:param name="selectorName" value="selected_to"/>
            <jsp:param name="selectorValue" value="${selected_to}"/>
            <jsp:param name="tfName" value="typed_to"/>
            <jsp:param name="tfValue" value="${typed_to}"/>
            <jsp:param name="tfError" value="${formErrors.typed_to}"/>
            <jsp:param name="error" value="${formErrors.languageTo}"/>
            <jsp:param name="below" value="yes"/>
        </jsp:include>        
    </div>

    <jsp:include page="../../templates/submitButton.jsp">
        <jsp:param name="name" value="search_button"/>
        <jsp:param name="value" value="Buscar"/>
    </jsp:include>
</form>