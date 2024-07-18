package io.github.orcunbalcilar.gpost.teststep.request.auth

trait HasAuth {
    Auth auth

    void basicAuth(@DelegatesTo(value = BasicAuth, strategy = Closure.DELEGATE_ONLY) Closure closure) {
        auth = new BasicAuth()
        closure.delegate = auth
        closure.resolveStrategy = Closure.DELEGATE_ONLY
        closure.run()
    }
}