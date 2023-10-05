package de.bs1.landshut.device.util.javalin;

import io.javalin.security.RouteRole;

public enum AuthenticationLevel implements RouteRole {

  USER,
  ADMIN

}
