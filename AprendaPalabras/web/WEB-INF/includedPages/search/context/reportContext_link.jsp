<c:set var="isContextReportedByUser" value="${c.reported}"/>

<c:if test="${!isContextCreatedByUser}">
    <form action="search.htm" method="POST">
        <c:choose>
            <c:when test="${!isContextReportedByUser}">
                <a href="" 
                   onclick="
                       saveScroll();
                       parentNode.submit(); return false"
                   >Reportar</a>
                <input type="hidden" name="action" value="reportContext"/>
            </c:when>
            <c:otherwise>
                <a href="" 
                   onclick="
                       saveScroll();
                       parentNode.submit(); return false"
                   >Quitar reporte</a>
                <input type="hidden" name="action" value="unreportContext"/>
            </c:otherwise>
        </c:choose>

        <input type="hidden" name="tr" value="${tr.trId}"/>
        <input type="hidden" name="c" value="${c.id}"/>
        <input type="hidden" name="cUserCreatorId" value="${c.userCreatorId}"/>
        <input type="hidden" name="word_from" value="${tr.wordFrom}"/>
        <input type="hidden" name="selected_from" value="${tr.langFrom}"/>

    </form>
</c:if>
