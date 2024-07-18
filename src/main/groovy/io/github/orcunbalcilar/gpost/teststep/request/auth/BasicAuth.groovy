package io.github.orcunbalcilar.gpost.teststep.request.auth

import org.apache.hc.client5.http.auth.Credentials
import org.apache.hc.client5.http.auth.UsernamePasswordCredentials

class BasicAuth extends Auth {
    private String username
    private String password

    void username(String username) {
        this.username = username
    }

    void password(String password) {
        this.password = password
    }

    @Override
    Credentials getCredentials() {
        new UsernamePasswordCredentials(username, password.toCharArray())
    }
}
