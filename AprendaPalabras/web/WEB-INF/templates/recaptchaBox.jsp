<div id="recaptcha"
     <c:if test="${!empty formErrors.recaptcha}">
         class="fieldError"
     </c:if>
     >
    <script type="text/javascript">
        var RecaptchaOptions = {
            lang : 'es',
            theme : 'white'
        };
    </script>
    <script type="text/javascript"
            src="http://api.recaptcha.net/challenge?k=6Lcq384SAAAAAJNbXJODHihOZ9viik5OL6e_b2ra">
    </script>
    <noscript>
    <iframe src="http://api.recaptcha.net/noscript?k=6Lcq384SAAAAAJNbXJODHihOZ9viik5OL6e_b2ra"
            height="300" width="500" frameborder="0"></iframe>
    <textarea name="recaptcha_challenge_field" rows="3" cols="40">
    </textarea>
    <input type="hidden" name="recaptcha_response_field" 
           value="manual_challenge">
    </noscript>
    <c:if test="${!empty formErrors.recaptcha}">
        <jsp:include page="fieldErrorMsg.jsp">
            <jsp:param name="error" value="${formErrors.recaptcha}"/>
        </jsp:include>
    </c:if>
</div>

