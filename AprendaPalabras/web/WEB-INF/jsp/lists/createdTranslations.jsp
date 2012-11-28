<c:set var="pageURL" value="?view=createdTrs"/>

<jsp:include page="../../includedPages/lists/menu.jsp"/>

<jsp:include page="../../templates/logicErrorBox.jsp"/>

<c:if test="${!empty deletedTr}">
    <div class="infoBox info">
        <form action="?view=createdTrs" method="POST">
            Se ha eliminado la traducción.  
            <a href="" onclick="
                parentNode.action += '&action=undoDel';
                parentNode.submit(); return false"
               >Deshacer</a>
            <input type="hidden" name="tr" value="${param.tr}"/>
        </form>
    </div>
</c:if>

<c:choose>
    <c:when test="${empty trList}">
        <div class="infoBox info">
            De momento no has creado ninguna traducción
        </div>
    </c:when>
    <c:otherwise>
        <%@include file="../../includedPages/lists/createdTranslationsList.jsp"%>
    </c:otherwise>
</c:choose>