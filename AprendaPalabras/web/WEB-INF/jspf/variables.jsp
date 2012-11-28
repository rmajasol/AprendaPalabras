<c:set var="formErrors" value="${errors.formErrors}"/>
<c:set var="logicError" value="${errors.logicError}"/>
<c:set var="noErrors" value="${empty formErrors && empty logicError}"/>
<c:set var="errors" value="${!empty formErrors || !empty logicError}"/>