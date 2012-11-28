<c:set var="isAccepLinkPressed" 
       value="${param.action eq 'createAccep' && tr.trId eq param.tr}"/>
<c:set var="hasAccepFormErrors" 
       value="${(!empty create_accep_button && errors) && tr.trId eq param.tr}"/>
<c:set var="isCreatingAccep"
       value="${isAccepLinkPressed || hasAccepFormErrors}"/>

<c:set var="isContextLinkPressed" 
       value="${param.action eq 'createContext' && tr.trId eq param.tr}"/>
<c:set var="hasContextFormErrors" 
       value="${(!empty create_context_button && errors) && tr.trId eq param.tr}"/>
<c:set var="isCreatingContext"
       value="${isContextLinkPressed || hasContextFormErrors}"/>

<c:set var="cList" value="${tr.contextList}"/>

<c:set var="hasAcceptation" value="${!empty tr.accepFrom}"/>
<c:set var="isAccepCreatedByUser" value="${tr.accepData.createdByUser}"/>
<c:set var="isAccepReportedByUser" value="${tr.accepData.reported}"/>
<c:set var="displayReportAccepButton"
       value="${hasAcceptation && !isAccepCreatedByUser && !isAccepReportedByUser}"/>
<c:set var="displayUnreportAccepButton"
       value="${hasAcceptation && !isAccepCreatedByUser && isAccepReportedByUser}"/>

<c:set var="isTrCreatedByUser" value="${tr.createdByUser}"/>
<c:set var="isTrReportedByUser" value="${tr.reported}"/>
<c:set var="displayReportTrButton"
       value="${!isTrCreatedByUser && !isTrReportedByUser}"/>
<c:set var="displayUnreportTrButton"
       value="${!isTrCreatedByUser && isTrReportedByUser}"/>
