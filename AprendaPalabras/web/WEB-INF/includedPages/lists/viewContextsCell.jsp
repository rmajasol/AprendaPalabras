<c:if test="${!empty tr.contextList && empty param.viewContextsId}">
    <form action="${pageURL}" method="POST">
        <a href="" 
           onclick="saveScroll();parentNode.submit(); return false"
           >Ver frases</a>
        <input type="hidden" name="viewContextsId" value="${tr.trId}"/>
    </form>
</c:if>

<c:if test="${param.viewContextsId eq tr.trId}">
    <form action="${pageURL}" method="POST">
        <a href="" 
           onclick="saveScroll();parentNode.submit(); return false"
           >Ocultar</a>
    </form>
</c:if>