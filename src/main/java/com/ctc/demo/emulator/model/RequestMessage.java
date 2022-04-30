package com.ctc.demo.emulator.model;

import javax.validation.Valid;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.v3.oas.annotations.media.Schema;

public class RequestMessage {

	  @JsonProperty("message")
	  private String message = null;


	  @Schema(description = "")

	    @Valid
	    public String getMessage() {
	    return message;
	  }

	  public void setMessage(String message) {
	    this.message = message;
	  }


	  @Override
	  public String toString() {
	    StringBuilder sb = new StringBuilder();
	    sb.append("class RequestMessage {\n");

	    sb.append("    message: ").append(toIndentedString(message)).append("\n");
	    sb.append("}");
	    return sb.toString();
	  }

	  /**
	   * Convert the given object to string with each line indented by 4 spaces
	   * (except the first line).
	   */
	  private String toIndentedString(java.lang.Object o) {
	    if (o == null) {
	      return "null";
	    }
	    return o.toString().replace("\n", "\n    ");
	  }
}
