package com.example.viadapp

import com.example.viadapp.resolvers.resolverbases.QueryResolvers
import org.springframework.stereotype.Component
import viaduct.api.Resolver

@Component
@Resolver
class HelloWorldResolver : QueryResolvers.Greeting() {
    override suspend fun resolve(ctx: Context): String {
        return "Hello, World!"
    }
}

@Component
@Resolver
class AuthorResolver : QueryResolvers.Author() {
    override suspend fun resolve(ctx: Context): String {
        return "Brian Kernighan"
    }
}
