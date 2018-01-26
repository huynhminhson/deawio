package deawio.base;

import java.io.IOException;
import java.io.InputStream;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

public class MyBatisUtil {
  public static final SqlSessionFactory sqlSessionFactory;

  static {
    InputStream inputStream = null;
    try {
      inputStream = Resources.getResourceAsStream("mybatis.xml");
    } catch (IOException e) {
      e.printStackTrace();
    }
    sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
  }
}
