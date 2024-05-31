package nz.ac.auckland.se281;

import java.util.Map;
import java.util.Queue;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

/** This class is the main entry point. */
public class MapEngine {

  private Map<String, Country> countries = new HashMap<>();
  private Map<String, List<String>> graph = new HashMap<>();

  public MapEngine() {
    // add other code here if you want
    loadMap(); // keep this mehtod invocation
  }

  /** invoked one time only when constracting the MapEngine class. */
  private void loadMap() {
    List<String> countries = Utils.readCountries();
    List<String> adjacencies = Utils.readAdjacencies();

    for (String line : countries) {
      String[] parts = line.split(",");
      this.countries.put(parts[0], new Country(parts[0], parts[1], Integer.parseInt(parts[2])));
      graph.putIfAbsent(parts[0], new ArrayList<>());
    }

    for (String line : adjacencies) {
      String[] parts = line.split(",");
      for (int i = 1; i < parts.length; i++) {
        graph.get(parts[0]).add(parts[i]);
      }
    }
  }

  public boolean isCountry(String country) throws CountryNotFoundException {
    if (countries.containsKey(country)) {
      return true;
    }
    throw new CountryNotFoundException(country);
  }

  /** this method is invoked when the user run the command info-country. */
  public void showInfoCountry() {
    while (true) {
      String countryName = Utils.scanner.nextLine();
      countryName = Utils.capitalizeFirstLetterOfEachWord(countryName);
      try {
        if (isCountry(countryName)) {
          Country country = countries.get(countryName);
          MessageCli.COUNTRY_INFO.printMessage(country.getName(), country.getContinent(),
              String.valueOf(country.getTax()));
          break;
        }
      } catch (CountryNotFoundException e) {
        MessageCli.INVALID_COUNTRY.printMessage(countryName);
      }
    }
  }

  public List<String> breathFirstTraversal(String root, String target) {
    Queue<String> queue = new LinkedList<>();
    Map<String, List<String>> routes = new HashMap<>();
    queue.add(root);
    routes.put(root, new ArrayList<>());
    routes.get(root).add(root);
    while (!queue.isEmpty()) {
      String node = queue.poll();
      for (String country : graph.get(node)) {
        if (!routes.containsKey(country)) {
          queue.add(country);
          routes.put(country, new ArrayList<>(routes.get(node)));
          routes.get(country).add(country);
        }
        if (country.equals(target)) {
          return routes.get(target);
        }
      }
    }
    return null;
  }

  /** this method is invoked when the user run the command route. */
  public void showRoute() {
    Country startCountry;
    while (true) {
      String startCountryName = Utils.scanner.nextLine();
      startCountryName = Utils.capitalizeFirstLetterOfEachWord(startCountryName);
      try {
        if (isCountry(startCountryName)) {
          startCountry = countries.get(startCountryName);
          break;
        }
      } catch (CountryNotFoundException e) {
        MessageCli.INVALID_COUNTRY.printMessage(startCountryName);
      }
    }

    Country endCountry;
    while (true) {
      String endCountryName = Utils.scanner.nextLine();
      endCountryName = Utils.capitalizeFirstLetterOfEachWord(endCountryName);
      try {
        if (isCountry(endCountryName)) {
          endCountry = countries.get(endCountryName);
          break;
        }
      } catch (CountryNotFoundException e) {
        MessageCli.INVALID_COUNTRY.printMessage(endCountryName);
      }
    }

    if (startCountry.equals(endCountry)) {
      MessageCli.NO_CROSSBORDER_TRAVEL.printMessage();
    } else {
      List<String> route = breathFirstTraversal(startCountry.getName(), endCountry.getName());
      MessageCli.ROUTE_INFO.printMessage(route.toString());
      List<String> continents = new ArrayList<>();
      int taxTotal = -startCountry.getTax();
      for (String country : route) {
        String continent = countries.get(country).getContinent();
        if (!continents.contains(continent)) {
          continents.add(continent);
        }
        taxTotal += countries.get(country).getTax();
      }
      MessageCli.CONTINENT_INFO.printMessage(continents.toString());
      MessageCli.TAX_INFO.printMessage(String.valueOf(Integer.toString(taxTotal)));
    }
  }
}
