<c:if test="${param.viewContextsId eq tr.trId}">
    <tr class="colspanAll">
        <td colspan="100%">
            <div class="contextList">
                <c:forEach var="c" items="${tr.contextList}">
                    <div class="context">
                        <div class="phrase">
                            ${c.phraseFrom}
                        </div>
                        <div class="phrase">
                            ${c.phraseTo}
                        </div>
                    </div>
                </c:forEach>
            </div>
        </td>
    </tr>
</c:if>