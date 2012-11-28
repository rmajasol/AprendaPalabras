<c:if test="${!hasAllDefAndHidden}">
    <td>
        <c:if test="${!(sessionUser.hideDefLangFrom && tr.langFrom eq def_lang_from)}">
            ${tr.langFrom}
        </c:if>
    </td>

    <td>
        <c:if test="${!(sessionUser.hideDefLangTo && tr.langTo eq def_lang_to)}">
            ${tr.langTo}
        </c:if>
    </td>
</c:if>