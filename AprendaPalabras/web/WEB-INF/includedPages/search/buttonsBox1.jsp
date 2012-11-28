<ul>
    <c:if test="${empty tr.accepFrom || empty tr.accepTo}">
        <li>
            <%@include  file="createAccep_link.jsp" %> 
        </li>
    </c:if>

    <li>
        <%@include  file="createContext_link.jsp" %>
    </li>

    <li>
        <%@include file="addTr_link.jsp" %>
    </li>
</ul>