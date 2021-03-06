package deawio.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DealModelExample {
  /**
   * This field was generated by MyBatis Generator. This field corresponds to the database table
   * deal
   *
   * @mbg.generated Wed Jan 24 21:36:03 GMT 2018
   */
  protected String orderByClause;

  /**
   * This field was generated by MyBatis Generator. This field corresponds to the database table
   * deal
   *
   * @mbg.generated Wed Jan 24 21:36:03 GMT 2018
   */
  protected boolean distinct;

  /**
   * This field was generated by MyBatis Generator. This field corresponds to the database table
   * deal
   *
   * @mbg.generated Wed Jan 24 21:36:03 GMT 2018
   */
  protected List<Criteria> oredCriteria;

  /**
   * This method was generated by MyBatis Generator. This method corresponds to the database table
   * deal
   *
   * @mbg.generated Wed Jan 24 21:36:03 GMT 2018
   */
  public DealModelExample() {
    oredCriteria = new ArrayList<Criteria>();
  }

  /**
   * This method was generated by MyBatis Generator. This method corresponds to the database table
   * deal
   *
   * @mbg.generated Wed Jan 24 21:36:03 GMT 2018
   */
  public void setOrderByClause(String orderByClause) {
    this.orderByClause = orderByClause;
  }

  /**
   * This method was generated by MyBatis Generator. This method corresponds to the database table
   * deal
   *
   * @mbg.generated Wed Jan 24 21:36:03 GMT 2018
   */
  public String getOrderByClause() {
    return orderByClause;
  }

  /**
   * This method was generated by MyBatis Generator. This method corresponds to the database table
   * deal
   *
   * @mbg.generated Wed Jan 24 21:36:03 GMT 2018
   */
  public void setDistinct(boolean distinct) {
    this.distinct = distinct;
  }

  /**
   * This method was generated by MyBatis Generator. This method corresponds to the database table
   * deal
   *
   * @mbg.generated Wed Jan 24 21:36:03 GMT 2018
   */
  public boolean isDistinct() {
    return distinct;
  }

  /**
   * This method was generated by MyBatis Generator. This method corresponds to the database table
   * deal
   *
   * @mbg.generated Wed Jan 24 21:36:03 GMT 2018
   */
  public List<Criteria> getOredCriteria() {
    return oredCriteria;
  }

  /**
   * This method was generated by MyBatis Generator. This method corresponds to the database table
   * deal
   *
   * @mbg.generated Wed Jan 24 21:36:03 GMT 2018
   */
  public void or(Criteria criteria) {
    oredCriteria.add(criteria);
  }

  /**
   * This method was generated by MyBatis Generator. This method corresponds to the database table
   * deal
   *
   * @mbg.generated Wed Jan 24 21:36:03 GMT 2018
   */
  public Criteria or() {
    Criteria criteria = createCriteriaInternal();
    oredCriteria.add(criteria);
    return criteria;
  }

  /**
   * This method was generated by MyBatis Generator. This method corresponds to the database table
   * deal
   *
   * @mbg.generated Wed Jan 24 21:36:03 GMT 2018
   */
  public Criteria createCriteria() {
    Criteria criteria = createCriteriaInternal();
    if (oredCriteria.size() == 0) {
      oredCriteria.add(criteria);
    }
    return criteria;
  }

  /**
   * This method was generated by MyBatis Generator. This method corresponds to the database table
   * deal
   *
   * @mbg.generated Wed Jan 24 21:36:03 GMT 2018
   */
  protected Criteria createCriteriaInternal() {
    Criteria criteria = new Criteria();
    return criteria;
  }

  /**
   * This method was generated by MyBatis Generator. This method corresponds to the database table
   * deal
   *
   * @mbg.generated Wed Jan 24 21:36:03 GMT 2018
   */
  public void clear() {
    oredCriteria.clear();
    orderByClause = null;
    distinct = false;
  }

  /**
   * This class was generated by MyBatis Generator. This class corresponds to the database table
   * deal
   *
   * @mbg.generated Wed Jan 24 21:36:03 GMT 2018
   */
  protected abstract static class GeneratedCriteria {
    protected List<Criterion> criteria;

    protected GeneratedCriteria() {
      super();
      criteria = new ArrayList<Criterion>();
    }

    public boolean isValid() {
      return criteria.size() > 0;
    }

    public List<Criterion> getAllCriteria() {
      return criteria;
    }

    public List<Criterion> getCriteria() {
      return criteria;
    }

    protected void addCriterion(String condition) {
      if (condition == null) {
        throw new RuntimeException("Value for condition cannot be null");
      }
      criteria.add(new Criterion(condition));
    }

    protected void addCriterion(String condition, Object value, String property) {
      if (value == null) {
        throw new RuntimeException("Value for " + property + " cannot be null");
      }
      criteria.add(new Criterion(condition, value));
    }

    protected void addCriterion(String condition, Object value1, Object value2, String property) {
      if (value1 == null || value2 == null) {
        throw new RuntimeException("Between values for " + property + " cannot be null");
      }
      criteria.add(new Criterion(condition, value1, value2));
    }

    public Criteria andDealIdIsNull() {
      addCriterion("deal_id is null");
      return (Criteria) this;
    }

    public Criteria andDealIdIsNotNull() {
      addCriterion("deal_id is not null");
      return (Criteria) this;
    }

    public Criteria andDealIdEqualTo(Integer value) {
      addCriterion("deal_id =", value, "dealId");
      return (Criteria) this;
    }

    public Criteria andDealIdNotEqualTo(Integer value) {
      addCriterion("deal_id <>", value, "dealId");
      return (Criteria) this;
    }

    public Criteria andDealIdGreaterThan(Integer value) {
      addCriterion("deal_id >", value, "dealId");
      return (Criteria) this;
    }

    public Criteria andDealIdGreaterThanOrEqualTo(Integer value) {
      addCriterion("deal_id >=", value, "dealId");
      return (Criteria) this;
    }

    public Criteria andDealIdLessThan(Integer value) {
      addCriterion("deal_id <", value, "dealId");
      return (Criteria) this;
    }

    public Criteria andDealIdLessThanOrEqualTo(Integer value) {
      addCriterion("deal_id <=", value, "dealId");
      return (Criteria) this;
    }

    public Criteria andDealIdIn(List<Integer> values) {
      addCriterion("deal_id in", values, "dealId");
      return (Criteria) this;
    }

    public Criteria andDealIdNotIn(List<Integer> values) {
      addCriterion("deal_id not in", values, "dealId");
      return (Criteria) this;
    }

    public Criteria andDealIdBetween(Integer value1, Integer value2) {
      addCriterion("deal_id between", value1, value2, "dealId");
      return (Criteria) this;
    }

    public Criteria andDealIdNotBetween(Integer value1, Integer value2) {
      addCriterion("deal_id not between", value1, value2, "dealId");
      return (Criteria) this;
    }

    public Criteria andCreatedIsNull() {
      addCriterion("created is null");
      return (Criteria) this;
    }

    public Criteria andCreatedIsNotNull() {
      addCriterion("created is not null");
      return (Criteria) this;
    }

    public Criteria andCreatedEqualTo(Date value) {
      addCriterion("created =", value, "created");
      return (Criteria) this;
    }

    public Criteria andCreatedNotEqualTo(Date value) {
      addCriterion("created <>", value, "created");
      return (Criteria) this;
    }

    public Criteria andCreatedGreaterThan(Date value) {
      addCriterion("created >", value, "created");
      return (Criteria) this;
    }

    public Criteria andCreatedGreaterThanOrEqualTo(Date value) {
      addCriterion("created >=", value, "created");
      return (Criteria) this;
    }

    public Criteria andCreatedLessThan(Date value) {
      addCriterion("created <", value, "created");
      return (Criteria) this;
    }

    public Criteria andCreatedLessThanOrEqualTo(Date value) {
      addCriterion("created <=", value, "created");
      return (Criteria) this;
    }

    public Criteria andCreatedIn(List<Date> values) {
      addCriterion("created in", values, "created");
      return (Criteria) this;
    }

    public Criteria andCreatedNotIn(List<Date> values) {
      addCriterion("created not in", values, "created");
      return (Criteria) this;
    }

    public Criteria andCreatedBetween(Date value1, Date value2) {
      addCriterion("created between", value1, value2, "created");
      return (Criteria) this;
    }

    public Criteria andCreatedNotBetween(Date value1, Date value2) {
      addCriterion("created not between", value1, value2, "created");
      return (Criteria) this;
    }

    public Criteria andModifiedIsNull() {
      addCriterion("modified is null");
      return (Criteria) this;
    }

    public Criteria andModifiedIsNotNull() {
      addCriterion("modified is not null");
      return (Criteria) this;
    }

    public Criteria andModifiedEqualTo(Date value) {
      addCriterion("modified =", value, "modified");
      return (Criteria) this;
    }

    public Criteria andModifiedNotEqualTo(Date value) {
      addCriterion("modified <>", value, "modified");
      return (Criteria) this;
    }

    public Criteria andModifiedGreaterThan(Date value) {
      addCriterion("modified >", value, "modified");
      return (Criteria) this;
    }

    public Criteria andModifiedGreaterThanOrEqualTo(Date value) {
      addCriterion("modified >=", value, "modified");
      return (Criteria) this;
    }

    public Criteria andModifiedLessThan(Date value) {
      addCriterion("modified <", value, "modified");
      return (Criteria) this;
    }

    public Criteria andModifiedLessThanOrEqualTo(Date value) {
      addCriterion("modified <=", value, "modified");
      return (Criteria) this;
    }

    public Criteria andModifiedIn(List<Date> values) {
      addCriterion("modified in", values, "modified");
      return (Criteria) this;
    }

    public Criteria andModifiedNotIn(List<Date> values) {
      addCriterion("modified not in", values, "modified");
      return (Criteria) this;
    }

    public Criteria andModifiedBetween(Date value1, Date value2) {
      addCriterion("modified between", value1, value2, "modified");
      return (Criteria) this;
    }

    public Criteria andModifiedNotBetween(Date value1, Date value2) {
      addCriterion("modified not between", value1, value2, "modified");
      return (Criteria) this;
    }

    public Criteria andUrlIsNull() {
      addCriterion("url is null");
      return (Criteria) this;
    }

    public Criteria andUrlIsNotNull() {
      addCriterion("url is not null");
      return (Criteria) this;
    }

    public Criteria andUrlEqualTo(String value) {
      addCriterion("url =", value, "url");
      return (Criteria) this;
    }

    public Criteria andUrlNotEqualTo(String value) {
      addCriterion("url <>", value, "url");
      return (Criteria) this;
    }

    public Criteria andUrlGreaterThan(String value) {
      addCriterion("url >", value, "url");
      return (Criteria) this;
    }

    public Criteria andUrlGreaterThanOrEqualTo(String value) {
      addCriterion("url >=", value, "url");
      return (Criteria) this;
    }

    public Criteria andUrlLessThan(String value) {
      addCriterion("url <", value, "url");
      return (Criteria) this;
    }

    public Criteria andUrlLessThanOrEqualTo(String value) {
      addCriterion("url <=", value, "url");
      return (Criteria) this;
    }

    public Criteria andUrlLike(String value) {
      addCriterion("url like", value, "url");
      return (Criteria) this;
    }

    public Criteria andUrlNotLike(String value) {
      addCriterion("url not like", value, "url");
      return (Criteria) this;
    }

    public Criteria andUrlIn(List<String> values) {
      addCriterion("url in", values, "url");
      return (Criteria) this;
    }

    public Criteria andUrlNotIn(List<String> values) {
      addCriterion("url not in", values, "url");
      return (Criteria) this;
    }

    public Criteria andUrlBetween(String value1, String value2) {
      addCriterion("url between", value1, value2, "url");
      return (Criteria) this;
    }

    public Criteria andUrlNotBetween(String value1, String value2) {
      addCriterion("url not between", value1, value2, "url");
      return (Criteria) this;
    }

    public Criteria andHighPriceIsNull() {
      addCriterion("high_price is null");
      return (Criteria) this;
    }

    public Criteria andHighPriceIsNotNull() {
      addCriterion("high_price is not null");
      return (Criteria) this;
    }

    public Criteria andHighPriceEqualTo(Double value) {
      addCriterion("high_price =", value, "highPrice");
      return (Criteria) this;
    }

    public Criteria andHighPriceNotEqualTo(Double value) {
      addCriterion("high_price <>", value, "highPrice");
      return (Criteria) this;
    }

    public Criteria andHighPriceGreaterThan(Double value) {
      addCriterion("high_price >", value, "highPrice");
      return (Criteria) this;
    }

    public Criteria andHighPriceGreaterThanOrEqualTo(Double value) {
      addCriterion("high_price >=", value, "highPrice");
      return (Criteria) this;
    }

    public Criteria andHighPriceLessThan(Double value) {
      addCriterion("high_price <", value, "highPrice");
      return (Criteria) this;
    }

    public Criteria andHighPriceLessThanOrEqualTo(Double value) {
      addCriterion("high_price <=", value, "highPrice");
      return (Criteria) this;
    }

    public Criteria andHighPriceIn(List<Double> values) {
      addCriterion("high_price in", values, "highPrice");
      return (Criteria) this;
    }

    public Criteria andHighPriceNotIn(List<Double> values) {
      addCriterion("high_price not in", values, "highPrice");
      return (Criteria) this;
    }

    public Criteria andHighPriceBetween(Double value1, Double value2) {
      addCriterion("high_price between", value1, value2, "highPrice");
      return (Criteria) this;
    }

    public Criteria andHighPriceNotBetween(Double value1, Double value2) {
      addCriterion("high_price not between", value1, value2, "highPrice");
      return (Criteria) this;
    }

    public Criteria andLowPriceIsNull() {
      addCriterion("low_price is null");
      return (Criteria) this;
    }

    public Criteria andLowPriceIsNotNull() {
      addCriterion("low_price is not null");
      return (Criteria) this;
    }

    public Criteria andLowPriceEqualTo(Double value) {
      addCriterion("low_price =", value, "lowPrice");
      return (Criteria) this;
    }

    public Criteria andLowPriceNotEqualTo(Double value) {
      addCriterion("low_price <>", value, "lowPrice");
      return (Criteria) this;
    }

    public Criteria andLowPriceGreaterThan(Double value) {
      addCriterion("low_price >", value, "lowPrice");
      return (Criteria) this;
    }

    public Criteria andLowPriceGreaterThanOrEqualTo(Double value) {
      addCriterion("low_price >=", value, "lowPrice");
      return (Criteria) this;
    }

    public Criteria andLowPriceLessThan(Double value) {
      addCriterion("low_price <", value, "lowPrice");
      return (Criteria) this;
    }

    public Criteria andLowPriceLessThanOrEqualTo(Double value) {
      addCriterion("low_price <=", value, "lowPrice");
      return (Criteria) this;
    }

    public Criteria andLowPriceIn(List<Double> values) {
      addCriterion("low_price in", values, "lowPrice");
      return (Criteria) this;
    }

    public Criteria andLowPriceNotIn(List<Double> values) {
      addCriterion("low_price not in", values, "lowPrice");
      return (Criteria) this;
    }

    public Criteria andLowPriceBetween(Double value1, Double value2) {
      addCriterion("low_price between", value1, value2, "lowPrice");
      return (Criteria) this;
    }

    public Criteria andLowPriceNotBetween(Double value1, Double value2) {
      addCriterion("low_price not between", value1, value2, "lowPrice");
      return (Criteria) this;
    }

    public Criteria andDiscountIsNull() {
      addCriterion("discount is null");
      return (Criteria) this;
    }

    public Criteria andDiscountIsNotNull() {
      addCriterion("discount is not null");
      return (Criteria) this;
    }

    public Criteria andDiscountEqualTo(Double value) {
      addCriterion("discount =", value, "discount");
      return (Criteria) this;
    }

    public Criteria andDiscountNotEqualTo(Double value) {
      addCriterion("discount <>", value, "discount");
      return (Criteria) this;
    }

    public Criteria andDiscountGreaterThan(Double value) {
      addCriterion("discount >", value, "discount");
      return (Criteria) this;
    }

    public Criteria andDiscountGreaterThanOrEqualTo(Double value) {
      addCriterion("discount >=", value, "discount");
      return (Criteria) this;
    }

    public Criteria andDiscountLessThan(Double value) {
      addCriterion("discount <", value, "discount");
      return (Criteria) this;
    }

    public Criteria andDiscountLessThanOrEqualTo(Double value) {
      addCriterion("discount <=", value, "discount");
      return (Criteria) this;
    }

    public Criteria andDiscountIn(List<Double> values) {
      addCriterion("discount in", values, "discount");
      return (Criteria) this;
    }

    public Criteria andDiscountNotIn(List<Double> values) {
      addCriterion("discount not in", values, "discount");
      return (Criteria) this;
    }

    public Criteria andDiscountBetween(Double value1, Double value2) {
      addCriterion("discount between", value1, value2, "discount");
      return (Criteria) this;
    }

    public Criteria andDiscountNotBetween(Double value1, Double value2) {
      addCriterion("discount not between", value1, value2, "discount");
      return (Criteria) this;
    }

    public Criteria andStoreIdIsNull() {
      addCriterion("store_id is null");
      return (Criteria) this;
    }

    public Criteria andStoreIdIsNotNull() {
      addCriterion("store_id is not null");
      return (Criteria) this;
    }

    public Criteria andStoreIdEqualTo(Integer value) {
      addCriterion("store_id =", value, "storeId");
      return (Criteria) this;
    }

    public Criteria andStoreIdNotEqualTo(Integer value) {
      addCriterion("store_id <>", value, "storeId");
      return (Criteria) this;
    }

    public Criteria andStoreIdGreaterThan(Integer value) {
      addCriterion("store_id >", value, "storeId");
      return (Criteria) this;
    }

    public Criteria andStoreIdGreaterThanOrEqualTo(Integer value) {
      addCriterion("store_id >=", value, "storeId");
      return (Criteria) this;
    }

    public Criteria andStoreIdLessThan(Integer value) {
      addCriterion("store_id <", value, "storeId");
      return (Criteria) this;
    }

    public Criteria andStoreIdLessThanOrEqualTo(Integer value) {
      addCriterion("store_id <=", value, "storeId");
      return (Criteria) this;
    }

    public Criteria andStoreIdIn(List<Integer> values) {
      addCriterion("store_id in", values, "storeId");
      return (Criteria) this;
    }

    public Criteria andStoreIdNotIn(List<Integer> values) {
      addCriterion("store_id not in", values, "storeId");
      return (Criteria) this;
    }

    public Criteria andStoreIdBetween(Integer value1, Integer value2) {
      addCriterion("store_id between", value1, value2, "storeId");
      return (Criteria) this;
    }

    public Criteria andStoreIdNotBetween(Integer value1, Integer value2) {
      addCriterion("store_id not between", value1, value2, "storeId");
      return (Criteria) this;
    }

    public Criteria andProductIdIsNull() {
      addCriterion("product_id is null");
      return (Criteria) this;
    }

    public Criteria andProductIdIsNotNull() {
      addCriterion("product_id is not null");
      return (Criteria) this;
    }

    public Criteria andProductIdEqualTo(Integer value) {
      addCriterion("product_id =", value, "productId");
      return (Criteria) this;
    }

    public Criteria andProductIdNotEqualTo(Integer value) {
      addCriterion("product_id <>", value, "productId");
      return (Criteria) this;
    }

    public Criteria andProductIdGreaterThan(Integer value) {
      addCriterion("product_id >", value, "productId");
      return (Criteria) this;
    }

    public Criteria andProductIdGreaterThanOrEqualTo(Integer value) {
      addCriterion("product_id >=", value, "productId");
      return (Criteria) this;
    }

    public Criteria andProductIdLessThan(Integer value) {
      addCriterion("product_id <", value, "productId");
      return (Criteria) this;
    }

    public Criteria andProductIdLessThanOrEqualTo(Integer value) {
      addCriterion("product_id <=", value, "productId");
      return (Criteria) this;
    }

    public Criteria andProductIdIn(List<Integer> values) {
      addCriterion("product_id in", values, "productId");
      return (Criteria) this;
    }

    public Criteria andProductIdNotIn(List<Integer> values) {
      addCriterion("product_id not in", values, "productId");
      return (Criteria) this;
    }

    public Criteria andProductIdBetween(Integer value1, Integer value2) {
      addCriterion("product_id between", value1, value2, "productId");
      return (Criteria) this;
    }

    public Criteria andProductIdNotBetween(Integer value1, Integer value2) {
      addCriterion("product_id not between", value1, value2, "productId");
      return (Criteria) this;
    }
  }

  /**
   * This class was generated by MyBatis Generator. This class corresponds to the database table
   * deal
   *
   * @mbg.generated do_not_delete_during_merge Wed Jan 24 21:36:03 GMT 2018
   */
  public static class Criteria extends GeneratedCriteria {

    protected Criteria() {
      super();
    }
  }

  /**
   * This class was generated by MyBatis Generator. This class corresponds to the database table
   * deal
   *
   * @mbg.generated Wed Jan 24 21:36:03 GMT 2018
   */
  public static class Criterion {
    private String condition;

    private Object value;

    private Object secondValue;

    private boolean noValue;

    private boolean singleValue;

    private boolean betweenValue;

    private boolean listValue;

    private String typeHandler;

    public String getCondition() {
      return condition;
    }

    public Object getValue() {
      return value;
    }

    public Object getSecondValue() {
      return secondValue;
    }

    public boolean isNoValue() {
      return noValue;
    }

    public boolean isSingleValue() {
      return singleValue;
    }

    public boolean isBetweenValue() {
      return betweenValue;
    }

    public boolean isListValue() {
      return listValue;
    }

    public String getTypeHandler() {
      return typeHandler;
    }

    protected Criterion(String condition) {
      super();
      this.condition = condition;
      this.typeHandler = null;
      this.noValue = true;
    }

    protected Criterion(String condition, Object value, String typeHandler) {
      super();
      this.condition = condition;
      this.value = value;
      this.typeHandler = typeHandler;
      if (value instanceof List<?>) {
        this.listValue = true;
      } else {
        this.singleValue = true;
      }
    }

    protected Criterion(String condition, Object value) {
      this(condition, value, null);
    }

    protected Criterion(String condition, Object value, Object secondValue, String typeHandler) {
      super();
      this.condition = condition;
      this.value = value;
      this.secondValue = secondValue;
      this.typeHandler = typeHandler;
      this.betweenValue = true;
    }

    protected Criterion(String condition, Object value, Object secondValue) {
      this(condition, value, secondValue, null);
    }
  }
}
