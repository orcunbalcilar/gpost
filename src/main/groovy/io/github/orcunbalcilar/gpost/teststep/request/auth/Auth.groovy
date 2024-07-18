package io.github.orcunbalcilar.gpost.teststep.request.auth

import org.apache.hc.client5.http.auth.Credentials

abstract class Auth {
    abstract Credentials getCredentials()
}
