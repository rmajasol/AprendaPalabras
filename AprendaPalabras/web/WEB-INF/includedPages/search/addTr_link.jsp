<form action="search.htm" method="POST">
    <c:if test="${!tr.added}">
        <a href="" 
           onclick="saveScroll();parentNode.submit(); return false"
           >Agregar</a>
        <input type="hidden" name="action" value="addTr"/>
    </c:if>

    <c:if test="${tr.added}">
        <a href="" 
           onclick="saveScroll();parentNode.submit(); return false"
           >Desagregar</a>
        <input type="hidden" name="action" value="remTr"/>
    </c:if>

    <input type="hidden" name="tr" value="${tr.trId}"/>
    <input type="hidden" name="word_from" value="${word_from}"/>
    <input type="hidden" name="selected_from" value="${selected_from}"/>
</form>