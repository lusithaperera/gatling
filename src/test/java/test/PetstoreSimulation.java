package example;

import static io.gatling.javaapi.core.CoreDsl.*;
import static io.gatling.javaapi.http.HttpDsl.*;

import io.gatling.javaapi.core.*;
import io.gatling.javaapi.http.*;

import java.time.Instant;
import java.util.Iterator;
import java.util.Map;
import java.util.UUID;
import java.util.function.Supplier;
import java.util.stream.Stream;
import com.pet_store.pet.petAPI;

public class PetstoreSimulation extends Simulation {

  private static final int vus = Integer.getInteger("vus", 2);
  private static final int rampSeconds = Integer.getInteger("ramp", 2);

  private static final HttpProtocolBuilder httpProtocol = http
      .baseUrl("https://petstore.swagger.io/v2")
      .acceptHeader("application/json")
      .contentTypeHeader("application/json")
      .userAgentHeader("gatling-java-petstore");

  public static Iterator<Map<String, Object>> petFeeder =
      Stream.generate((Supplier<Map<String, Object>>) () -> {
        String uniqueSuffix = String.valueOf(Instant.now().toEpochMilli());
        String petName = "dog_" + uniqueSuffix;
        String status = "available";
        String requestId = UUID.randomUUID().toString();
        return Map.of(
            "petName", petName,
            "status", status,
            "request_id", requestId
        );
      }).iterator();

  public static ChainBuilder createPet() { return petAPI.createPet(); }
  public static ChainBuilder getPetById() { return petAPI.getPetById(); }
  public static ChainBuilder findByStatus() { return petAPI.findByStatus(); }
  public static ChainBuilder deletePet() { return petAPI.deletePet(); }

  private static final ScenarioBuilder scn = scenario("Petstore Scenario")
      .feed(petFeeder)
      .exec(createPet())
      .exec(getPetById())
      .exec(findByStatus())
      .exec(deletePet());

  {
    setUp(
        scn.injectOpen(rampUsers(vus).during(rampSeconds))
    ).protocols(httpProtocol);
  }
}
