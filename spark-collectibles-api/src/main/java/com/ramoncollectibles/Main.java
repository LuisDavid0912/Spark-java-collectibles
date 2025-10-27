package com.ramoncollectibles;

import com.google.gson.Gson; 
import com.ramoncollectibles.model.User;
import com.ramoncollectibles.service.UserService;

import static spark.Spark.after;
import static spark.Spark.delete;
import static spark.Spark.exception;
import static spark.Spark.get;
import static spark.Spark.options;
import static spark.Spark.port;
import static spark.Spark.post;
import static spark.Spark.put;

public class Main {

    private static UserService userService = new UserService();
    private static Gson gson = new Gson();

    public static void main(String[] args) {

        port(8080); 

        System.out.println("Spark server starting...");


        get("/users", (req, res) -> {
            res.type("application/json"); // Indicamos que la respuesta es JSON
            return userService.getAllUsers();
        }, gson::toJson); // Usamos Gson para convertir la lista a JSON

        // GET /users/:id — Retrieve a user by the given ID
        get("/users/:id", (req, res) -> {
            res.type("application/json");
            String id = req.params(":id"); // Obtenemos el ID de la URL
            User user = userService.getUserById(id);
            if (user != null) {
                return user;
            } else {
                res.status(404); // Not Found
                // Devolvemos un JSON de error estándar
                return "{\"message\":\"User with ID '" + id + "' not found\"}";
            }
        }, gson::toJson);

        // POST /users — Add a user
        post("/users", (req, res) -> {
            res.type("application/json");
            // Convertimos el JSON del cuerpo de la petición a un objeto User
            User newUser = gson.fromJson(req.body(), User.class);
            // Validacion simple (podría mejorarse en un proyecto real)
            if (newUser.getName() == null || newUser.getEmail() == null) {
                 res.status(400); // Bad Request
                 return "{\"message\":\"Missing name or email in request body\"}";
            }
            User createdUser = userService.addUser(newUser);
            res.status(201); // Created
            return createdUser;
        }, gson::toJson);

        // PUT /users/:id — Edit a specific user
        put("/users/:id", (req, res) -> {
            res.type("application/json");
            String id = req.params(":id");
            User updatedInfo = gson.fromJson(req.body(), User.class);
             // Validacion simple
            if (updatedInfo.getName() == null || updatedInfo.getEmail() == null) {
                 res.status(400); // Bad Request
                 return "{\"message\":\"Missing name or email in request body\"}";
            }
            User updatedUser = userService.updateUser(id, updatedInfo);
            if (updatedUser != null) {
                return updatedUser;
            } else {
                res.status(404);
                return "{\"message\":\"User with ID '" + id + "' not found for update\"}";
            }
        }, gson::toJson);

        // OPTIONS /users/:id — Check whether a user with the given ID exists
        options("/users/:id", (req, res) -> {
            String id = req.params(":id");
            if (userService.userExists(id)) {
                res.status(200); // OK
                // Puedes devolver cabeceras Allow si quieres ser más específico,
                // pero un 200 OK es suficiente para indicar existencia.
                return "OK";
            } else {
                res.status(404); // Not Found
                return "Not Found";
            }
        });

        // DELETE /users/:id — Delete a specific user
        delete("/users/:id", (req, res) -> {
            res.type("application/json"); // Aunque no devolvemos cuerpo, ayuda a la consistencia
            String id = req.params(":id");
            if (userService.deleteUser(id)) {
                res.status(204); // No Content (éxito en borrado)
                return ""; // No devolvemos cuerpo en DELETE exitoso
            } else {
                res.status(404);
                return "{\"message\":\"User with ID '" + id + "' not found for deletion\"}";
            }
        });

        // --- Manejo de Excepciones y Filtros ---

        // Configuración para que las excepciones devuelvan JSON (simple)
        exception(Exception.class, (exception, req, res) -> {
            res.status(500); // Internal Server Error
            res.type("application/json");
            // Incluye un mensaje más útil en el JSON de error
            res.body("{\"message\":\"An internal server error occurred\", \"error\":\"" + exception.getMessage() + "\"}");
            System.err.println("Error processing request: " + req.pathInfo());
            exception.printStackTrace(); // Imprime el stack trace completo en la consola para debugging
        });

        // Filtro 'after' para asegurar Content-Type JSON en respuestas de /users
        after((req, res) -> {
            // Solo aplica si la ruta empieza con /users y no se ha establecido un tipo ya
            if (req.pathInfo().startsWith("/users") && res.type() == null) {
                 res.type("application/json");
            }
        });

        System.out.println("Spark routes defined. Server running on http://localhost:8080");
    }
}