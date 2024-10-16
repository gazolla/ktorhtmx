package com.example.plugins

import io.ktor.http.HttpStatusCode
import io.ktor.server.application.*
import io.ktor.server.http.content.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.server.thymeleaf.Thymeleaf
import io.ktor.server.thymeleaf.ThymeleafContent
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver
import io.ktor.server.plugins.statuspages.*

fun Application.configureRouting() {
    // Configura o Thymeleaf
    install(Thymeleaf) {
        setTemplateResolver(ClassLoaderTemplateResolver().apply {
            prefix = "templates/"  // Onde os templates Thymeleaf estarão
            suffix = ".html"       // Sufixo dos arquivos
            characterEncoding = "utf-8"
        })
    }

    install(StatusPages) {
        status(HttpStatusCode.NotFound) { call, _ ->
            if (call.request.headers["HX-Request"] == "true") {
                call.respond(ThymeleafContent("404", mapOf("title" to "Página não encontrada")))
            } else {
                call.respond(ThymeleafContent("layout", mapOf("content" to "404", "title" to "Página não encontrada")))
            }
        }
        exception<Throwable> { call, cause ->
            call.application.log.error("Erro interno do servidor", cause)
            val status = HttpStatusCode.InternalServerError
            if (call.request.headers["HX-Request"] == "true") {
                call.respond(status, ThymeleafContent("500", mapOf("title" to "Erro interno do servidor")))
            } else {
                call.respond(status, ThymeleafContent("layout", mapOf("content" to "500", "title" to "Erro interno do servidor")))
            }
        }  
    }



    routing {
        get("/") {
            if (call.request.headers["HX-Request"] == "true") {
                call.respond(ThymeleafContent("index", mapOf("title" to "Página Inicial")))
            } else {
                call.respond(ThymeleafContent("layout", mapOf("content" to "index", "title" to "Página Inicial")))
            }
        }

        get("/about") {
            if (call.request.headers["HX-Request"] == "true") {
                call.respond(ThymeleafContent("about", mapOf("title" to "Sobre Nós")))
            } else {
                call.respond(ThymeleafContent("layout", mapOf("content" to "about", "title" to "Sobre Nós")))
            }
        }

        get("/services") {
            if (call.request.headers["HX-Request"] == "true") {
                call.respond(ThymeleafContent("services", mapOf("title" to "Serviços")))
            } else {
                call.respond(ThymeleafContent("layout", mapOf("content" to "services", "title" to "Serviços")))
            }
        }

        get("/contact") {
            if (call.request.headers["HX-Request"] == "true") {
                call.respond(ThymeleafContent("contact", mapOf("title" to "Contato")))
            } else {
                call.respond(ThymeleafContent("layout", mapOf("content" to "contact", "title" to "Contato")))
            }
        }

        get("/blog") {
            if (call.request.headers["HX-Request"] == "true") {
                call.respond(ThymeleafContent("blog", mapOf("title" to "Blog")))
            } else {
                call.respond(ThymeleafContent("layout", mapOf("content" to "blog", "title" to "Blog")))
            }
        }
        // Arquivos estáticos
        staticResources("/static", "static")
    }
}
