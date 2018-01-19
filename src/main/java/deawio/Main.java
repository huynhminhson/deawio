package deawio;

import deawio.crawler.site.StoreSteampoweredCom;

public class Main {
  public static void main(String[] args) throws InterruptedException {
    while (true) {
      Thread t1 = new Thread(new StoreSteampoweredCom());
      t1.start();
      t1.join();
    }
  }
}
