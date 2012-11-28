<div>
    <label>Password:</label>
    <input type="password" name="password"
           <c:if test="${!empty errors.formErrors.password}">
               class="error"
           </c:if>
           value="<c:out value="${errors.correctFields.password}"/>"/>
    <c:if test="${!empty errors.formErrors.password}">
        <span class="fieldErrorMsg"><c:out value="${errors.formErrors.password}"/></span>
    </c:if>
</div>