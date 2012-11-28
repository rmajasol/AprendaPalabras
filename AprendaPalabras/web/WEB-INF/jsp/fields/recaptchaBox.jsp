<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<div>
    <script type="text/javascript"
            src="http://api.recaptcha.net/challenge?k=6Lcq384SAAAAAJNbXJODHihOZ9viik5OL6e_b2ra">
    </script>
    <noscript>
    <iframe src="http://api.recaptcha.net/noscript?k=6Lcq384SAAAAAJNbXJODHihOZ9viik5OL6e_b2ra"
            height="300" width="500" frameborder="0"></iframe><br>
    <textarea name="recaptcha_challenge_field" rows="3" cols="40">
    </textarea>
    <input type="hidden" name="recaptcha_response_field" 
           value="manual_challenge">
    </noscript>

    <c:if test="${!empty param.error}">
        <span class="fieldErrorMsg"><c:out value="${param.error}"/></span>
    </c:if>
</div>
