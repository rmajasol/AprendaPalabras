<c:set var="pageURL" value="?view=addedTrs"/>

<jsp:include page="../../includedPages/lists/menu.jsp"/>

<c:choose>
    <c:when test="${empty trList}">
        <div class="infoBox info">
            De momento no agregaste ninguna traducci�n a tu lista personal.
        </div>
    </c:when>
    <c:otherwise>
        <%@include file="../../includedPages/lists/addedTranslationsList.jsp"%>
    </c:otherwise>
</c:choose>