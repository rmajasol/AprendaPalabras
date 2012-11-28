<div id="resultBox">

    <%-- SI NO TIENE TRADUCCIONES --%>
    <c:if test="${empty trList}">
        ${word_from} no tiene traducción ${selected_from}->${selected_to}. 
    </c:if>


    <%-- LISTA DE TRADUCCIONES --%>
    <c:forEach var="tr" items="${trList}">

        <%@include file="variables.jsp" %>

        <div class="translation
             <c:if test="${tr.added}">
                 added
             </c:if>
             ">
            <div class="word">
                <c:out value="${tr.wordTo}"/>

                <%-- ACEPCIÓN SEGÚN SE HAYA ELEGIDO INVERTIR SU IDIOMA O NO --%>
                <c:set var="accep" value="${tr.accepFrom}"/>
                <c:if test="${sessionUser.invertAcceptations}">
                    <c:set var="accep" value="${tr.accepTo}"/>
                </c:if>
                <c:if test="${!empty accep}">
                    <span class="acceptation">(${accep})</span>
                </c:if>
            </div>


            <div class="dateAuthor littleFont">
                Creada 
                <c:if test="${!empty tr.userCreatorName}">por ${tr.userCreatorName}</c:if> 
                el <fmt:formatDate pattern="yyyy-MM-dd HH:mm" value="${tr.creationDate}"/>
            </div>


            <%@include file="addedCounter.jsp" %>


            <!-- CAJA DE BOTONES 1 (crear accep, crear context, agregar tr)-->
            <div class="buttonsBox">
                <%@include file="buttonsBox1.jsp" %>
            </div>

            <!-- CAJA DE BOTONES 2 (reportar accep y tr)-->
            <div class="buttonsBox">
                <%@include file="buttonsBox2.jsp" %>
            </div>


            <!-- FORMULARIOS (crear accep, crear contexto)-->
            <div id="trForms">
                <%@include file="createAccep_form.jsp" %>
                <%@include file="createContext_form.jsp" %>
            </div>


            <div class="contextList">
                <%@include file="context/contextList.jsp" %>
            </div>
        </div>
    </c:forEach>



</div>