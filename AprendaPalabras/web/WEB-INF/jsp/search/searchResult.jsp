<jsp:include page="../../includedPages/search/createTr_form.jsp"/>


<c:if test="${!empty trCreated_msg}">
    <div class="infoBox result_ok">
        Traducci�n creada con �xito
    </div>
</c:if>


<%-- LISTA DE TRADUCCIONES --%>
<jsp:include page="../../includedPages/search/translationList.jsp"/>