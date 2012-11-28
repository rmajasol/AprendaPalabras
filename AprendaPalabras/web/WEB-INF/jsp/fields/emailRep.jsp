<tr class="field">
<table>
    <tr class="field">
        <td class="name">Email: </td>
        <td class="value">
            <input type="text" name="email"
                   <c:if test="${!empty errors.formErrors.email}">
                       class="error"
                   </c:if>
                   value="<c:out value="${errors.correctFields.email}"/>"/>
        </td>
    </tr>
    <tr class="field">
        <td class="name">Repita email: </td>
        <td class="value">
            <input type="text" name="email2"
                   <c:if test="${!empty errors.formErrors.email2}">
                       class="error"
                   </c:if>
                   value="<c:out value="${errors.correctFields.email2}"/>"/>
        </td>
    </tr>
</table>
</tr> 

<c:if test="${!empty errors.formErrors.email}">
    <tr class="fieldErrorDesc">
        <td colspan="2">
            <c:out value="${errors.formErrors.email}"/>
        </td>
    </tr>
</c:if>