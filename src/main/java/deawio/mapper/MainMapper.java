package deawio.mapper;

import org.apache.ibatis.annotations.Select;

public interface MainMapper {
  @Select("SELECT LASTVAL()")
  public Integer selectLastVal();
}
