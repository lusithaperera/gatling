package com.pet_store.pet;

import static io.gatling.javaapi.core.CoreDsl.*;
import static io.gatling.javaapi.http.HttpDsl.*;

import io.gatling.javaapi.core.ChainBuilder;

public class petAPI {

  public static ChainBuilder createPet() {
    String transaction = "Create Pet";
    return exec(
        http(transaction)
            .post("/pet")
            .header("x-dynaTrace", "NA=" + transaction + ";")
            .header("x-request-id", "#{request-id}")
            .body(ElFileBody("bodies/new_pet.json")).asJson()
            .check(status().is(200))
            .check(jsonPath("$.id").saveAs("petId"))
    );
  }

  public static ChainBuilder getPetById() {
    String transaction = "Get Pet By Id";
    return exec(
        http(transaction)
            .get("/pet/#{petId}")
            .header("x-dynaTrace", "NA=" + transaction + ";")
            .check(status().is(200))
            .check(jsonPath("$.name").is("#{petName}"))
    );
  }

  public static ChainBuilder findByStatus() {
    String transaction = "Find Pets By Status";
    return exec(
        http(transaction)
            .get("/pet/findByStatus")
            .queryParam("status", "#{status}")
            .header("x-dynaTrace", "NA=" + transaction + ";")
            .check(status().is(200))
    );
  }

  public static ChainBuilder deletePet() {
    String transaction = "Delete Pet";
    return exec(
        http(transaction)
            .delete("/pet/#{petId}")
            .header("x-dynaTrace", "NA=" + transaction + ";")
            .check(status().in(200, 202, 204, 404))
    );
  }
}
