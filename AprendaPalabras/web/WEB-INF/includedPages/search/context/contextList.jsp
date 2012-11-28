<%-- SI LA TRADUCCIÓN NO TIENE FRASES DE CONTEXTO 
<c:if test="${empty cList}">
    Esta traducción no tiene frases de contexto. 
</c:if> --%>

<%-- LISTA DE TRADUCCIONES --%>

<c:forEach var="c" items="${cList}">

    <%@include file="variables.jsp" %>

    <div class="context
         <c:if test="${c.reportedCount gt 0}">
             reported
         </c:if>
         ">

        <c:choose>
            <c:when test="${isEditingContext}">
                <%@include file="editContext_form.jsp" %>
            </c:when>

            <c:otherwise>
                <div class="phrase">
                    ${c.phraseFrom}
                </div>
                <div class="phrase">
                    ${c.phraseTo}
                </div>

                <div class="context_info">
                    <div>
                        Creado por <c:out value="${c.userCreatorName}"/> el 
                        <fmt:formatDate pattern="yyyy-MM-dd HH:mm" value="${c.creationDate}"/>

                        <%@include file="reportedContextCounter.jsp" %>
                    </div>

                    <%-- EDITAR / ELIMINAR / REPORTAR CONTEXTO --%>
                    <div class="contextAction">
                        <c:if test="${isContextCreatedByUser && !isEditingContext}"> 
                            <%@include file="editDelContext_links.jsp" %>
                        </c:if>

                        <%@include file="reportContext_link.jsp" %>
                    </div>
                </div>

            </c:otherwise>
        </c:choose>
    </div>


</c:forEach>