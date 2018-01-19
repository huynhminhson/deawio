package deawio.mapper;

import deawio.model.DealModel;
import deawio.model.DealModelExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface DealModelMapper {
  /**
   * This method was generated by MyBatis Generator. This method corresponds to the database table
   * deal
   *
   * @mbg.generated Sat Jan 13 07:44:22 GMT 2018
   */
  long countByExample(DealModelExample example);

  /**
   * This method was generated by MyBatis Generator. This method corresponds to the database table
   * deal
   *
   * @mbg.generated Sat Jan 13 07:44:22 GMT 2018
   */
  int deleteByExample(DealModelExample example);

  /**
   * This method was generated by MyBatis Generator. This method corresponds to the database table
   * deal
   *
   * @mbg.generated Sat Jan 13 07:44:22 GMT 2018
   */
  int deleteByPrimaryKey(Integer dealId);

  /**
   * This method was generated by MyBatis Generator. This method corresponds to the database table
   * deal
   *
   * @mbg.generated Sat Jan 13 07:44:22 GMT 2018
   */
  int insert(DealModel record);

  /**
   * This method was generated by MyBatis Generator. This method corresponds to the database table
   * deal
   *
   * @mbg.generated Sat Jan 13 07:44:22 GMT 2018
   */
  int insertSelective(DealModel record);

  /**
   * This method was generated by MyBatis Generator. This method corresponds to the database table
   * deal
   *
   * @mbg.generated Sat Jan 13 07:44:22 GMT 2018
   */
  List<DealModel> selectByExample(DealModelExample example);

  /**
   * This method was generated by MyBatis Generator. This method corresponds to the database table
   * deal
   *
   * @mbg.generated Sat Jan 13 07:44:22 GMT 2018
   */
  DealModel selectByPrimaryKey(Integer dealId);

  /**
   * This method was generated by MyBatis Generator. This method corresponds to the database table
   * deal
   *
   * @mbg.generated Sat Jan 13 07:44:22 GMT 2018
   */
  int updateByExampleSelective(
      @Param("record") DealModel record, @Param("example") DealModelExample example);

  /**
   * This method was generated by MyBatis Generator. This method corresponds to the database table
   * deal
   *
   * @mbg.generated Sat Jan 13 07:44:22 GMT 2018
   */
  int updateByExample(
      @Param("record") DealModel record, @Param("example") DealModelExample example);

  /**
   * This method was generated by MyBatis Generator. This method corresponds to the database table
   * deal
   *
   * @mbg.generated Sat Jan 13 07:44:22 GMT 2018
   */
  int updateByPrimaryKeySelective(DealModel record);

  /**
   * This method was generated by MyBatis Generator. This method corresponds to the database table
   * deal
   *
   * @mbg.generated Sat Jan 13 07:44:22 GMT 2018
   */
  int updateByPrimaryKey(DealModel record);
}
