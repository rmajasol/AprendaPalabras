<c:set var="baseURL" value="config.htm"/>

<form id="form" class="config" action="${baseURL}" method="POST">

    <div class="formSection">
        <div>
            Idioma origen por defecto: 
            <jsp:include page="../templates/languageSelector_def.jsp">
                <jsp:param name="selectorName" value="def_lang_from"/>
                <jsp:param name="value" value="${def_lang_from}"/>
            </jsp:include>
        </div>
        <div>
            <input type="checkbox" name="hide_def_from" value="ON"
                   <c:if test="${sessionUser.hideDefLangFrom}">
                       CHECKED
                   </c:if>
                   /> Ocultar este idioma en las listas.
        </div>
    </div>
    <div class="formSection">
        <div>
            Idioma destino por defecto: 
            <jsp:include page="../templates/languageSelector_def.jsp">
                <jsp:param name="selectorName" value="def_lang_to"/>
                <jsp:param name="value" value="${def_lang_to}"/>
            </jsp:include>
        </div>
        <div>
            <input type="checkbox" name="hide_def_to" value="ON"
                   <c:if test="${sessionUser.hideDefLangTo}">
                       CHECKED
                   </c:if>
                   /> Ocultar este idioma en las listas.
        </div>
    </div>

    <div>
        <input type="checkbox" name="invert_accep_lang" value="ON"
               <c:if test="${sessionUser.invertAcceptations}">
                   CHECKED
               </c:if>
               /> Invertir idioma de la acepción.
        <jsp:include page="../templates/infoIcon.jsp">
            <jsp:param name="altText" 
                       value="Si se introducen traducciones donde el origen es un lenguaje no-nativo, como por ejemplo para inglés -> español la traducción 'jack -> gato', marcando esta casilla podemos indicarle a la aplicación que la acepción 'herramienta' para 'jack' se refiere al idioma destino y no el origen como viene configurado por defecto."/>
        </jsp:include>
    </div>

    <div>
        <input type="checkbox" name="auto_adding" value="ON"
               <c:if test="${sessionUser.autoAdding}">
                   CHECKED
               </c:if>
               /> Auto-añadir a mi lista personal lo que cree.
    </div>

    <div>
        <input type="checkbox" name="hide_username" value="ON"
               <c:if test="${sessionUser.hideUsername}">
                   CHECKED
               </c:if>
               /> Ocultar mi nombre de usuario para las contribuciones.
        <jsp:include page="../templates/infoIcon.jsp">
            <jsp:param name="altText" 
                       value="De esta forma nadie podrá ver tu nombre de usuario en las contribuciones, tanto en las realizadas como en las que en un futuro hicieras."/>
        </jsp:include>
    </div>


    <jsp:include page="../templates/submitButton.jsp">
        <jsp:param name="name" value="save_config_button"/>
        <jsp:param name="value" value="Guardar configuración"/>
    </jsp:include>
</form>
<div class="block">
    <a href="${baseURL}?action=unsuscribe">Darme de baja</a>
</div>