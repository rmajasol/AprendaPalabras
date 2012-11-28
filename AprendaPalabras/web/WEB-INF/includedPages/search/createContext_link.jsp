<c:if test="${!isCreatingContext}">
    <form action="search.htm" method="POST">
        <a href="" 
           onclick="saveScroll();parentNode.submit(); return false"
           >Crear 
            <c:if test="${fn:length(cList) gt 0}"> nuevo </c:if>
            contexto</a>
        <input type="hidden" name="action" value="createContext"/>
        <input type="hidden" name="tr" value="${tr.trId}"/>
        <input type="hidden" name="word_from" value="${tr.wordFrom}"/>
        <input type="hidden" name="selected_from" value="${tr.langFrom}"/>
    </form>
</c:if>