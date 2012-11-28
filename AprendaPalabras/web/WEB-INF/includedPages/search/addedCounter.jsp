<c:if test="${tr.addedCount ne 0}">
    <div class="addedCounter">
        Agregada 
        <c:if test="${!tr.added}"> 
            <c:if test="${tr.addedCount eq 1}">por 1 usuario</c:if>
            <c:if test="${tr.addedCount gt 1}">por ${tr.addedCount} usuarios</c:if>
        </c:if>
        <c:if test="${tr.added}">
            <c:if test="${tr.addedCount eq 1}">sólo por tí</c:if>
            <c:if test="${tr.addedCount eq 2}">por tí y por 1 usuario más</c:if>
            <c:if test="${tr.addedCount gt 2}">por tí y por ${tr.addedCount-1} usuarios más</c:if>
        </c:if>
    </div>
</c:if>