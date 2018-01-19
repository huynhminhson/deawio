package deawio.crawler;

public class BaseCrawler {
  public static double extractPrice(String priceString) {
    if (priceString.toLowerCase().contains("free")) {
      return 0;
    } else {
      for (char ch : priceString.toCharArray()) {
        if (ch == '.' || ch == ',' || Character.isDigit(ch)) {
        } else {
          priceString = priceString.replace(Character.toString(ch), "");
        }
      }
      if (priceString.indexOf(",") < priceString.indexOf(".")) {
        priceString = priceString.replace(",", "");
      } else {
        priceString = priceString.replace(".", "");
        priceString = priceString.replace(",", ".");
      }
      return Double.valueOf(priceString);
    }
  }

  public static double extractDiscount(String discountString) {
    for (char ch : discountString.toCharArray()) {
      if (ch == '.' || ch == ',' || Character.isDigit(ch)) {
      } else {
        discountString = discountString.replace(Character.toString(ch), "");
      }
    }
    return Double.valueOf(discountString) / 100;
  }

  public static double calcDiscount(double low, double high) {
    if (low > high) {
      throw new IllegalArgumentException();
    } else {
      if (high == 0) {
        return (double) 1;
      } else {
        return 1 - (low / high);
      }
    }
  }

  public static double calcHighPrice(double discount, double low) {
    if (discount == 1) {
      if (low == 0) {
        return 0;
      } else {
        throw new IllegalArgumentException();
      }
    } else {
      return low / (1 - discount);
    }
  }

  public static double calcLowPrice(double discount, double high) {
    return (1 - discount) * high;
  }
}
