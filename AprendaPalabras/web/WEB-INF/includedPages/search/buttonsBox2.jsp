<ul>
    <c:if test="${displayReportAccepButton}">
        <li>
            <%@include  file="reportAccep_link.jsp" %> 
        </li>
    </c:if>
    <c:if test="${displayUnreportAccepButton}">
        <li>
            <%@include  file="unreportAccep_link.jsp" %> 
        </li>
    </c:if>

    <c:if test="${displayReportTrButton}">
        <li>
            <%@include  file="reportTr_link.jsp" %>
        </li>
    </c:if>
    <c:if test="${displayUnreportTrButton}">
        <li>
            <%@include  file="unreportTr_link.jsp" %>
        </li>
    </c:if>
</ul>