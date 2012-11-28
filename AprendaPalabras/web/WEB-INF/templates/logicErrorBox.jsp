<c:if test="${!empty logicError}">
    <div id="logicErrorBox">
        <span class="title">Error:</span>
        <span class="descr">
            <c:out value="${logicError}" escapeXml="false"/>
        </span>
    </div>
</c:if>