<form action="search.htm" method="POST">


    <c:if test="${!tr.added}">
        <c:if test="${tr.addedCount ne 0}">
            Agregada 
            <c:if test="${tr.addedCount eq 1}">por 1 usuario</c:if>
            <c:if test="${tr.addedCount gt 1}">por ${tr.addedCount} usuarios</c:if>
        </c:if>
        <a href="" 
           onclick="saveScroll();parentNode.submit(); return false"
           >Agregar</a>
        <input type="hidden" name="action" value="addTr"/>
    </c:if>


    <c:if test="${tr.added}">
        <c:if test="${tr.addedCount ne 0}">
            Agregada 
            <c:if test="${tr.addedCount eq 1}">sólo por tí</c:if>
            <c:if test="${tr.addedCount eq 2}">por tí y por 1 usuario más</c:if>
            <c:if test="${tr.addedCount gt 2}">por tí y por ${tr.addedCount-1} usuarios más</c:if>
        </c:if>
        (<a href="" 
            onclick="saveScroll();parentNode.submit(); return false"
            >Quitar de mi lista</a>)
        <input type="hidden" name="action" value="remTr"/>
    </c:if>


    <input type="hidden" name="tr" value="${tr.trId}"/>
    <input type="hidden" name="word_from" value="${word_from}"/>
    <input type="hidden" name="selected_from" value="${selected_from}"/>
</form>


<div>
    Creada 
    <c:if test="${!empty tr.userCreatorName}">por ${tr.userCreatorName}</c:if> 
    el <fmt:formatDate pattern="yyyy-MM-dd HH:mm" value="${tr.creationDate}"/>
</div>


