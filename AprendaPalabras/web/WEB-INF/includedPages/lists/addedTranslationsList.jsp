<table class="sortable">
    <thead>
        <tr>
            <th></th>
            <c:if test="${!hasAllDefAndHidden}">
                <th>DE:</th>
                <th>A:</th>
            </c:if>
            <th></th>
            <th></th>
            <th>Agregada</th>
            <th>Aprendida</th>
        </tr>
    </thead>

    <tbody>
        <c:forEach var="tr" items="${trList}">
            <tr
                <c:if test="${tr.learned}">
                    class="learned"
                </c:if>
                >
                <td>
                    <span class="word">${tr.wordFrom}</span>
                    <c:if test="${!empty tr.accepFrom}">
                        <span class="acceptation">(${tr.accepFrom})</span>
                    </c:if>
                </td>

                <%@include file="../../includedPages/lists/pairLangFromTo.jsp" %>

                <td>
                    <span class="word">${tr.wordTo}</span>
                    <c:if test="${!empty tr.accepTo}">
                        <span class="acceptation">(${tr.accepTo})</span>
                    </c:if>
                </td>

                <td>
                    <%@include file="../../includedPages/lists/viewContextsCell.jsp" %>
                </td>

                <td>
                    <fmt:formatDate pattern="yyyy-MM-dd hh:mm" value="${tr.dateAdded}"/> 
                </td>

                <td class="mark">
                    <form action="${pageURL}" method="POST">
                        <c:if test="${tr.learned}">
                            <a href="" 
                               onclick="saveScroll();parentNode.submit(); return false"
                               >SI</a>
                            <input type="hidden" name="learned" value="no"/>
                        </c:if>
                        <c:if test="${!tr.learned}">
                            <a href="" 
                               onclick="saveScroll();parentNode.submit(); return false"
                               >NO</a>
                            <input type="hidden" name="learned" value="yes"/>
                        </c:if>
                        <input type="hidden" name="uht" value="${tr.uhtId}"/>
                    </form>
                </td>

            </tr>

            <%-- FRASES DE CONTEXTO --%>
            <%@include file="../../includedPages/lists/contextsRow.jsp" %>

        </c:forEach>
    </tbody>
</table>