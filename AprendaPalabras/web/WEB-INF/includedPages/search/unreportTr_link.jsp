<form action="search.htm" method="POST">
    <a href="" 
       onclick="
           saveScroll();
           parentNode.submit(); return false"
       >No reportar Traducción</a>
    <input type="hidden" name="action" value="unreportTr"/>
    <input type="hidden" name="tr" value="${tr.trId}"/>
    <input type="hidden" name="word_from" value="${tr.wordFrom}"/>
    <input type="hidden" name="selected_from" value="${tr.langFrom}"/>
</form>