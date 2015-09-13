#{if session.username && controllers.security.Secure.Security.invoke("checkAdmin")}
    #{doBody /}
#{/if}