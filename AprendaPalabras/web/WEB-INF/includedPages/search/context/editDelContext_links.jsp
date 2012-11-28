<form action="search.htm" method="POST">
    <a href="" 
       onclick="
           saveScroll();
           parentNode.action += '?action=editContext';
           parentNode.submit(); return false"
       >Editar</a>
    <a href="" 
       onclick="
           saveScroll();
           parentNode.action += '?action=remContext';
           parentNode.submit(); return false"
       >Eliminar</a>
    <input type="hidden" name="c" value="${c.id}"/>
    <input type="hidden" name="tr" value="${tr.trId}"/>
    <input type="hidden" name="word_from" value="${tr.wordFrom}"/>
    <input type="hidden" name="selected_from" value="${tr.langFrom}"/>
</form>