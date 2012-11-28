<c:if test="${c.reportedCount ne 0}">

    <div class="reportedCounter">
        Reportado por 
        <c:if test="${!isContextReportedByUser}">
            <c:if test="${c.reportedCount eq 1}">1 usuario</c:if>
            <c:if test="${c.reportedCount gt 2}">${c.reportedCount} usuarios</c:if>
        </c:if>

        <c:if test="${isContextReportedByUser}">
            <c:if test="${c.reportedCount eq 1}">tí</c:if>
            <c:if test="${c.reportedCount gt 1}">tí y 
                <c:if test="${c.reportedCount eq 2}">1 usuario más</c:if>
                <c:if test="${c.reportedCount gt 2}">${c.reportedCount} usuarios más</c:if>
            </c:if>
        </c:if>
    </div>
    
</c:if>