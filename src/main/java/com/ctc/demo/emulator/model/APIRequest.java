package com.ctc.demo.emulator.model;

import javax.validation.Valid;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.v3.oas.annotations.media.Schema;

public class APIRequest {
	  @JsonProperty("request_message")
	  private RequestMessage requestMessage = null;


	  @Schema(description = "")

	    @Valid
	    public RequestMessage getRequestMessage() {
	    return requestMessage;
	  }

	  public void setRequestMessage(RequestMessage requestMessage) {
	    this.requestMessage = requestMessage;
	  }


	  @Override
	  public String toString() {
	    StringBuilder sb = new StringBuilder();
	    sb.append("class APIRequest {\n");

	    sb.append("    requestMessage: ").append(toIndentedString(requestMessage)).append("\n");
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
