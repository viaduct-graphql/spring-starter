package com.example.viadapp

import com.example.viadapp.resolvers.resolverbases.QueryResolvers
import org.springframework.stereotype.Component
import viaduct.api.Resolver

@Component
@Resolver
class ThrowExceptionResolver : QueryResolvers.ThrowException() {
    override suspend fun resolve(ctx: Context): String {
        throw IllegalStateException("This is a resolver error")
    }
}
