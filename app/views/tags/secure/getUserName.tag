#{if session.username}
    %{name = controllers.security.Secure.Security.invoke("getUserName")}%
    ${name}
#{/if}