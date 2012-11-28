<tr class="field">
<table>
    <tr class="field">
        <td class="name">Password: </td>
        <td class="value">
            <input type="password" name="password"
                   <c:if test="${!empty errors.formErrors.password}">
                       class="error"
                   </c:if>
                   value="<c:out value="${errors.correctFields.password}"/>"/>
        </td>
    </tr>
    <tr class="field">
        <td class="name">Repita password: </td>
        <td class="value">
            <input type="password" name="password2"
                   <c:if test="${!empty errors.formErrors.password}">
                       class="error"
                   </c:if>
                   value="<c:out value="${errors.correctFields.password2}"/>"/>
        </td>
    </tr>
</table>
</tr> 

<c:if test="${!empty errors.formErrors.password}">
    <tr class="fieldErrorDesc">
        <td colspan="2">
            <c:out value="${errors.formErrors.password}"/>
        </td>
    </tr>
</c:if>