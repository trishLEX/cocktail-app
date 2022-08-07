package org.openapitools.client.model;


import com.google.gson.annotations.SerializedName;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;


@ApiModel(description = "")
public class SaveIngredientRequestDTO  {
  
  @SerializedName("id")
  private Integer id = null;
  @SerializedName("amount")
  private Integer amount = null;
  @SerializedName("unit")
  private String unit = null;

  /**
   **/
  @ApiModelProperty(value = "")
  public Integer getId() {
    return id;
  }
  public void setId(Integer id) {
    this.id = id;
  }

  /**
   **/
  @ApiModelProperty(value = "")
  public Integer getAmount() {
    return amount;
  }
  public void setAmount(Integer amount) {
    this.amount = amount;
  }

  /**
   **/
  @ApiModelProperty(value = "")
  public String getUnit() {
    return unit;
  }
  public void setUnit(String unit) {
    this.unit = unit;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    SaveIngredientRequestDTO saveIngredientRequestDTO = (SaveIngredientRequestDTO) o;
    return (this.id == null ? saveIngredientRequestDTO.id == null : this.id.equals(saveIngredientRequestDTO.id)) &&
        (this.amount == null ? saveIngredientRequestDTO.amount == null : this.amount.equals(saveIngredientRequestDTO.amount)) &&
        (this.unit == null ? saveIngredientRequestDTO.unit == null : this.unit.equals(saveIngredientRequestDTO.unit));
  }

  @Override
  public int hashCode() {
    int result = 17;
    result = 31 * result + (this.id == null ? 0: this.id.hashCode());
    result = 31 * result + (this.amount == null ? 0: this.amount.hashCode());
    result = 31 * result + (this.unit == null ? 0: this.unit.hashCode());
    return result;
  }

  @Override
  public String toString()  {
    StringBuilder sb = new StringBuilder();
    sb.append("class SaveIngredientRequestDTO {\n");
    
    sb.append("  id: ").append(id).append("\n");
    sb.append("  amount: ").append(amount).append("\n");
    sb.append("  unit: ").append(unit).append("\n");
    sb.append("}\n");
    return sb.toString();
  }
}
