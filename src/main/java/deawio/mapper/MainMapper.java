package deawio.mapper;

import org.apache.ibatis.annotations.Select;

public interface MainMapper {
  @Select("select lastval()")
  public Integer selectLastVal();
}
