package nz.ac.auckland.se281;

public class Country {
  private String name;
  private String continent;
  private int tax;

  public Country(String name, String continent, int tax) {
    this.name = name;
    this.continent = continent;
    this.tax = tax;
  }

  public String getName() {
    return name;
  }

  public String getContinent() {
    return continent;
  }

  public int getTax() {
    return tax;
  }

  public void setName(String name) {
    this.name = name;
  }

  public void setContinent(String continent) {
    this.continent = continent;
  }

  public void setTax(int tax) {
    this.tax = tax;
  }
}
