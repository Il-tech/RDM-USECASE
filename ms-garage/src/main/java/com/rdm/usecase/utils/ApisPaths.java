package com.rdm.usecase.utils;

public interface ApisPaths {

  interface Base {
    String API = "/api";
  }

  interface Accessory {
    String ACCESSORY_PATH = Base.API + "/accessories";
  }

  interface Garage {
    String GARAGE_PATH = Base.API + "/garages";
    String BY_ID = "/{id}";
  }

  interface Vehicle {
    String BY_ID = "/{id}";
    String ADD = "/add";
  }

  interface RabbitMQ {
    public static final String VEHICLE_EXCHANGE = "vehicle.exchange";
    public static final String VEHICLE_ROUTING_KEY = "vehicle.created";
    public static final String VEHICLE_QUEUE = "vehicle.queue";
  }
}
