<c:set var="isEditContextLinkPressed" 
       value="${param.action eq 'editContext' && c.id eq param.c}"/>
<c:set var="hasEditContextFormErrors" 
       value="${(!empty edit_context_button && errors) && c.id eq param.c}"/>

<c:set var="isEditingContext" 
       value="${isEditContextLinkPressed || hasEditContextFormErrors}"/>
<c:set var="isContextCreatedByUser" value="${sessionUser.id eq c.userCreatorId}"/>

<c:set var="isContextReportedByUser" value="${c.reported}"/>

