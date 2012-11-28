<c:if test="${!isCreatingAccep}">
    <form action="search.htm" method="POST" onsubmit="saveScroll()">
        <a href="" 
           onclick="saveScroll();parentNode.submit(); return false"
           >Crear acepción</a>
        <input type="hidden" name="action" value="createAccep"/>
        <input type="hidden" name="tr" value="${tr.trId}"/>
        <input type="hidden" name="word_from" value="${tr.wordFrom}"/>
        <input type="hidden" name="selected_from" value="${tr.langFrom}"/>
    </form>
</c:if>