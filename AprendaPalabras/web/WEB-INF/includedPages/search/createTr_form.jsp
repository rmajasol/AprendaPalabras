<form action="search.htm" method="POST" id="form">

    <jsp:include page="../../templates/logicErrorBox.jsp"/>

    <div class="wordField_long">
        <label>
            <span class='addTr_label'>${word_from}</span>
            <span class='addTr_label2'>@ ${selected_from} -> ${selected_to}</span>
        </label>
        <jsp:include page="../../templates/textField_withPrompt.jsp">
            <jsp:param name="type" value="text"/>
            <jsp:param name="name" value="word_to"/>
            <jsp:param name="value" value="${word_to}"/>
            <jsp:param name="promptMsg" value="${wordTo_pmsg}"/>
            <jsp:param name="error" value="${formErrors.word_to}"/>
        </jsp:include>
    </div>
    <div class="accep_long">
        <jsp:include page="../../templates/textField_withPrompt.jsp">
            <jsp:param name="type" value="text"/>
            <jsp:param name="name" value="acceptation"/>
            <jsp:param name="value" value="${acceptation}"/>
            <jsp:param name="promptMsg" value="${acceptation_pmsg}"/>
            <jsp:param name="error" value="${formErrors.acceptance}"/>
            <jsp:param name="infoIcon" value="Hay palabras polisémicas que pueden tener varias acepciones. Por ejemplo 'gato' en inglés puede ser 'cat' para la acepción 'animal', pero 'jack' para la acepción 'gato de levantar el coche'."/>
        </jsp:include>
    </div>

    <a href="search.htm">Retroceder</a>
    <input type="submit" name="add_tr_button" value="Añadir"/>

    <input type="hidden" name="word_from" value="${word_from}"/>
    <input type="hidden" name="selected_from" value="${selected_from}"/>
    <input type="hidden" name="selected_to" value="${selected_to}"/>
</form>