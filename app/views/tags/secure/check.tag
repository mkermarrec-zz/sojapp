#{if session.username && controllers.security.Secure.Security.invoke("check", _arg)}
    #{doBody /}
#{/if}