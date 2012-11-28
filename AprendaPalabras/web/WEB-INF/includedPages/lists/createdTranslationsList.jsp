<table class="sortable">
    <thead>
        <tr>
            <th></th>
            <c:if test="${!hasAllDefAndHidden}">
                <th>De:</th>
                <th>A:</th>
            </c:if>
            <th></th>
            <th></th>
            <th>Creada</th>
            <th>Acción</th>
        </tr>
    </thead>

    <tbody>
        <c:forEach var="tr" items="${trList}">

            <c:set var="rowIsBeingEditing" value="${param.action eq 'edit' && param.tr eq tr.trId
                                                    || ((!empty edit_tr_button && errors) && param.tr eq tr.trId)}"/>

            <tr
                <c:if test="${tr.trId eq param.tr}">
                    class="editing"
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

                <%-- LINK PARA VER CONTEXTOS --%>
                <td>
                    <%@include file="viewContextsCell.jsp" %>
                </td>

                <td>
                    <fmt:formatDate pattern="yyyy-MM-dd HH:mm" value="${tr.creationDate}"/>
                </td>
                <td class="mark">
                    <form action="${pageURL}" method="POST">
                        <c:if test="${!rowIsBeingEditing}">
                            <a href="" 
                               onclick="
                                   saveScroll();
                                   parentNode.action += '&action=edit';
                                   parentNode.submit(); return false"
                               >editar</a> |
                        </c:if>
                        <a href="" 
                           onclick="
                               saveScroll();
                               parentNode.action += '&action=del';
                               parentNode.submit(); return false"
                           >elim.</a>
                        <input type="hidden" name="tr" value="${tr.trId}"/>
                    </form>
                </td>
            </tr>
            <c:if test="${rowIsBeingEditing}">
                <tr class="colspanAll">
                    <td colspan="100%">
                        <%@include file="editForm.jsp" %>
                    </td>
                </tr>
            </c:if>

            <%@include file="contextsRow.jsp" %>

        </c:forEach>
    </tbody>
</table>