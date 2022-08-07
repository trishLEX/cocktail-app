package org.openapitools.client.model;

import com.google.gson.annotations.SerializedName;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;


@ApiModel(description = "")
public class CocktailIngredientDTO  {
  
  @SerializedName("id")
  private Integer id = null;
  @SerializedName("name")
  private String name = null;
  @SerializedName("preview")
  private byte[] preview = null;
  @SerializedName("type")
  private IngredientTypeDTO type = null;
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
  public String getName() {
    return name;
  }
  public void setName(String name) {
    this.name = name;
  }

  /**
   **/
  @ApiModelProperty(value = "")
  public byte[] getPreview() {
    return preview;
  }
  public void setPreview(byte[] preview) {
    this.preview = preview;
  }

  /**
   **/
  @ApiModelProperty(value = "")
  public IngredientTypeDTO getType() {
    return type;
  }
  public void setType(IngredientTypeDTO type) {
    this.type = type;
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
    CocktailIngredientDTO cocktailIngredientDTO = (CocktailIngredientDTO) o;
    return (this.id == null ? cocktailIngredientDTO.id == null : this.id.equals(cocktailIngredientDTO.id)) &&
        (this.name == null ? cocktailIngredientDTO.name == null : this.name.equals(cocktailIngredientDTO.name)) &&
        (this.preview == null ? cocktailIngredientDTO.preview == null : this.preview.equals(cocktailIngredientDTO.preview)) &&
        (this.type == null ? cocktailIngredientDTO.type == null : this.type.equals(cocktailIngredientDTO.type)) &&
        (this.amount == null ? cocktailIngredientDTO.amount == null : this.amount.equals(cocktailIngredientDTO.amount)) &&
        (this.unit == null ? cocktailIngredientDTO.unit == null : this.unit.equals(cocktailIngredientDTO.unit));
  }

  @Override
  public int hashCode() {
    int result = 17;
    result = 31 * result + (this.id == null ? 0: this.id.hashCode());
    result = 31 * result + (this.name == null ? 0: this.name.hashCode());
    result = 31 * result + (this.preview == null ? 0: this.preview.hashCode());
    result = 31 * result + (this.type == null ? 0: this.type.hashCode());
    result = 31 * result + (this.amount == null ? 0: this.amount.hashCode());
    result = 31 * result + (this.unit == null ? 0: this.unit.hashCode());
    return result;
  }

  @Override
  public String toString()  {
    StringBuilder sb = new StringBuilder();
    sb.append("class CocktailIngredientDTO {\n");
    
    sb.append("  id: ").append(id).append("\n");
    sb.append("  name: ").append(name).append("\n");
    sb.append("  preview: ").append(preview).append("\n");
    sb.append("  type: ").append(type).append("\n");
    sb.append("  amount: ").append(amount).append("\n");
    sb.append("  unit: ").append(unit).append("\n");
    sb.append("}\n");
    return sb.toString();
  }
}
