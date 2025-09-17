package viaduct.demoapp.resolvers

import viaduct.api.TenantModule

class HelloWorldTenantModule : TenantModule {
    override val metadata = mapOf(
        "name" to "HelloWorldTenantModule"
    )
}
