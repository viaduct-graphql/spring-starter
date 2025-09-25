package com.example.viadapp.rest

import graphql.ExecutionResult
import kotlinx.coroutines.future.await
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import viaduct.service.api.ExecutionInput
import viaduct.service.api.Viaduct

const val SCHEMA_ID = "publicSchema"

@RestController
class ViaductGraphQLController {
    @Autowired
    lateinit var viaduct: Viaduct

    @PostMapping("/graphql")
    suspend fun graphql(
        @RequestBody request: Map<String, Any>
    ): ResponseEntity<Map<String, Any>> {
        val result: ExecutionResult = run {
            @Suppress("UNCHECKED_CAST")
            val executionInput = ExecutionInput(
                query = request["query"] as String,
                variables = (request["variables"] as? Map<String, Any>) ?: emptyMap(),
                requestContext = object {},
                schemaId = SCHEMA_ID
            )
            viaduct.executeAsync(executionInput).await()
        }

        return when {
            // This handles the introspection query returning the GraphQL Schema
            request["operationName"] == "IntrospectionQuery" -> {
                ResponseEntity.ok(mapOf("data" to result.getData<Map<String, Any>>()))
            }

            else -> {
                ResponseEntity.status(statusCode(result)).body(result.toSpecification())
            }
        }
    }

    fun statusCode(result: ExecutionResult) =
        when {
            result.isDataPresent && result.errors.isNotEmpty() -> HttpStatus.BAD_REQUEST
            else -> HttpStatus.OK
        }
}
