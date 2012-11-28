<div>
    <label>Username:</label>
    <input type="text" name="username"
           <c:if test="${!empty errors.formErrors.username}">
               class="error"
           </c:if>
           value="<c:out value="${errors.correctFields.username}"/>"/>
    <c:if test="${!empty errors.formErrors.username}">
        <span class="fieldErrorMsg"><c:out value="${errors.formErrors.username}"/></span>
    </c:if>
</div>

<!--</td>
</tr>

<%--<c:if test="${!empty errors.formErrors.username}">--%>
    <tr class="fieldErrorDesc">
        <td colspan="2">
            
        </td>
    </tr>
<%--</c:if>--%>
</table>-->