<div id="content">
    <div id="searchBox"
         <c:if test="${errors}">
             class="fieldError"
         </c:if>
         >            
        <%-- hay que usar el @include ya que con jsp:include no se propaga fieldsOk y demás--%>
        <%@include file="search/translationSelection.jsp"%>
    </div>
    <div id="resultBox">

        <c:forEach var="tr" items="${trList}">
            <div class="element">
                <c:out value="${tr.word}"/>
                <c:out value="${tr.creator}"/>
                <c:out value="${tr.creationDate}"/>
            </div>
        </c:forEach>


    </div>
</div>
